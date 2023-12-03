package co.yorku.nutrifit.visualizer.calculators.helper;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class SortHelper {

    public Map<String, Double> sort(Map<String, Double> data) {
        return data.entrySet().stream() // Loop through the data
                .sorted(Map.Entry.<String, Double>comparingByValue().reversed()) // Reverse it
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1, // Comparator to sort from highest average to lowest average
                        LinkedHashMap::new
                ));
    }

}
