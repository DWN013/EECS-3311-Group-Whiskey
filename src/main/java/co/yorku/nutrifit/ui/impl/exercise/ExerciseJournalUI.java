package co.yorku.nutrifit.ui.impl.exercise;

import co.yorku.nutrifit.NutriFit;
import co.yorku.nutrifit.logs.LogIterator;
import co.yorku.nutrifit.logs.impl.Exercise;
import co.yorku.nutrifit.logs.impl.Meal;
import co.yorku.nutrifit.ui.NutrifitWindow;
import co.yorku.nutrifit.ui.impl.meal.MealDisplayUI;
import co.yorku.nutrifit.ui.impl.meal.MealJournalUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/*
 * This class will show all users existing exercise logs in the database 
 */

public class ExerciseJournalUI extends NutrifitWindow {

    public ExerciseJournalUI() {
        super("Exercise Display", new GridLayout(2, 1));

        //Get first possible date and last possible date so that we are able to get all logs in the database
        Date fromDate = new Date(System.currentTimeMillis() - TimeUnit.DAYS.toMillis(9999999));
        Date toDate = new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(9999999));

        //Getting all exercise logs in the database for the user
        LogIterator exercises = NutriFit.getInstance().getUserDatabase().getUserExerciseLogs(NutriFit.getInstance().getLoadedProfile().getId(), fromDate, toDate);
        exercises.sortByDateAscending();

        DefaultTableModel defaultTableModel = new DefaultTableModel() // Create the default table model
        {
            @Override
            public boolean isCellEditable(int row, int column) { // Override isCellEditable and return false because we don't want the user editing the cell
                return false;
            }
        };

        // Add our columns
        defaultTableModel.addColumn("Date");
        defaultTableModel.addColumn("Activity Type");
        defaultTableModel.addColumn("Intensity");
        defaultTableModel.addColumn("Duration");
        defaultTableModel.addColumn("Calories Burned");

        JTable table = new JTable(defaultTableModel); // Create a JTable
        JScrollPane jScrollPane = new JScrollPane(table); // Create a scroller pane
        jScrollPane.setPreferredSize(new Dimension(600, 400)); // Set the height

        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd yyyy"); // Create a SimpleDateFormat object to format Date objects

        //Loop through exercise logs
        while (exercises.hasNext()) {
            // Get the next meal from the iterator
            Exercise meal = (Exercise) exercises.getNext();
            // Format the day
            String day = dateFormat.format(meal.getDate());

            int totalSeconds = meal.getTimeSpentInSeconds(); // Get the total seconds
            long hours = totalSeconds / 3600; // Calculate the hours
            long minutes = (totalSeconds % 3600) / 60; // Calculate the minutes
            long seconds = totalSeconds % 60; // Calculate the seconds

            StringBuilder duration = new StringBuilder(); // Create a stringbuilder
            if (hours > 0) { // Check and append hours to the stringbuilder
                duration.append(hours).append(" hr");
                if (hours > 1) {
                    duration.append("s");
                }
                duration.append(" ");
            }
            if (minutes > 0) { // Check and append minutes to the stringbuilder
                duration.append(minutes).append(" min");
                if (minutes > 1) {
                    duration.append("s");
                }
                duration.append(" ");
            }
            if (seconds > 0) { // Check and append seconds to the stringbuilder
                duration.append(seconds).append(" sec");
            }


            // Add the row
            defaultTableModel.addRow(new Object[] { day, meal.getActivityType().getDisplayName(), meal.getIntensity().getDisplayName(), duration.toString(), meal.getTotalCaloriesBurned()});
        }

        //add the data + back button to the UI page
        this.addComponent(jScrollPane);
        this.addBackButton(MainExerciseMenu.getInstance());
        this.build();
    }

    //Method will allow us to show UI page to user after created
    public void showToUser() {
        setVisible(true);
    }
}
