package co.yorku.nutrifit.ui.impl.main;

import co.yorku.nutrifit.NutriFit;
import co.yorku.nutrifit.profile.IProfile;
import co.yorku.nutrifit.profile.impl.ProfileHandler;
import co.yorku.nutrifit.ui.NutrifitWindow;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/*
 * Sign In UI page - allow user to create a new profile
 */

public class SignUpPage extends NutrifitWindow {

    public SignUpPage(NutrifitWindow parent) {
        super("Sign Up", new GridLayout(8, 2));

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

            //Add new profile to the Database
            int userid = NutriFit.getInstance().getUserDatabase().setupProfile(new ProfileHandler(
                    -1, name, isMale, height, age, weight, isMetric
            ));

            // Update the result label
            JOptionPane.showMessageDialog(null, "New profile created successfully!");

            NutriFit.getInstance().setLoadedProfile(NutriFit.getInstance().getUserDatabase().getProfile(userid));
            hideWindow(); // Hide this window
            NutriFitMainUI.getInstance().showWindow(); // Show the Main UI
        });

        this.addBackButton(parent);

        this.build();
    }

}
