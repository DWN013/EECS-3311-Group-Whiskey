package co.yorku.nutrifit.ui.impl.main;

import co.yorku.nutrifit.NutriFit;
import co.yorku.nutrifit.profile.IProfile;
import co.yorku.nutrifit.profile.impl.ProfileHandler;
import co.yorku.nutrifit.ui.NutrifitWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ProfileSelectionUI extends NutrifitWindow {

    private static ProfileSelectionUI instance;

    public static ProfileSelectionUI getInstance() {
        if (instance == null) {
            instance = new ProfileSelectionUI();
        }

        return instance;
    }

    private ProfileSelectionUI() {
        super("Profile Page", new GridLayout(9, 2));

        List<IProfile> allProfiles = NutriFit.getInstance().getUserDatabase().getAllProfiles();
        JComboBox<String> profileList = addComboBox(allProfiles.stream().map(IProfile::getName).collect(Collectors.toList()));
        addButton("Load Selected Profile", event -> {
            IProfile selectedIndex = allProfiles.get(profileList.getSelectedIndex());
            NutriFit.getInstance().setLoadedProfile(selectedIndex);
            hideWindow(); // Hide this window
            NutriFitMainUI.getInstance().showWindow(); // Show the Main UI
        });

        // Create components for the new profile layout
        addLabel("Name:");
        JTextField nameField = addTextField(32);
        addLabel("Height (cm):");
        JTextField heightField = addTextField(5);
        addLabel("Age:");
        JTextField ageField = addTextField(5);
        addLabel("Weight:");
        JTextField weightField = addTextField(5);

        JComboBox<String> metricOrImperalJComboBox = addComboBox("Metric", "Imperial");
        JComboBox<String> maleOrFemale = addComboBox("Male", "Female");

        addButton("Submit", event -> {
            // Get user input from fields in the new profile layout
            String name = nameField.getText();
            String heightText = heightField.getText();
            String ageText = ageField.getText();
            String weightText = weightField.getText();

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
            boolean isMetric = Objects.requireNonNull(metricOrImperalJComboBox.getSelectedItem()).toString().equalsIgnoreCase("Metric");
            boolean isMale = Objects.requireNonNull(maleOrFemale.getSelectedItem()).toString().equalsIgnoreCase("Male");

            try {
                age = Integer.parseInt(ageText);
                height = Float.parseFloat(heightText);
                weight = Float.parseFloat(weightText);
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

            NutriFit.getInstance().setLoadedProfile(NutriFit.getInstance().getUserDatabase().getProfile(userid));
            hideWindow(); // Hide this window
            NutriFitMainUI.getInstance().showWindow(); // Show the Main UI
        });

        this.build();
    }

}
