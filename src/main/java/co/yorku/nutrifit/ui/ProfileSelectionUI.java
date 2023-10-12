package co.yorku.nutrifit.ui;

import co.yorku.nutrifit.database.userdata.UserDatabaseInterface;
import co.yorku.nutrifit.profile.Profile;
import co.yorku.nutrifit.profile.impl.ProfileHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.List;

public class ProfileSelectionUI extends JFrame {

    public ProfileSelectionUI(UserDatabaseInterface userDatabaseInterface) {
        super("Profile");

        List<Profile> allProfiles = userDatabaseInterface.getAllProfiles();

        String[] listItems = new String[allProfiles.size()];
        for (int i = 0; i < allProfiles.size(); i++) {
            Profile profile = allProfiles.get(i);
            listItems[i] = "#" + profile.getId() + ": " + profile.getName();
        }
        JComboBox list = new JComboBox(listItems);
        JButton loadProfile = new JButton("Load Selected Profile");

        // Create components for the new profile layout
        JPanel newProfilePanel = new JPanel(new GridLayout(6, 2));
        JLabel nameLabel = new JLabel("Name:");
        JTextField nameField = new JTextField(20);  // Use the global field
        JLabel sexLabel = new JLabel("Sex:");
        JTextField sexField = new JTextField(5);     // Use the global field
        JLabel heightLabel = new JLabel("Height (cm):");
        JTextField heightField = new JTextField(5); // Use the global field
        JLabel ageLabel = new JLabel("Age:");
        JTextField ageField = new JTextField(5);     // Use the global field
        JButton submitButton = new JButton("Submit");

        // Add action listener to the Submit button in the new profile layout
        submitButton.addActionListener(e -> {
            // Get user input from fields in the new profile layout
            String name = nameField.getText();
            String sex = sexField.getText();
            String heightText = heightField.getText();
            String ageText = ageField.getText();

            // Validate input
            if (name.isEmpty() || sex.isEmpty() || heightText.isEmpty() || ageText.isEmpty()) {
                // Handle empty fields
                JOptionPane.showMessageDialog(null, "Please fill in all fields.");
                return; // Stop processing if any field is empty
            }

            // Parse input values
            int age;

            try {
                age = Integer.parseInt(ageText);
            } catch (NumberFormatException ex) {
                // Handle invalid input
                JOptionPane.showMessageDialog(null, "Invalid input for height or age.");
                return; // Stop processing if parsing fails
            }

            int userid = userDatabaseInterface.setupProfile(new ProfileHandler(
                    userDatabaseInterface,
                    -1, name, sex, heightText, age
            ));


            // Update the result label
            JOptionPane.showMessageDialog(null, "New profile created successfully!");

            new NutriFitMainUI(userDatabaseInterface, userid);
            dispatchEvent(new WindowEvent(ProfileSelectionUI.this, WindowEvent.WINDOW_CLOSING));
        });

        loadProfile.addActionListener(e -> {
            Profile selectedIndex = allProfiles.get(list.getSelectedIndex());
            new NutriFitMainUI(userDatabaseInterface, selectedIndex.getId());
            dispatchEvent(new WindowEvent(ProfileSelectionUI.this, WindowEvent.WINDOW_CLOSING));
        });

        newProfilePanel.add(list);
        newProfilePanel.add(loadProfile);

        newProfilePanel.add(nameLabel);
        newProfilePanel.add(nameField);
        newProfilePanel.add(sexLabel);
        newProfilePanel.add(sexField);
        newProfilePanel.add(heightLabel);
        newProfilePanel.add(heightField);
        newProfilePanel.add(ageLabel);
        newProfilePanel.add(ageField);
        newProfilePanel.add(submitButton);

        getContentPane().add(newProfilePanel);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 200);
        setVisible(true);
    }

}
