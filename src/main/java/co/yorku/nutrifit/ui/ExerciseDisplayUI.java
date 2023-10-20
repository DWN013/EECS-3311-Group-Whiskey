package co.yorku.nutrifit.ui;

import co.yorku.nutrifit.NutriFit;
import co.yorku.nutrifit.logs.LogIterator;
import co.yorku.nutrifit.logs.impl.Exercise;

import javax.swing.*;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class ExerciseDisplayUI extends JFrame {

    public ExerciseDisplayUI() {

        // TODO: filter by like date and stuff, for now just show all

        Date fromDate = new Date(System.currentTimeMillis() - TimeUnit.DAYS.toMillis(9999999));
        Date toDate = new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(9999999));

        LogIterator exercises = NutriFit.getInstance().getUserDatabase().getUserExerciseLogs(NutriFit.getInstance().getLoadedProfile().getId(), fromDate, toDate);
        exercises.sortByDateAscending();
        String[] data = exercises.getTotalEntries() <= 0 ? new String[] { "No Exercises Logged" } : new String[exercises.getTotalEntries()];

        int index = 0;
        while (exercises.hasNext()) {
            Exercise exercise = (Exercise) exercises.getNext();
            String toShow = exercise.getDate().toString() + " [INTENSITY=" + exercise.getIntensity().toString() + "][ACTIVITY_TYPE=" + exercise.getActivityType().toString() + "][TIME_IN_SECONDS=" + exercise.getTimeSpentInSeconds() + "][CALORIES_BURNED=" + exercise.getTotalCaloriesBurned() + "]";
            data[index] = toShow;
            index++;
        }

        add(new JList(data));
        pack();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(false);

        // TODO: Have some button that when the user clicks "Submit"
        // It will call the EventManager to notify whatever UI that there has been an update
        // NutriFit.getInstance().getEventManager().notify(null, null);
    }

    public void showToUser() {
        setVisible(true);
    }
}
