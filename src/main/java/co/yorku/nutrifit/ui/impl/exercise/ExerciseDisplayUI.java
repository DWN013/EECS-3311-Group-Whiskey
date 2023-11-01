package co.yorku.nutrifit.ui.impl.exercise;

import co.yorku.nutrifit.NutriFit;
import co.yorku.nutrifit.logs.LogIterator;
import co.yorku.nutrifit.logs.impl.Exercise;
import co.yorku.nutrifit.ui.NutrifitWindow;

import javax.swing.*;
import java.awt.*;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class ExerciseDisplayUI extends NutrifitWindow {

    public ExerciseDisplayUI() {
        super("Exercise Display", new GridLayout());
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

        this.addComponent(new JList<>(data));
        this.build();
    }

    public void showToUser() {
        setVisible(true);
    }
}
