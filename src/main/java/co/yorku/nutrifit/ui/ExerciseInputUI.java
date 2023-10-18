package co.yorku.nutrifit.ui;

import co.yorku.nutrifit.database.userdata.UserDatabaseInterface;
import co.yorku.nutrifit.database.userdata.objects.Intensity;
import co.yorku.nutrifit.database.userdata.objects.ExerciseLog;
import co.yorku.nutrifit.profile.Profile;
import com.toedter.calendar.JDateChooser;
import java.util.Date;

import javax.swing.*;
import java.awt.*;


//Interface details to input user exercise information
public class ExerciseInputUI extends JFrame{

    //ExerciseUI actions
    public ExerciseInputUI(UserDatabaseInterface userDatabaseInterface, int userId){
        //Title of window that opens when "Log Exercise" button pressed
        super("Exercise UI");
        Profile profile = userDatabaseInterface.getProfile(userId);
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
        JComboBox<String> exerciseComboBox = new JComboBox<>();
        //Intensity combo box
        JLabel intensityLabel = new JLabel("Intensity");
        JComboBox<Intensity> intensityComboBox = new JComboBox<>(Intensity.values());
        //Time in seconds
        JLabel secondsLabel = new JLabel("Seconds");
        JTextField secondsField = new JTextField(6);

        //public Date(int year, int month, int date) {
        //        this(year, month, date, 0, 0, 0);
        //    }
        Date formattedDateTime = dateChooser.getDate();
        //formattedDateTime.setTime();
        String temp = "";
        ExerciseLog exerciseObj = new ExerciseLog(formattedDateTime, secondsField, temp, (Intensity) intensityComboBox.getSelectedItem());



        //Save button for saving to DB
        JButton saveButton = new JButton("Save");
        //Action listener for save button
        saveButton.addActionListener(e -> {
            String selectedDate = ((JTextField) dateChooser.getDateEditor().getUiComponent()).getText();
            String selectedExercise = (String) exerciseComboBox.getSelectedItem();
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