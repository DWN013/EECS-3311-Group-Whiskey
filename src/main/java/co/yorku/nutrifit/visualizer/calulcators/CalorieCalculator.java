package co.yorku.nutrifit.visualizer.calulcators;

import co.yorku.nutrifit.NutriFit;
import co.yorku.nutrifit.logs.LogIterator;
import co.yorku.nutrifit.logs.impl.Meal;
import com.google.common.collect.Maps;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;

public class CalorieCalculator {

    public LinkedHashMap<String, Integer> getCaloriesPerDay(Date dateFrom, Date dateTo) {

        LogIterator logIterator = NutriFit.getInstance().getUserDatabase().getUserMealLogs(
                NutriFit.getInstance().getLoadedProfile().getId(),
                dateFrom,
                dateTo
        );
        logIterator.sortByDateAscending();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM dd yyyy");
        LinkedHashMap<String, Integer> caloriesPerDay = Maps.newLinkedHashMap();

        while (logIterator.hasNext()) {
            Meal meal = (Meal)  logIterator.getNext();
            Date date = meal.getDate();
            String key = simpleDateFormat.format(date);

            caloriesPerDay.put(key, caloriesPerDay.getOrDefault(key, 0) + meal.getTotalMealCalories());
        }


        return caloriesPerDay;
    }

}
