package co.yorku.nutrifit.visualizer.calculators;

import co.yorku.nutrifit.NutriFit;
import co.yorku.nutrifit.logs.LogIterator;
import co.yorku.nutrifit.logs.impl.Meal;
import co.yorku.nutrifit.object.NutrientData;
import co.yorku.nutrifit.object.FoodNutrientInfo;
import com.google.common.collect.Maps;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/*
    This is the NutrientCalculator.
    It's purpose is to calculate the user's Nutrients consumed
    over a period of time
 */
public class NutrientCalculator {

    // This method returns a LinkedHashMap of type String, Map<String, Double> which contain
    // a day and a map of nutrients and percentages consumed that day
    public LinkedHashMap<String, Map<String, Double>> getNutrientInfoPerDate(Date dateFrom, Date dateTo) {

        // Query The Database for the specific date range
        LogIterator logIterator = NutriFit.getInstance().getUserDatabase().getUserMealLogs(
                NutriFit.getInstance().getLoadedProfile().getId(),
                dateFrom,
                dateTo
        );
        logIterator.sortByDateAscending(); // Sort data by ascending

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM dd yyyy"); // Create a SimpleDateFormat object to format Date objects

        // This map holds a String and Map
        // The string is the day
        // The map contains a nutrient name and an AverageCalculator object that will be used later to calculate the average amount of times that a specific nutrient shows up that day
        LinkedHashMap<String, Map<String, Double>> data = Maps.newLinkedHashMap();
        Map<String, Integer> totalNutrientsForEachDay = Maps.newHashMap();

        while (logIterator.hasNext()) { // Loop through all the user meals
            Meal meal = (Meal)  logIterator.getNext(); // Get the meal object
            Date date = meal.getDate(); // Get the date from the meal object
            String dayKey = simpleDateFormat.format(date); // Format the date

            // Get or Create the Nutrient map for the specific day
            Map<String,Double> nutrientsPerDay = data.computeIfAbsent(dayKey, k -> Maps.newLinkedHashMap());

            for (Map.Entry<Integer, Integer> integerIntegerEntry : meal.getFoodIDAndAmounts().entrySet()) { // Loop Through all the foods in the meal

                FoodNutrientInfo foodNutrientInfo = NutriFit.getInstance().getNutrientDatabase().getNutrientInfo(integerIntegerEntry.getKey()); // Query Database for specific food info

                for (Map.Entry<Integer, Double> integerDoubleEntry : foodNutrientInfo.getNutrientIDAndNutrientValue().entrySet()) { // Loop through all the specific nutrients found in this specific food

                    // If the nutrient value is less than or equal to 0, continue
                    if (integerDoubleEntry.getValue() <= 0.0) continue;

                    NutrientData nutrientData = NutriFit.getInstance().getNutrientDatabase().getNutrientData(integerDoubleEntry.getKey()); // Query Database for the NutrientData for the specific Nutrient
                    if (nutrientData == null) continue; // If it's null, we continue
                    // Get the total sum of the specific nutrient
                    double sum = nutrientsPerDay.computeIfAbsent(nutrientData.getName(), id -> 0.0);

                    // Check if the unit is mg, convert it to g
                    if (nutrientData.getUnit().equals("mg")) {
                        sum+=(integerDoubleEntry.getValue()/1000); // Add it to the sum
                    } else {
                        sum+=integerDoubleEntry.getValue(); // Add it to the sum
                    }

                    nutrientsPerDay.put(nutrientData.getName(), sum); // Update our nutrient map with new sum
                    totalNutrientsForEachDay.put(dayKey, totalNutrientsForEachDay.getOrDefault(dayKey, 0) + 1);
                }
            }

        }

        LinkedHashMap<String, Map<String, Double>> calculatedData = averageOutNutrientsPerDay(data, totalNutrientsForEachDay); // This will calculate the average sum and create the final object to be returned
        return aggregateData(calculatedData); // This will aggregate the nutrient data to show only the top 5 and everything else in the "other" category
    }

    private LinkedHashMap<String, Map<String, Double>> aggregateData(LinkedHashMap<String, Map<String, Double>> data) {

        LinkedHashMap<String, Map<String, Double>> finalData = Maps.newLinkedHashMap(); // Final data to be returned

        for (Map.Entry<String, Map<String, Double>> stringMapEntry : data.entrySet()) { // Loop through all the sorted data

            int total = 0; // Total number of nutrients
            Map<String, Double> finalDataForDay = Maps.newHashMap(); // Final averages to display for the day

            for (Map.Entry<String, Double> stringDoubleEntry : stringMapEntry.getValue().entrySet()) { // Loop through all the nutrients and it's averages
                if (total >= 5) { // If the total number of nutrients we are displaying is greater than or equal to 5, we will put everything else in an "Other Nutrients" category
                    finalDataForDay.put("Other Nutrients", finalDataForDay.getOrDefault("Other Nutrients", 0.0) + stringDoubleEntry.getValue()); // Increment the average for the other nutrients category
                } else {

                    String nutrientName = stringDoubleEntry.getKey(); // Get the nutrient tag
                    // Get the first char and uppercase it, get the rest of the string and lowercase it
                    nutrientName = Character.toUpperCase(nutrientName.charAt(0)) + nutrientName.substring(1).toLowerCase();
                    if (nutrientName.contains(",")) { // Remove any commas and get the first element
                        nutrientName = nutrientName.split(",")[0];
                    }

                    if (nutrientName.contains("(")) { // Remove any brackets and get the first element
                        nutrientName = nutrientName.split("\\(")[0];
                    }

                    // Add the nutrient name and average
                    finalDataForDay.put(nutrientName.trim(), stringDoubleEntry.getValue());
                    total++; // Increment the total
                }
            }

            // Put the data in the finalData map to be returned
            finalData.put(stringMapEntry.getKey(), finalDataForDay);
        }

        // Return final data
        return finalData;
    }

    private LinkedHashMap<String, Map<String, Double>> averageOutNutrientsPerDay(LinkedHashMap<String, Map<String, Double>> data, Map<String, Integer> totalNutrientsForEachDay) {
        LinkedHashMap<String, Map<String, Double>> averageNutrientsPerDay = Maps.newLinkedHashMap(); // The calculated average nutrients per day

        for (Map.Entry<String, Map<String, Double>> stringLinkedHashMapEntry : data.entrySet()) { // Loop through the data

            String day = stringLinkedHashMapEntry.getKey(); // Get the day

            int totalNutrientsForThatDay = totalNutrientsForEachDay.getOrDefault(day, 0); // Get the total sum of nutrients for that day
            if (totalNutrientsForThatDay <= 0) continue; // If it's less than or equal to 0, continue

            // Call the calculateAverage method to calculate the average for the day
            // That method returns a list of nutrients and the average
            Map<String, Double> averageNutrients = calculateAverage(stringLinkedHashMapEntry.getValue(), totalNutrientsForThatDay);

            averageNutrientsPerDay.put(day, averageNutrients); // Put the data into the map to be returned
        }
        return averageNutrientsPerDay; // Return teh data
    }

    private static Map<String, Double> calculateAverage(Map<String, Double> nutrientsAndSums, int totalNutrientsForThatDay) {
        LinkedHashMap<String, Double> averageNutrients = Maps.newLinkedHashMap(); // Create a map that will be returned

        for (Map.Entry<String, Double> stringMapEntry : nutrientsAndSums.entrySet()) { // Loop through every nutrient and it's sum
            double sum = stringMapEntry.getValue(); // Get the sum
            double average = (sum / (double) totalNutrientsForThatDay); // Calculate the average
            averageNutrients.put(stringMapEntry.getKey(), average); // Add it to the map to be returned
        }

        // This will sort and return the average nutrients from higest to lowest
        return averageNutrients.entrySet().stream() // Loop through the data
                .sorted(Map.Entry.<String, Double>comparingByValue().reversed()) // Reverse it
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1, // Comparator to sort from highest average to lowest average
                        LinkedHashMap::new
                ));
    }

}
