package co.yorku.nutrifit.ui;

import co.yorku.nutrifit.database.userdata.UserDatabaseInterface;
import co.yorku.nutrifit.profile.Profile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

//Interface details to input user exercise information
public class ExerciseInputUI extends JFrame{

    //ExerciseUI actions
    public ExerciseInputUI(UserDatabaseInterface userDatabaseInterface, int userId){
        //Title of window that opens when "Log Exercise" button pressed
        super("Exercise Page");
        Profile profile = userDatabaseInterface.getProfile(userId);
        //Debug line
        System.out.println("Loaded exercise input for " + profile.getId() + " -> " + profile.getName());

        //Save button that saves info on boxes to db
        JButton saveButton = new JButton("Save Exercise");

        //New exercise panel to input information
        JPanel newExercisePanel = new JPanel(new GridLayout(6, 2));
        //Date
        JLabel dateLabel = new JLabel("Date (dd-MM-yyyy)");
        JTextField dateField = new JTextField(10);
        String dateText = dateField.getText();
        //Exercise input
        JLabel exerciseLabel = new JLabel("Exercise Done:");
        JComboBox<String> exerciseComboBox = new JComboBox<>();

        //Action listener for save button
        saveButton.addActionListener(e -> {

        });

        newExercisePanel.add(dateLabel);
        newExercisePanel.add(dateField);
        getContentPane().add(newExercisePanel);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 300);
        setVisible(true);
    }

}
