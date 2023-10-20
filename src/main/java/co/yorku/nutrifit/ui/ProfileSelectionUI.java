package co.yorku.nutrifit.ui;

import co.yorku.nutrifit.NutriFit;
import co.yorku.nutrifit.database.nutrient.INFDatabase;
import co.yorku.nutrifit.database.userdata.IUserDatabase;
import co.yorku.nutrifit.profile.IProfile;
import co.yorku.nutrifit.profile.impl.ProfileHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.util.List;

public class ProfileSelectionUI extends JFrame {

    private static ProfileSelectionUI instance;

    public static ProfileSelectionUI getInstance() {
        if (instance == null) {
            instance = new ProfileSelectionUI();
        }

        return instance;
    }

    private ProfileSelectionUI() {
        super("Profile");

        List<IProfile> allProfiles = NutriFit.getInstance().getUserDatabase().getAllProfiles();

        String[] listItems = new String[allProfiles.size()];
        for (int i = 0; i < allProfiles.size(); i++) {
            IProfile profile = allProfiles.get(i);
            listItems[i] = "#" + profile.getId() + ": " + profile.getName();
        }
        JComboBox list = new JComboBox(listItems);
        JButton loadProfile = new JButton("Load Selected Profile");

        // Create components for the new profile layout
        JPanel newProfilePanel = new JPanel(new GridLayout(9, 2));
        JLabel nameLabel = new JLabel("Name:");
        JTextField nameField = new JTextField(20);  // Use the global field
        JLabel heightLabel = new JLabel("Height (cm):");
        JTextField heightField = new JTextField(5); // Use the global field
        JLabel ageLabel = new JLabel("Age:");
        JTextField ageField = new JTextField(5);     // Use the global field
        JLabel weightLabel = new JLabel("Weight:");
        JTextField weightField = new JTextField(5);     // Use the global field
        JButton submitButton = new JButton("Submit");


        String[] metricOrImperial = new String[2];
        metricOrImperial[0] = "Metric";
        metricOrImperial[1] = "Imperial";
        JComboBox metricOrImperalJComboBox = new JComboBox(metricOrImperial);

        String[] isMalOrFemale = new String[2];
        isMalOrFemale[0] = "Male";
        isMalOrFemale[1] = "Female";
        JComboBox maleOrFemale = new JComboBox(isMalOrFemale);

        // Add action listener to the Submit button in the new profile layout
        submitButton.addActionListener(e -> {
            // Get user input from fields in the new profile layout
            String name = nameField.getText();
            String heightText = heightField.getText();
            String ageText = ageField.getText();

            // Validate input
            if (name.isEmpty() || heightText.isEmpty() || ageText.isEmpty()) {
                // Handle empty fields
                JOptionPane.showMessageDialog(null, "Please fill in all fields.");
                return; // Stop processing if any field is empty
            }

            // Parse input values
            float height;
            int age;
            float weight;
            boolean isMetric = metricOrImperial[metricOrImperalJComboBox.getSelectedIndex()].equalsIgnoreCase("Metric");
            boolean isMale = metricOrImperial[maleOrFemale.getSelectedIndex()].equalsIgnoreCase("Male");

            try {
                age = Integer.parseInt(ageText);
                height = Float.parseFloat(heightText);
                weight = Float.parseFloat(heightText);
            } catch (NumberFormatException ex) {
                // Handle invalid input
                JOptionPane.showMessageDialog(null, "Invalid input for height or age.");
                return; // Stop processing if parsing fails
            }

            int userid = NutriFit.getInstance().getUserDatabase().setupProfile(new ProfileHandler(
                    -1, name, isMale, height, age, weight, isMetric
            ));


            // Update the result label
            JOptionPane.showMessageDialog(null, "New profile created successfully!");

            NutriFitMainUI.getInstance().showToUser();
            dispatchEvent(new WindowEvent(ProfileSelectionUI.this, WindowEvent.WINDOW_CLOSING));
        });

        loadProfile.addActionListener(e -> {
            IProfile selectedIndex = allProfiles.get(list.getSelectedIndex());
            NutriFit.getInstance().setLoadedProfile(selectedIndex);
            NutriFitMainUI.getInstance().showToUser();
            dispatchEvent(new WindowEvent(ProfileSelectionUI.this, WindowEvent.WINDOW_CLOSING));
        });

        newProfilePanel.add(list);
        newProfilePanel.add(loadProfile);
        newProfilePanel.add(metricOrImperalJComboBox);
        newProfilePanel.add(maleOrFemale);

        newProfilePanel.add(nameLabel);
        newProfilePanel.add(nameField);
        newProfilePanel.add(heightLabel);
        newProfilePanel.add(heightField);
        newProfilePanel.add(ageLabel);
        newProfilePanel.add(ageField);
        newProfilePanel.add(weightLabel);
        newProfilePanel.add(weightField);
        newProfilePanel.add(submitButton);

        getContentPane().add(newProfilePanel);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 200);
        setVisible(false);
    }

    public void showToUser() {
        setVisible(true);
    }
}
