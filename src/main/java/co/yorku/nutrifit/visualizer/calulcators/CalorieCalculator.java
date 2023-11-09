package co.yorku.nutrifit.visualizer.calulcators;

import co.yorku.nutrifit.NutriFit;
import co.yorku.nutrifit.logs.LogIterator;
import co.yorku.nutrifit.logs.impl.Meal;
import com.google.common.collect.Maps;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

public class CalorieCalculator {

    public LinkedHashMap<String, Integer> getCalories(Date dateFrom, Date dateTo) {

        LogIterator logIterator = NutriFit.getInstance().getUserDatabase().getUserMealLogs(
                NutriFit.getInstance().getLoadedProfile().getId(),
                dateFrom,
                dateTo
        );

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM dd yyyy");

        logIterator.sortByDateAscending();

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
