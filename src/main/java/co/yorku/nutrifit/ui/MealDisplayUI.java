package co.yorku.nutrifit.ui;

import co.yorku.nutrifit.database.userdata.IUserDatabase;
import co.yorku.nutrifit.object.Meal;
import co.yorku.nutrifit.profile.IProfile;

import javax.swing.*;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class MealDisplayUI extends JFrame {

    public MealDisplayUI(IUserDatabase iUserDatabase, IProfile iProfile) {

        // TODO: filter by like date and stuff, for now just show all

        Date fromDate = new Date(System.currentTimeMillis() - TimeUnit.DAYS.toMillis(9999999));
        Date toDate = new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(9999999));

        List<Meal> meals = iUserDatabase.getUserMealLogs(iProfile.getId(), fromDate, toDate);
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
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

    }

}
