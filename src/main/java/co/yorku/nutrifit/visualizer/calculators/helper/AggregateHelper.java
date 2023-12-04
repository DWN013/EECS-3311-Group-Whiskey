package co.yorku.nutrifit.visualizer.calculators.helper;

import com.google.common.collect.Maps;

import java.util.LinkedHashMap;
import java.util.Map;
/*

    This class is used to aggregate data

 */
public class AggregateHelper {

    public LinkedHashMap<String, Map<String, Double>> aggregateData(String otherCategory, LinkedHashMap<String, Map<String, Double>> data) {

        LinkedHashMap<String, Map<String, Double>> finalData = Maps.newLinkedHashMap(); // Final data to be returned

        for (Map.Entry<String, Map<String, Double>> stringMapEntry : data.entrySet()) { // Loop through all the sorted data
            // Put the data in the finalData map to be returned
            finalData.put(stringMapEntry.getKey(), aggregateKey(otherCategory, stringMapEntry));
        }

        // Return final data
        return finalData;
    }

    private Map<String, Double> aggregateKey(String otherCategory, Map.Entry<String, Map<String, Double>> stringMapEntry) {

        int total = 0; // Total number of nutrients
        Map<String, Double> finalDataForDay = Maps.newHashMap(); // Final averages to display for the day

        for (Map.Entry<String, Double> stringDoubleEntry : stringMapEntry.getValue().entrySet()) { // Loop through all the nutrients and it's averages
            if (total >= 5) { // If the total number of nutrients we are displaying is greater than or equal to 5, we will put everything else in an "Other Nutrients" category
                finalDataForDay.put(otherCategory, finalDataForDay.getOrDefault(otherCategory, 0.0) + stringDoubleEntry.getValue()); // Increment the average for the other nutrients category
            } else {

                String nutrientName = cleanupName(stringDoubleEntry.getKey()); // Get the nutrient tag
                // Add the nutrient name and average
                finalDataForDay.put(nutrientName.trim(), stringDoubleEntry.getValue());
                total++; // Increment the total
            }
        }
        return finalDataForDay;
    }

    private String cleanupName(String nutrientName) {
        nutrientName = Character.toUpperCase(nutrientName.charAt(0)) + nutrientName.substring(1).toLowerCase();
        if (nutrientName.contains(",")) { // Remove any commas and get the first element
            nutrientName = nutrientName.split(",")[0];
        }

        if (nutrientName.contains("(")) { // Remove any brackets and get the first element
            nutrientName = nutrientName.split("\\(")[0];
        }
        return nutrientName;
    }

}
