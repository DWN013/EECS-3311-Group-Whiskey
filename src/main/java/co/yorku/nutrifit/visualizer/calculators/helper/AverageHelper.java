package co.yorku.nutrifit.visualizer.calculators.helper;

import com.google.common.collect.Maps;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;
/*

    This class is used help calculate averages

 */
public class AverageHelper {

    public LinkedHashMap<String, Map<String, Double>> averageOutDataPerDay(LinkedHashMap<String, Map<String, Double>> data, Map<String, Integer> totalKV) {
        LinkedHashMap<String, Map<String, Double>> averageData = Maps.newLinkedHashMap(); // The calculated average nutrients per day

        for (Map.Entry<String, Map<String, Double>> stringLinkedHashMapEntry : data.entrySet()) { // Loop through the data

            String day = stringLinkedHashMapEntry.getKey(); // Get the day

            int sumForKey = totalKV.getOrDefault(day, 0); // Get the total sum of nutrients for that day
            if (sumForKey <= 0) continue; // If it's less than or equal to 0, continue

            // Call the calculateAverage method to calculate the average for the day
            // That method returns a list of data and the average
            Map<String, Double> averageKeyData = calculateAverage(stringLinkedHashMapEntry.getValue(), sumForKey);

            averageData.put(day, averageKeyData); // Put the data into the map to be returned
        }
        return averageData; // Return teh data
    }

    public Map<String, Double> calculateAverage(Map<String, Double> keyAndSums, int totalEntries) {
        Map<String, Double> averageData = Maps.newLinkedHashMap(); // Create a map that will be returned

        for (Map.Entry<String, Double> stringMapEntry : keyAndSums.entrySet()) { // Loop through every key and it's sum
            double sum = stringMapEntry.getValue(); // Get the sum
            double average = (sum / (double) totalEntries); // Calculate the average
            averageData.put(stringMapEntry.getKey(), average); // Add it to the map to be returned
        }

        // This will sort and return the average nutrients from higest to lowest
        return new SortHelper().sort(averageData);
    }


}
