package co.yorku.nutrifit.ui;

import co.yorku.nutrifit.database.userdata.IUserDatabase;
import co.yorku.nutrifit.object.Meal;
import co.yorku.nutrifit.object.MealType;
import co.yorku.nutrifit.profile.IProfile;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import java.awt.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MealInputUI extends JFrame {

    private Map<String, Integer> ingredientsMap = new HashMap<>();

    public MealInputUI(IUserDatabase userDatabaseInterface, IProfile profile) {

        JPanel mealPanel = new JPanel(new GridLayout(6, 2));

        //Date selection drop down  menu
        JLabel dateLabel = new JLabel("Date");
        JDateChooser dateChooser = new JDateChooser();
        dateChooser.setDateFormatString("yyyy-MM-dd");

        JLabel timeLabel = new JLabel("Time (24:00)");
        JTextField timeField = new JTextField(5);

        JLabel mealTypeLabel = new JLabel("Meal Type:");
        JComboBox<MealType> mealTypeDropdown = new JComboBox<>(MealType.values());

        JLabel ingredientsLabel = new JLabel("Ingredients:");
        JTextField ingredientsField = new JTextField(5);

        JLabel quantityLabel = new JLabel("Quantity:");
        JSpinner quantityDropdown = new JSpinner(new SpinnerNumberModel(
                1,
                1,
                1000000,
                1
        ));

        JButton addIngredientButton = new JButton("Add Ingredient");
        JButton submitButton = new JButton("Submit");

        addIngredientButton.addActionListener(e -> {
            String ingredient = ingredientsField.getText();
            int quantity = Integer.parseInt(quantityDropdown.getValue().toString());

            // Validate input
            if (ingredient.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please fill in the ingredient field.");
                return;
            }

            ingredientsMap.put(ingredient, quantity);
            ingredientsField.setText("");
            updatePanel(mealPanel, dateLabel, dateChooser, timeLabel, timeField, mealTypeLabel, mealTypeDropdown, ingredientsLabel, ingredientsField, quantityLabel, quantityDropdown);
            mealPanel.add(addIngredientButton);
            mealPanel.add(submitButton);
        });


        submitButton.addActionListener(e -> {
            // Get user input from fields in the new profile layout
            MealType mealType = (MealType) mealTypeDropdown.getSelectedItem();

            Date formattedDateTime = dateChooser.getDate();
            String[] timeSplit = timeField.getText().split(":");
            formattedDateTime.setHours(Integer.parseInt(timeSplit[0]));
            formattedDateTime.setMinutes(Integer.parseInt(timeSplit[1]));

            // Code that logs the meal log
            // ****DO NOT DELETE****
            userDatabaseInterface.addUserMealLog(
                    profile.getId(),
                    new Meal(
                            formattedDateTime,
                            mealType,
                            ingredientsMap,
                            100 // TODO: calculate this
                    )
            );

            JOptionPane.showMessageDialog(null, "Meal Logging Successful!");


        });

        // Add the components to the mealPanel
        updatePanel(mealPanel, dateLabel, dateChooser, timeLabel, timeField, mealTypeLabel, mealTypeDropdown, ingredientsLabel, ingredientsField, quantityLabel, quantityDropdown);
        mealPanel.add(addIngredientButton);
        mealPanel.add(submitButton);

        getContentPane().add(mealPanel);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(250, 220);
        setVisible(true);
    }

    private void updatePanel(JPanel mealPanel, JLabel dateLabel, JDateChooser dateChooser, JLabel timeLabel, JTextField timeField, JLabel mealTypeLabel, JComboBox<MealType> mealTypeDropdown, JLabel ingredientsLabel, JTextField ingredientsField, JLabel quantityLabel, JSpinner quantityDropdown) {
        mealPanel.removeAll();
        mealPanel.add(dateLabel);
        mealPanel.add(dateChooser);
        mealPanel.add(timeLabel);
        mealPanel.add(timeField);
        mealPanel.add(mealTypeLabel);
        mealPanel.add(mealTypeDropdown);
        mealPanel.add(ingredientsLabel);
        mealPanel.add(ingredientsField);
        mealPanel.add(quantityLabel);
        mealPanel.add(quantityDropdown);


        // Add the ingredient details to the panel
        mealPanel.revalidate();
        mealPanel.repaint();
    }
}
