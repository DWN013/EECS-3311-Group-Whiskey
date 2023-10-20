package co.yorku.nutrifit.ui;

import co.yorku.nutrifit.NutriFit;
import co.yorku.nutrifit.object.Meal;

import javax.swing.*;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class MealDisplayUI extends JFrame {

    private static MealDisplayUI instance;

    public static MealDisplayUI getInstance() {
        if (instance == null) {
            instance = new MealDisplayUI();
        }
        return instance;
    }

    private MealDisplayUI() {

        // TODO: filter by like date and stuff, for now just show all

        Date fromDate = new Date(System.currentTimeMillis() - TimeUnit.DAYS.toMillis(9999999));
        Date toDate = new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(9999999));

        List<Meal> meals = NutriFit.getInstance().getUserDatabase().getUserMealLogs(NutriFit.getInstance().getLoadedProfile().getId(), fromDate, toDate);
        meals.sort(Comparator.comparingLong(lhs -> lhs.getDate().getTime()));
        String[] data = meals.isEmpty() ? new String[] { "No Meals Logged" } : new String[meals.size()];

        int index = 0;
        for (Meal meal : meals) {

            String mapAsANiceString = "";

            for (Map.Entry<String, Integer> stringIntegerEntry : meal.getIngredientsAndQuantities().entrySet()) {
                mapAsANiceString += "[" + stringIntegerEntry.getKey() + "=" + stringIntegerEntry.getValue() + "]";
            }

            String toShow = meal.getDate().toString() + " [TYPE=" + meal.getMealType().name() + ", TOTAL_CALORIES=" + meal.getTotalMealCalories() + "]: " + mapAsANiceString;

            data[index] = toShow;
            index++;
        }

        add(new JList(data));
        pack();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(false);

    }

    public void showToUser() {
        setVisible(true);
    }
}
