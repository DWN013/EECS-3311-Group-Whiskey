package co.yorku.nutrifit.visualizer.calulcators;

import co.yorku.nutrifit.NutriFit;
import co.yorku.nutrifit.logs.LogIterator;
import co.yorku.nutrifit.logs.impl.Meal;
import co.yorku.nutrifit.object.AverageCalculator;
import co.yorku.nutrifit.object.NutrientData;
import co.yorku.nutrifit.object.FoodNutrientInfo;
import com.google.common.collect.Maps;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class NutrientCalculator {

    public LinkedHashMap<String, Map<String, Double>> getNutrientInfoPerDate(Date dateFrom, Date dateTo) {

        LogIterator logIterator = NutriFit.getInstance().getUserDatabase().getUserMealLogs(
                NutriFit.getInstance().getLoadedProfile().getId(),
                dateFrom,
                dateTo
        );
        logIterator.sortByDateAscending();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM dd yyyy");
        LinkedHashMap<String, Map<String, AverageCalculator>> data = Maps.newLinkedHashMap(); // <Day, <NutrientID, <SumOfNutrientValueToday, TotalNutrientValues>>>

        while (logIterator.hasNext()) {
            Meal meal = (Meal)  logIterator.getNext();
            Date date = meal.getDate();
            String dayKey = simpleDateFormat.format(date);

            Map<String,AverageCalculator> nutrientsPerDay = data.computeIfAbsent(dayKey, k -> Maps.newLinkedHashMap()); // holds the nutrient ID, the sum of nutrient values for that day and the total amount added to that sum so we can calc. the average later

            for (Map.Entry<Integer, Integer> integerIntegerEntry : meal.getFoodIDAndAmounts().entrySet()) { // get all the food ids and amount

                FoodNutrientInfo foodNutrientInfo = NutriFit.getInstance().getNutrientDatabase().getNutrientInfo(integerIntegerEntry.getKey()); // get nutrient information about the food

                for (Map.Entry<Integer, Double> integerDoubleEntry : foodNutrientInfo.getNutrientIDAndNutrientValue().entrySet()) { // for every nutrient and it's nutrient value
                    NutrientData nutrientData = NutriFit.getInstance().getNutrientDatabase().getNutrientData(integerDoubleEntry.getKey());
                    if (nutrientData == null) continue;

                    AverageCalculator averageCalculator = nutrientsPerDay.computeIfAbsent(nutrientData.getName(), id -> new AverageCalculator());

                    double toAdd = integerDoubleEntry.getValue();;

                    if (nutrientData.getUnit().equals("mg") && toAdd > 0.0) { // convert to g
                        toAdd/=1000;
                    }

                    averageCalculator.add(toAdd);
                }
            }

        }

        LinkedHashMap<String, Map<String, Double>> calculatedData = averageOutNutrientsPerDay(data);

        return aggregateData(calculatedData);
    }

    private LinkedHashMap<String, Map<String, Double>> aggregateData(LinkedHashMap<String, Map<String, Double>> data) {

        LinkedHashMap<String, Map<String, Double>> finalData = Maps.newLinkedHashMap();

        for (Map.Entry<String, Map<String, Double>> stringMapEntry : data.entrySet()) {

            int total = 0;
            Map<String, Double> finalDataForDay = Maps.newHashMap();

            for (Map.Entry<String, Double> stringDoubleEntry : stringMapEntry.getValue().entrySet()) {
                if (total >= 5) {
                    finalDataForDay.put("Other Nutrients", finalDataForDay.getOrDefault("Other Nutrients", 0.0) + stringDoubleEntry.getValue());
                } else {

                    String nutrientTag = stringDoubleEntry.getKey();
                    nutrientTag = Character.toUpperCase(nutrientTag.charAt(0)) + nutrientTag.substring(1);
                    if (nutrientTag.contains(",")) {
                        nutrientTag = nutrientTag.split(",")[0];
                    }

                    if (nutrientTag.contains("(")) {
                        nutrientTag = nutrientTag.split("\\(")[0];
                    }

                    finalDataForDay.put(nutrientTag.trim(), stringDoubleEntry.getValue());
                    total++;
                }
            }

            finalData.put(stringMapEntry.getKey(), finalDataForDay);
        }

        return finalData;
    }

    private LinkedHashMap<String, Map<String, Double>> averageOutNutrientsPerDay(LinkedHashMap<String, Map<String, AverageCalculator>> data) {
        LinkedHashMap<String, Map<String, Double>> averageNutrientsPerDay = Maps.newLinkedHashMap();

        for (Map.Entry<String, Map<String, AverageCalculator>> stringLinkedHashMapEntry : data.entrySet()) {

            String day = stringLinkedHashMapEntry.getKey();
            Map<String, Double> averageNutrients = calculateAverage(stringLinkedHashMapEntry);

            averageNutrientsPerDay.put(day, averageNutrients);
        }
        return averageNutrientsPerDay;
    }

    private static Map<String, Double> calculateAverage(Map.Entry<String, Map<String,AverageCalculator>> stringLinkedHashMapEntry) {
        LinkedHashMap<String, Double> averageNutrients = Maps.newLinkedHashMap();

        for (Map.Entry<String, AverageCalculator> averageCalculatorEntry : stringLinkedHashMapEntry.getValue().entrySet()) {
            AverageCalculator averageCalculator = averageCalculatorEntry.getValue();
            double average = averageCalculator.calculateAverage();
            if (average <= 0) continue; // skip, no nutrient values found

            averageNutrients.put(averageCalculatorEntry.getKey(), average);
        }

        return averageNutrients.entrySet().stream()
                .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));
    }

}
