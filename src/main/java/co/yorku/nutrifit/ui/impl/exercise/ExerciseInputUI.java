package co.yorku.nutrifit.ui.impl.exercise;

import co.yorku.nutrifit.NutriFit;
import co.yorku.nutrifit.exercise.IExercise;
import co.yorku.nutrifit.exercise.calculators.ExerciseCalculator;
import co.yorku.nutrifit.exercise.calculators.WeightLossCalculator;
import co.yorku.nutrifit.exercise.impl.ExerciseHandler;
import co.yorku.nutrifit.object.ActivityType;
import co.yorku.nutrifit.logs.impl.Exercise;
import co.yorku.nutrifit.object.Intensity;
import co.yorku.nutrifit.ui.NutrifitWindow;
import co.yorku.nutrifit.ui.impl.main.NutriFitMainUI;
import com.toedter.calendar.JDateChooser;

import java.util.Date;

import javax.swing.*;
import java.awt.*;


//Interface details to input user exercise information
public class ExerciseInputUI extends NutrifitWindow {


    private static ExerciseInputUI instance;

    public static ExerciseInputUI getInstance() {
        if (instance == null) {
            instance = new ExerciseInputUI();
        }
        return instance;
    }

    //ExerciseUI actions
    private ExerciseInputUI() {
        super("Exercise Input", new GridLayout(6, 2));

        IExercise exercise = new ExerciseHandler(new ExerciseCalculator(), new WeightLossCalculator());

        //Date input
        addLabel("Date", 1);
        JDateChooser dateChooser = new JDateChooser();
        dateChooser.setDateFormatString("yyyy-MM-dd");
        this.addComponent(dateChooser, 1);

        //Time the user did this at, (make a variable later of type Date)
        addLabel("Time (24:00)", 1);
        JTextField timeField = addTextField(5, 1);

        //Exercise input
        addLabel("Exercise", 1);
        JComboBox<Enum<?>> exerciseComboBox = addComboBox(ActivityType.values(), 1);

        //Intensity combo box
        addLabel("Intensity", 1);
        JComboBox<Enum<?>> intensityComboBox = addComboBox(Intensity.values(), 1);

        //Time in seconds
        addLabel("Seconds", 1);
        JTextField secondsField = addTextField(6, 1);


        //Save button for saving to DB
        addButton("Save", event -> {
            ActivityType activityType = (ActivityType) exerciseComboBox.getSelectedItem();
            Intensity intensity = (Intensity) intensityComboBox.getSelectedItem();
            int durationInSeconds = Integer.parseInt(secondsField.getText());
            int totalCaloriesBurned = exercise.getTotalCaloriesBurned(activityType, intensity, durationInSeconds, NutriFit.getInstance().getLoadedProfile());

            Date formattedDateTime = dateChooser.getDate();
            String[] timeSplit = timeField.getText().split(":");
            formattedDateTime.setHours(Integer.parseInt(timeSplit[0]));
            formattedDateTime.setMinutes(Integer.parseInt(timeSplit[1]));

            System.out.println(new Date(formattedDateTime.getTime()));

            Exercise exerciseObj = new Exercise(formattedDateTime, durationInSeconds, activityType, intensity, totalCaloriesBurned);

            NutriFit.getInstance().getUserDatabase().addUserExerciseLog(NutriFit.getInstance().getLoadedProfile().getId(), exerciseObj);
            JOptionPane.showMessageDialog(null, "Exercise Logging Successful!");

        },1);

        this.addBackButton(NutriFitMainUI.getInstance());
        this.build();
    }
}