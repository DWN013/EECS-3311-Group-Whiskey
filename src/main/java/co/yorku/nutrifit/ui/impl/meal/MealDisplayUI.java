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

/*
 * MealDisplayUI will show all the meal logs existing in the database to the user 
 */

public class MealDisplayUI extends NutrifitWindow {

    public MealDisplayUI() {
        super("Exercise Display", new GridLayout());
        // Set the date range from 9999999 days ago to 9999999 days in the future
        Date fromDate = new Date(System.currentTimeMillis() - TimeUnit.DAYS.toMillis(9999999));
        Date toDate = new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(9999999));
        // Retrieve meal logs for the user within the specified date range
        LogIterator meals = NutriFit.getInstance().getUserDatabase().getUserMealLogs(NutriFit.getInstance().getLoadedProfile().getId(), fromDate, toDate);
        meals.sortByDateAscending();
        // Initialize an array to store data for display in the UI
        String[] data = meals.getTotalEntries() <= 0 ? new String[] { "No Meals Logged" } : new String[meals.getTotalEntries()];
        // Index variable for populating the 'data' array
        int index = 0;
        //Loop through meal logs
        while (meals.hasNext()) {
            // Get the next meal from the iterator
            Meal meal = (Meal) meals.getNext();
            // String to store formatted representation of food ID and amounts
            String mapAsANiceString = "";
            // Concatenate the food name and amount in a nicer format
            for (Map.Entry<Integer, Integer> stringIntegerEntry : meal.getFoodIDAndAmounts().entrySet()) {
                mapAsANiceString += "[" + NutriFit.getInstance().getNutrientDatabase().getFoodInfo(stringIntegerEntry.getKey()).getFoodName() + "=" + stringIntegerEntry.getValue() + "]";
            }
            // String to represent meal's details
            String toShow = meal.getDate().toString() + " [TYPE=" + meal.getMealType().name() + ", TOTAL_CALORIES=" + meal.getTotalMealCalories() + "]: " + mapAsANiceString;
            // Assign the formatted string to the corresponding index in the data array
            data[index] = toShow;
            index++;
        }

        this.addComponent(new JList<>(data));
        this.addBackButton(NutriFitMainUI.getInstance());
        this.build();
    }
}
