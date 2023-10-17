package co.yorku.nutrifit.ui;

import co.yorku.nutrifit.database.userdata.UserDatabaseInterface;
import javax.swing.*;
import java.awt.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MealInputUI  {

    private UserDatabaseInterface userDatabaseInterface;
    private JPanel mealPanel;

    private Map<String, String> ingredientsMap;


    public MealInputUI(UserDatabaseInterface userDatabaseInterface) {
        this.userDatabaseInterface = userDatabaseInterface;
        this.ingredientsMap = new HashMap<>();

        this.mealPanel = createPanel();


    }

    public JPanel getPanel() {
        return mealPanel;
    }

    public JPanel createPanel() {
        JPanel mealPanel = new JPanel(new GridLayout(6, 2));


        JLabel dateLabel = new JLabel("Date (dd-MM-yyyy):");
        JTextField dateField = new JTextField(20);

        JLabel mealTypeLabel = new JLabel("Meal Type:");
        JComboBox<String> mealTypeDropdown = new JComboBox<>(new String[]{"Breakfast", "Lunch", "Dinner","Snacks"});
        JTextField mealTypeField = new JTextField(5);

        JLabel ingredientsLabel = new JLabel("Ingredients:");
        JTextField ingredientsField = new JTextField(5);

        JLabel quantityLabel = new JLabel("Quantity:");
        JComboBox<String> quantityDropdown = new JComboBox<>(new String[]{"little", "medium", "lot"});
        JTextField quantityField = new JTextField(5);

        JButton addIngredientButton = new JButton("Add Ingredient");
        JButton submitButton = new JButton("Submit");


        addIngredientButton.addActionListener(e -> {
            String ingredient = ingredientsField.getText();
            String quantity = (String) quantityDropdown.getSelectedItem();

            // Validate input
            if (ingredient.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please fill in the ingredient field.");
                return;
            }

            ingredientsMap.put(ingredient, quantity);
            ingredientsField.setText("");
            updatePanel(mealPanel, dateLabel, dateField, mealTypeLabel, mealTypeDropdown, ingredientsLabel, ingredientsField, quantityLabel, quantityDropdown);
            mealPanel.add(addIngredientButton);
            mealPanel.add(submitButton);
        });


        submitButton.addActionListener(e -> {
            // Get user input from fields in the new profile layout
            String dateText = dateField.getText();
            String mealType = (String) mealTypeDropdown.getSelectedItem();


            // Validate input
            if (dateText.isEmpty() || mealType.isEmpty()) {
                // Handle empty fields
                JOptionPane.showMessageDialog(null, "Please fill in all fields.");
                return; // Stop processing if any field is empty
            }

            try {
                DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
                Date date = df.parse(dateText);
                // Handle the parsed date object here
            } catch (ParseException ex) {
                // Handle invalid input
                JOptionPane.showMessageDialog(null, "Invalid date input. Please use the format dd-MM-yyyy.");
            }

            JOptionPane.showMessageDialog(null, "Meal Logging Successful!");
            System.out.println("Date: " + dateText);
            System.out.println("Meal Type: " + mealType);
            System.out.println("Map of Ingredients and Quantities:");
            for (Map.Entry<String, String> entry : ingredientsMap.entrySet()) {
                System.out.println(entry.getKey() + " : " + entry.getValue());
            }


        });

        // Add the components to the mealPanel
        updatePanel(mealPanel, dateLabel, dateField, mealTypeLabel, mealTypeDropdown, ingredientsLabel, ingredientsField, quantityLabel, quantityDropdown);
        mealPanel.add(addIngredientButton);
        mealPanel.add(submitButton);

        return mealPanel;
    }

    public void updatePanel(JPanel mealPanel, JLabel dateLabel, JTextField dateField, JLabel mealTypeLabel, JComboBox<String> mealTypeDropdown, JLabel ingredientsLabel, JTextField ingredientsField, JLabel quantityLabel, JComboBox<String> quantityDropdown) {
        mealPanel.removeAll();
        mealPanel.add(dateLabel);
        mealPanel.add(dateField);
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
