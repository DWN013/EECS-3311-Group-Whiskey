package co.yorku.nutrifit.visualizer.calculators;

import co.yorku.nutrifit.NutriFit;
import co.yorku.nutrifit.logs.LogIterator;
import co.yorku.nutrifit.logs.impl.Meal;
import co.yorku.nutrifit.object.daterange.DateRange;
import com.google.common.collect.Maps;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
/*
    This is the CalorieCalculator.
    It's purpose is to calculate the total calories consumed
    over a specific time period.
 */
public class CalorieCalculator {

    // This method returns a LinkedHashMap of type String, String which contain
    // a day and the total number of calories consumed for that day
    public LinkedHashMap<String, Integer> getCaloriesPerDay(DateRange dateRange) {

        // Query The Database for the specific date range
        LogIterator logIterator = NutriFit.getInstance().getUserDatabase().getUserMealLogs(
                NutriFit.getInstance().getLoadedProfile().getId(),
                dateRange
        );
        logIterator.sortByDateAscending(); // Sort data by ascending

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM dd yyyy"); // Create a SimpleDateFormat object to format Date objects
        LinkedHashMap<String, Integer> caloriesPerDay = Maps.newLinkedHashMap(); // Create a map that will be returned by the function

        while (logIterator.hasNext()) { // Loop through every meal that was returned from the database
            Meal meal = (Meal)  logIterator.getNext(); // Get the meal object
            Date date = meal.getDate(); // Get the date from the meal object
            String key = simpleDateFormat.format(date); // Format the date

            // Increment the total calories consumed for the day
            caloriesPerDay.put(key, caloriesPerDay.getOrDefault(key, 0) + meal.getTotalMealCalories());
        }

        // Return the map that contains the data
        return caloriesPerDay;
    }

}
