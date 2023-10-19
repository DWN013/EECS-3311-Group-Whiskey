package co.yorku.nutrifit.ui;

import co.yorku.nutrifit.database.userdata.IUserDatabase;
import co.yorku.nutrifit.exercise.IExercise;
import co.yorku.nutrifit.exercise.calculators.ExerciseCalculator;
import co.yorku.nutrifit.exercise.calculators.WeightLossCalculator;
import co.yorku.nutrifit.exercise.impl.ActivityCalorieTemp;
import co.yorku.nutrifit.exercise.impl.ExerciseHandler;
import co.yorku.nutrifit.object.ActivityType;
import co.yorku.nutrifit.object.Exercise;
import co.yorku.nutrifit.object.Intensity;
import co.yorku.nutrifit.object.Meal;
import co.yorku.nutrifit.profile.IProfile;
import com.toedter.calendar.JDateChooser;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.*;
import java.awt.*;


//Interface details to input user exercise information
public class ExerciseInputUI extends JFrame{

    //ExerciseUI actions
    public ExerciseInputUI(IUserDatabase userDatabaseInterface, int userId){
        //Title of window that opens when "Log Exercise" button pressed
        super("Exercise UI");
        IProfile profile = userDatabaseInterface.getProfile(userId);
        IExercise exercise = new ExerciseHandler(new ExerciseCalculator(), new WeightLossCalculator());
        //Debug line
        System.out.println("Loaded exercise input for " + profile.getId() + " -> " + profile.getName());

        //New exercise panel to input information
        JPanel newExercisePanel = new JPanel(new GridLayout(6, 2));
        //Date input
        JLabel dateLabel = new JLabel("Date");
        JDateChooser dateChooser = new JDateChooser();
        dateChooser.setDateFormatString("yyyy-MM-dd");
        //Time the user did this at, (make a variable later of type Date)
        JLabel timeLabel = new JLabel("Time (24:00)");
        JTextField timeField = new JTextField(5);
        //Exercise input
        JLabel exerciseLabel = new JLabel("Exercise");
        JComboBox<ActivityType> exerciseComboBox = new JComboBox<>(ActivityType.values());
        //Intensity combo box
        JLabel intensityLabel = new JLabel("Intensity");
        JComboBox<Intensity> intensityComboBox = new JComboBox<>(Intensity.values());
        //Time in seconds
        JLabel secondsLabel = new JLabel("Seconds");
        JTextField secondsField = new JTextField(6);


        //Save button for saving to DB
        JButton saveButton = new JButton("Save");
        //Action listener for save button
        saveButton.addActionListener(e -> {
            ActivityType activityType = (ActivityType) exerciseComboBox.getSelectedItem();
            Intensity intensity = (Intensity) intensityComboBox.getSelectedItem();
            int durationInSeconds = Integer.parseInt(secondsField.getText());
            int totalCaloriesBurned = exercise.getTotalCaloriesBurned(activityType, intensity, durationInSeconds, profile);

            Date formattedDateTime = dateChooser.getDate();
            String[] timeSplit = timeField.getText().split(":");
            formattedDateTime.setHours(Integer.parseInt(timeSplit[0]));
            formattedDateTime.setMinutes(Integer.parseInt(timeSplit[1]));

            System.out.println(new Date(formattedDateTime.getTime()));

            Exercise exerciseObj = new Exercise(formattedDateTime,
                    durationInSeconds,
                    activityType,
                    intensity,
                    totalCaloriesBurned);

            // Code that logs the exercise logs
            // ****DO NOT DELETE****
            userDatabaseInterface.addUserExerciseLog(
                    profile.getId(),
                    exerciseObj
            );

            JOptionPane.showMessageDialog(null, "Exercise Logging Successful!");

            //Insert more data here
            //Call Database Adapter to save data
            /*
            boolean saved = userDatabaseInterface.CREATE_EXERCISE_TABLE(profile.getId(), profile.getName(), profile.getSex(), profile.getHeight(),
                    profile.getAge(), selectedDate, selectedExercise);
            if (saved) {
                JOptionPane.showMessageDialog(this, "Data saved successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "Error saving data to DB, might have Ligma.");
            }
            */


        });

        newExercisePanel.add(dateLabel);
        newExercisePanel.add(dateChooser);
        newExercisePanel.add(exerciseLabel);
        newExercisePanel.add(exerciseComboBox);
        newExercisePanel.add(timeLabel);
        newExercisePanel.add(timeField);
        newExercisePanel.add(intensityLabel);
        newExercisePanel.add(intensityComboBox);
        newExercisePanel.add(secondsLabel);
        newExercisePanel.add(secondsField);
        newExercisePanel.add(saveButton);


        getContentPane().add(newExercisePanel);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(250, 220);
        setVisible(true);
    }

}