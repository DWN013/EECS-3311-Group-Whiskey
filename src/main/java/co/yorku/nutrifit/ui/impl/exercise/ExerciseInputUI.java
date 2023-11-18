package co.yorku.nutrifit.ui.impl.exercise;

import co.yorku.nutrifit.NutriFit;
import co.yorku.nutrifit.exercise.IExercise;
import co.yorku.nutrifit.exercise.calculators.ExerciseCalculator;
import co.yorku.nutrifit.exercise.impl.ExerciseHandler;
import co.yorku.nutrifit.object.ActivityType;
import co.yorku.nutrifit.logs.impl.Exercise;
import co.yorku.nutrifit.object.Intensity;
import co.yorku.nutrifit.ui.NutrifitWindow;
import com.toedter.calendar.JDateChooser;

import java.util.Date;

import javax.swing.*;
import java.awt.*;

/*
 * Interface details to input user exercise information
 */

public class ExerciseInputUI extends NutrifitWindow {


    private static ExerciseInputUI instance; //Store instance of ExerciseInputUI

    //Implementation of Singleton Design Pattern by keeping instance of ExerciseInputUI page. This instance can be used by our other UI classes
    public static ExerciseInputUI getInstance() {
        if (instance == null) {
            instance = new ExerciseInputUI();
        }
        return instance;
    }

    //ExerciseUI actions
    private ExerciseInputUI() {
        super("Exercise Input", new GridLayout(6, 2));

        //Create IExercise object to record exercises the user may input
        IExercise exercise = new ExerciseHandler(new ExerciseCalculator());

        //Date input
        addLabel("Date");
        JDateChooser dateChooser = new JDateChooser();
        dateChooser.setDateFormatString("yyyy-MM-dd");
        this.addComponent(dateChooser);

        //Time user did Exercise label and prompt
        addLabel("Time (24:00)");
        JTextField timeField = addTextField(5);

        //Exercise input
        addLabel("Exercise");
        JComboBox<Enum<?>> exerciseComboBox = addComboBox(ActivityType.values());

        //Intensity combo box
        addLabel("Intensity");
        JComboBox<Enum<?>> intensityComboBox = addComboBox(Intensity.values());

        //Time in seconds
        addLabel("Seconds");
        JTextField secondsField = addTextField(6);


        //Save button for saving to DB
        addButton("Save", event -> {
        	//Get user inputted data and store into variables
            ActivityType activityType = (ActivityType) exerciseComboBox.getSelectedItem();
            Intensity intensity = (Intensity) intensityComboBox.getSelectedItem();
            int durationInSeconds = Integer.parseInt(secondsField.getText());
            int totalCaloriesBurned = exercise.getTotalCaloriesBurned(activityType, intensity, durationInSeconds, NutriFit.getInstance().getLoadedProfile());

            //Format the date entered by user 
            Date formattedDateTime = dateChooser.getDate();
            String[] timeSplit = timeField.getText().split(":");
            formattedDateTime.setHours(Integer.parseInt(timeSplit[0]));
            formattedDateTime.setMinutes(Integer.parseInt(timeSplit[1]));

            //Create object for exercise with the data inputted by the user
            Exercise exerciseObj = new Exercise(formattedDateTime, durationInSeconds, activityType, intensity, totalCaloriesBurned);

            //Store new exercise log into the database
            NutriFit.getInstance().getUserDatabase().addUserExerciseLog(NutriFit.getInstance().getLoadedProfile().getId(), exerciseObj);
            
            //Confirmation message shown to user for successful logging
            JOptionPane.showMessageDialog(null, "Exercise Logging Successful!");

        });

        //Create a button and add all the newly created JFrame elements to the UI
        this.addBackButton(MainExerciseMenu.getInstance());
        this.build();
    }
}