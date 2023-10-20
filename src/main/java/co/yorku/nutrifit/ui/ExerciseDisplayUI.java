package co.yorku.nutrifit.ui;

import co.yorku.nutrifit.NutriFit;
import co.yorku.nutrifit.database.userdata.IUserDatabase;
import co.yorku.nutrifit.object.Exercise;
import co.yorku.nutrifit.object.Meal;
import co.yorku.nutrifit.profile.IProfile;

import javax.swing.*;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class ExerciseDisplayUI extends JFrame {

    private static ExerciseDisplayUI instance;

    public static ExerciseDisplayUI getInstance() {
        if (instance == null) {
            instance = new ExerciseDisplayUI();
        }
        return instance;
    }

    private ExerciseDisplayUI() {

        // TODO: filter by like date and stuff, for now just show all

        Date fromDate = new Date(System.currentTimeMillis() - TimeUnit.DAYS.toMillis(9999999));
        Date toDate = new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(9999999));

        List<Exercise> exercises = NutriFit.getInstance().getUserDatabase().getUserExerciseLogs(NutriFit.getInstance().getLoadedProfile().getId(), fromDate, toDate);
        exercises.sort(Comparator.comparingLong(lhs -> lhs.getDate().getTime()));
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
        setVisible(false);

        // TODO: Have some button that when the user clicks "Submit"
        // It will call the EventManager to notify whatever UI that there has been an update
        // NutriFit.getInstance().getEventManager().notify(null, null);
    }

    public void showToUser() {
        setVisible(true);
    }
}
