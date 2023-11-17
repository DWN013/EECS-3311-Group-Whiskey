package co.yorku.nutrifit.ui.impl.exercise;

import co.yorku.nutrifit.NutriFit;
import co.yorku.nutrifit.logs.LogIterator;
import co.yorku.nutrifit.logs.impl.Exercise;
import co.yorku.nutrifit.ui.NutrifitWindow;
import javax.swing.*;
import java.awt.*;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/*
 * UI class define the "Show Exercise Log" UI within the Exercise Menu
 * This class will show all users existing exercise logs in the database 
 */

public class ExerciseDisplayUI extends NutrifitWindow {

    public ExerciseDisplayUI() {
        super("Exercise Display", new GridLayout());

        //Get first possible date and last possible date so that we are able to get all logs in the database
        Date fromDate = new Date(System.currentTimeMillis() - TimeUnit.DAYS.toMillis(9999999));
        Date toDate = new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(9999999));

        //Getting all exercise logs in the database for the user
        LogIterator exercises = NutriFit.getInstance().getUserDatabase().getUserExerciseLogs(NutriFit.getInstance().getLoadedProfile().getId(), fromDate, toDate);
        exercises.sortByDateAscending();
        //If no entries, show to the user that there are "No exercises logged"
        String[] data = exercises.getTotalEntries() <= 0 ? new String[] { "No Exercises Logged" } : new String[exercises.getTotalEntries()];

        //If there are exercise entries, go through all entries retrieved from the database
        int index = 0;
        while (exercises.hasNext()) {
            Exercise exercise = (Exercise) exercises.getNext();
            //Print out exercises in format to user
            String toShow = exercise.getDate().toString() + " [INTENSITY=" + exercise.getIntensity().toString() + "][ACTIVITY_TYPE=" + exercise.getActivityType().toString() + "][TIME_IN_SECONDS=" + exercise.getTimeSpentInSeconds() + "][CALORIES_BURNED=" + exercise.getTotalCaloriesBurned() + "]";
            data[index] = toShow;
            index++;
        }

        //add the data + back button to the UI page
        this.addComponent(new JList<>(data));
        this.addBackButton(MainExerciseMenu.getInstance());
        this.build();
    }

    //Method will allow us to show UI page to user after created
    public void showToUser() {
        setVisible(true);
    }
}
