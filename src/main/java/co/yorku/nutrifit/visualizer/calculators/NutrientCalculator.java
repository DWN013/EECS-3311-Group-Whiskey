package co.yorku.nutrifit.visualizer.calculators;

import co.yorku.nutrifit.NutriFit;
import co.yorku.nutrifit.logs.LogIterator;
import co.yorku.nutrifit.logs.impl.Meal;
import co.yorku.nutrifit.object.NutrientData;
import co.yorku.nutrifit.object.FoodNutrientInfo;
import co.yorku.nutrifit.visualizer.calculators.helper.AggregateHelper;
import co.yorku.nutrifit.visualizer.calculators.helper.AverageHelper;
import com.google.common.collect.Maps;

import java.text.SimpleDateFormat;
import java.util.*;

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
                this.updateFoodData(dayKey, foodNutrientInfo, totalNutrientsForEachDay, nutrientsPerDay, integerIntegerEntry.getValue());
            }

        }

        return new AggregateHelper().aggregateData("Other Nutrients", new AverageHelper().averageOutDataPerDay(data, totalNutrientsForEachDay)); // This perform final calculations
    }

    private void updateFoodData(String dayKey,
                                FoodNutrientInfo foodNutrientInfo,
                                Map<String, Integer> totalNutrientsForEachDay,
                                Map<String,Double> nutrientsPerDay,
                                double value) {
        for (Map.Entry<Integer, Double> integerDoubleEntry : foodNutrientInfo.getNutrientIDAndNutrientValue().entrySet()) { // Loop through all the specific nutrients found in this specific food

            // If the nutrient value is less than or equal to 0, continue
            if (integerDoubleEntry.getValue() <= 0.0) continue;

            NutrientData nutrientData = NutriFit.getInstance().getNutrientDatabase().getNutrientData(integerDoubleEntry.getKey()); // Query Database for the NutrientData for the specific Nutrient
            if (nutrientData == null) continue; // If it's null, we continue
            // Get the total sum of the specific nutrient
            double sum = nutrientsPerDay.computeIfAbsent(nutrientData.getName(), id -> 0.0);

            // Check if the unit is mg, convert it to g
            if (nutrientData.getUnit().equals("mg")) {
                sum+=((integerDoubleEntry.getValue()/1000) * value); // Add it to the sum and multiply by the meal quantity
            } else {
                sum+=(integerDoubleEntry.getValue() * value); // Add it to the sum and multiply by the meal quantity
            }

            nutrientsPerDay.put(nutrientData.getName(), sum); // Update our nutrient map with new sum
            totalNutrientsForEachDay.put(dayKey, totalNutrientsForEachDay.getOrDefault(dayKey, 0) + 1);
        }
    }

}
