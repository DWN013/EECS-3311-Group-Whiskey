package co.yorku.nutrifit.ui.impl.meal;

import co.yorku.nutrifit.NutriFit;
import co.yorku.nutrifit.logs.LogIterator;
import co.yorku.nutrifit.logs.impl.Meal;
import co.yorku.nutrifit.ui.NutrifitWindow;
import co.yorku.nutrifit.ui.impl.main.NutriFitMainUI;

import javax.swing.*;
import java.awt.*;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class MealDisplayUI extends NutrifitWindow {

    public MealDisplayUI() {
        super("Exercise Display", new GridLayout());

        Date fromDate = new Date(System.currentTimeMillis() - TimeUnit.DAYS.toMillis(9999999));
        Date toDate = new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(9999999));

        LogIterator meals = NutriFit.getInstance().getUserDatabase().getUserMealLogs(NutriFit.getInstance().getLoadedProfile().getId(), fromDate, toDate);
        meals.sortByDateAscending();
        String[] data = meals.getTotalEntries() <= 0 ? new String[] { "No Meals Logged" } : new String[meals.getTotalEntries()];

        int index = 0;
        while (meals.hasNext()) {
            Meal meal = (Meal) meals.getNext();

            String mapAsANiceString = "";

            for (Map.Entry<Integer, Integer> stringIntegerEntry : meal.getFoodIDAndAmounts().entrySet()) {
                mapAsANiceString += "[" + NutriFit.getInstance().getNutrientDatabase().getFoodInfo(stringIntegerEntry.getKey()).getFoodName() + "=" + stringIntegerEntry.getValue() + "]";
            }

            String toShow = meal.getDate().toString() + " [TYPE=" + meal.getMealType().name() + ", TOTAL_CALORIES=" + meal.getTotalMealCalories() + "]: " + mapAsANiceString;

            data[index] = toShow;
            index++;
        }

        this.addComponent(new JList<>(data));
        this.addBackButton(NutriFitMainUI.getInstance());
        this.build();
    }
}
