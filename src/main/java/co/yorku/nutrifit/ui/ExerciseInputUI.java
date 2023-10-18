package co.yorku.nutrifit.ui;

import co.yorku.nutrifit.database.userdata.IUserDatabase;
import co.yorku.nutrifit.profile.IProfile;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import java.awt.*;


//Interface details to input user exercise information
public class ExerciseInputUI extends JFrame{

    //ExerciseUI actions
    public ExerciseInputUI(IUserDatabase userDatabaseInterface, int userId){
        //Title of window that opens when "Log Exercise" button pressed
        super("Exercise UI");
        IProfile profile = userDatabaseInterface.getProfile(userId);
        //Debug line
        System.out.println("Loaded exercise input for " + profile.getId() + " -> " + profile.getName());

        //New exercise panel to input information
        JPanel newExercisePanel = new JPanel(new GridLayout(6, 2));
        //Date input
        JLabel dateLabel = new JLabel("Date");
        JDateChooser dateChooser = new JDateChooser();
        dateChooser.setDateFormatString("yyyy-MM-dd");
        //Exercise input
        JLabel exerciseLabel = new JLabel("Exercise Done:");
        JComboBox<String> exerciseComboBox = new JComboBox<>();

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
                JOptionPane.showMessageDialog(this, "Error saving data to DB, might have ligma.");
            }
            */


        });

        newExercisePanel.add(dateLabel);
        newExercisePanel.add(dateChooser);
        newExercisePanel.add(saveButton);


        getContentPane().add(newExercisePanel);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(250, 220);
        setVisible(true);
    }

}