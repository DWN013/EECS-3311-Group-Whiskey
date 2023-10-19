package co.yorku.nutrifit.ui;

import co.yorku.nutrifit.database.userdata.IUserDatabase;
import co.yorku.nutrifit.object.Exercise;
import co.yorku.nutrifit.object.Meal;
import co.yorku.nutrifit.profile.IProfile;

import javax.swing.*;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class ExerciseDisplayUI extends JFrame {

    public ExerciseDisplayUI(IUserDatabase iUserDatabase, IProfile iProfile) {

        // TODO: filter by like date and stuff, for now just show all

        Date fromDate = new Date(System.currentTimeMillis() - TimeUnit.DAYS.toMillis(9999999));
        Date toDate = new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(9999999));

        List<Exercise> exercises = iUserDatabase.getUserExerciseLogs(iProfile.getId(), fromDate, toDate);
        String[] data = exercises.isEmpty() ? new String[] { "No Exercises Logged" } : new String[exercises.size()];

        int index = 0;
        for (Exercise exercise : exercises) {
            String toShow = exercise.getDate().toString() + " [INTENSITY=" + exercise.getIntensity().toString() + "][ACTIVITY_TYPE=" + exercise.getActivityType().toString() + "][TIME_IN_SECONDS=" + exercise.getTimeSpentInSeconds() + "][CALORIES_BURNED=" + exercise.getTotalCaloriesBurned() + "]";
            data[index] = toShow;
            index++;
        }

        add(new JList(data));
        pack();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

    }

}
