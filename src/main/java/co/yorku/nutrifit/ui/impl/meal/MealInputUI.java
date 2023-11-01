package co.yorku.nutrifit.ui.impl.meal;

import co.yorku.nutrifit.NutriFit;
import co.yorku.nutrifit.logs.impl.Meal;
import co.yorku.nutrifit.object.MealType;
import co.yorku.nutrifit.ui.NutrifitWindow;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import java.awt.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MealInputUI extends NutrifitWindow {

    private static MealInputUI instance;

    public static MealInputUI getInstance() {
        if (instance == null) {
            instance = new MealInputUI();
        }

        return instance;
    }

    private Map<String, Integer> ingredientsMap = new HashMap<>();

    private MealInputUI() {
        super("Log Meal", new GridLayout(6, 2));

        //Date selection drop down  menu
        addLabel("Date");
        JDateChooser dateChooser = new JDateChooser();
        dateChooser.setDateFormatString("yyyy-MM-dd");
        this.addComponent(dateChooser);

        addLabel("Time (24:00)");
        JTextField timeField = addTextField(5);

        addLabel("Meal Type:");
        JComboBox<Enum<?>> mealTypeDropdown = addComboBox(MealType.values());

        addLabel("Ingredients:");
        JTextField ingredientsField = addTextField(5);

        addLabel("Quantity:");
        JSpinner quantityDropdown = addSpinner();

        addButton("Add Ingredient", event -> {
            String ingredient = ingredientsField.getText();
            int quantity = Integer.parseInt(quantityDropdown.getValue().toString());

            // Validate input
            if (ingredient.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please fill in the ingredient field.");
                return;
            }

            ingredientsMap.put(ingredient, quantity);
            ingredientsField.setText("");
            quantityDropdown.setValue(1);
        });
        addButton("Submit", event -> {
            // Get user input from fields in the new profile layout
            MealType mealType = (MealType) mealTypeDropdown.getSelectedItem();

            Date formattedDateTime = dateChooser.getDate();
            String[] timeSplit = timeField.getText().split(":");
            formattedDateTime.setHours(Integer.parseInt(timeSplit[0]));
            formattedDateTime.setMinutes(Integer.parseInt(timeSplit[1]));

            // Code that logs the meal log
            // ****DO NOT DELETE****
            NutriFit.getInstance().getUserDatabase().addUserMealLog(
                    NutriFit.getInstance().getLoadedProfile().getId(),
                    new Meal(
                            formattedDateTime,
                            mealType,
                            ingredientsMap,
                            100 // TODO: calculate this
                    )
            );

            JOptionPane.showMessageDialog(null, "Meal Logging Successful!");

        });

        this.build();
    }

    public void clearInputtedIngredients() {
        this.ingredientsMap.clear();
    }

}
