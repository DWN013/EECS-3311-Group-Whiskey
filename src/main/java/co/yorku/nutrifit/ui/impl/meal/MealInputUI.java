package co.yorku.nutrifit.ui.impl.meal;

import co.yorku.nutrifit.NutriFit;
import co.yorku.nutrifit.logs.LogIterator;
import co.yorku.nutrifit.logs.impl.Meal;
import co.yorku.nutrifit.object.FoodInfo;
import co.yorku.nutrifit.object.MealType;
import co.yorku.nutrifit.ui.NutrifitWindow;
import com.google.common.collect.Maps;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/*
 * Class responsible for meal input UI
 * Takes input from user for their meals
 */

public class MealInputUI extends NutrifitWindow {

    // Singleton instance
    private static MealInputUI instance;

    // Table model and table for displaying added foods
    private DefaultTableModel tableModel;
    private JTable foodsTable;

    // Singleton pattern: method to retrieve instance
    public static MealInputUI getInstance() {
        if (instance == null) {
            instance = new MealInputUI();
        }

        return instance;
    }

    // Map to store food and its quantity
    private Map<String, Integer> foodMap = Maps.newHashMap();


    private MealInputUI() {
        // Invoke superclass constructor with title and layout
        super("Log Meal", new GridLayout(8, 2));

        // UI setup: labels, text fields, drop-downs, and buttons

        //Date selection drop down  menu
        addLabel("Date");
        JDateChooser dateChooser = new JDateChooser();
        dateChooser.setDateFormatString("yyyy-MM-dd");
        this.addComponent(dateChooser);

        //Time selection label
        addLabel("Time (24:00)");
        JTextField timeField = addTextField(5);

        //Meal Type selection drop down  menu
        addLabel("Meal Type:");
        JComboBox<Enum<?>> mealTypeDropdown = addComboBox(MealType.values());

        //Food selection label
        addLabel("Food:");
        JTextField foodField = addTextField(20);

        //Quantity selection drop down  menu
        addLabel("Quantity:");
        JSpinner quantityDropdown = addSpinner();

        // Setting up the table for foods and quantities
        tableModel = new DefaultTableModel();
        tableModel.addColumn("Foods");
        tableModel.addColumn("Quantity");

        foodsTable = new JTable(tableModel);

        JScrollPane jScrollPane = new JScrollPane(foodsTable);
        jScrollPane.setPreferredSize(new Dimension(150, 75));

        addComponent(jScrollPane);

        // Adding the Add Food button
        addButton("Add Food", event -> {
            String food = getFood(foodField.getText());
            int quantity = Integer.parseInt(quantityDropdown.getValue().toString());

            // Validate input
            if (food == null) {
                showMessageDialog( "Could not find food specified. Please try again.");
                return;
            }

            if (quantity <= 0) {
                showMessageDialog("You must input a quantity larger than 0!");
                return;
            }

            foodMap.put(food, quantity);
            quantityDropdown.setValue(1);
            updateFoodsTable();
        });

        // Adding the Edit Food button
        addButton("Edit Food", event -> {
            // Get the food inputted in the text field
            String inputtedFood = getFood(foodField.getText());

            // Check if the inputted food exists in the foodMap
            if (inputtedFood != null && foodMap.containsKey(inputtedFood)) {
                // Retrieve the current quantity of the selected food
                int currentQuantity = foodMap.get(inputtedFood);

                // Prompt the user to enter a new quantity for the selected food
                String input = openTextInputDialog("Enter the new quantity for " + inputtedFood + " (current: " + currentQuantity + ")");

                // Check if a new quantity is provided and not empty
                if (input != null && !input.isEmpty()) {
                    try {
                        // Parse the new quantity
                        int newQuantity = Integer.parseInt(input);


                        if (newQuantity <= 0) {
                            showMessageDialog("You must input a quantity larger than 0!");
                            return;
                        }

                        // Update the quantity for the selected food in the foodMap
                        foodMap.put(inputtedFood, newQuantity);

                        // Update the foods table in the UI
                        updateFoodsTable();
                    } catch (NumberFormatException e) {
                        showMessageDialog("Please enter a valid integer for the quantity.");
                    }
                }
            } else {
                showMessageDialog("Please add the food before editing.");
            }
        });


        // Adding the Delete Food button
        addButton("Delete Food", event -> {
            // Get the selected food from the text field
            String selectedFood = this.getFoodToDelete();

            // Check if the selected food exists in the foodMap
            if (selectedFood != null && foodMap.containsKey(selectedFood)) {
                // Ask for confirmation before deleting the selected food
                int option = showConfirmationDialog("Do you want to delete " + selectedFood + " from the food list?");

                // Check if the user confirmed deletion
                if (option == JOptionPane.YES_OPTION) {
                    // Remove the selected food from the foodMap
                    foodMap.remove(selectedFood);

                    // Update the foods table in the UI
                    updateFoodsTable();
                }
            } else {
                showMessageDialog("Please add food before deleting.");
            }
        });


        // Adding the Submit button
        addButton("Submit", event -> {

            // Validation: Check if time field is empty
            if (timeField.getText().isEmpty()) {
                showMessageDialog("Please enter a valid time.");
                return;
            }

            // Validation: Check if date is not selected
            if (dateChooser.getDate() == null) {
                showMessageDialog("Please enter a valid date.");
                return;
            }

            // Retrieve meal type selected by user
            MealType mealType = (MealType) mealTypeDropdown.getSelectedItem();

            // Get selected date and set time boundaries for the day
            Date formattedDateTime = dateChooser.getDate();
            Date startOfDay = new Date(formattedDateTime.getTime());
            Date endOfDay = new Date(formattedDateTime.getTime());

            startOfDay.setHours(0);
            startOfDay.setMinutes(0);
            startOfDay.setSeconds(0);

            endOfDay.setHours(23);
            endOfDay.setMinutes(59);
            endOfDay.setSeconds(59);

            // Retrieve meal logs for the selected day
            LogIterator logIterator = NutriFit.getInstance().getUserDatabase().getUserMealLogs(
                    NutriFit.getInstance().getLoadedProfile().getId(), startOfDay, endOfDay);

            // Ensure only one type of meal is logged per day
            while (mealType != MealType.SNACK && logIterator.hasNext()) {
                Meal meal = (Meal) logIterator.getNext();
                if (meal.getMealType() == mealType) {
                    showMessageDialog("You can only log one " + mealType.getDisplayName() + " once per day!");
                    return;
                }
            }


            try {
                // Parse the time input
                String[] timeSplit = timeField.getText().split(":");
                formattedDateTime.setHours(Integer.parseInt(timeSplit[0]));
                formattedDateTime.setMinutes(Integer.parseInt(timeSplit[1]));

                // Create a map of food info and their quantities
                Map<FoodInfo, Integer> foodInfo = createFoodMap();
                Map<Integer, Integer> foodIDToFoodAmountMap = foodInfo.entrySet().stream().collect(
                        Collectors.toMap(e -> e.getKey().getFoodID(), Map.Entry::getValue));

                // Log the meal in the database
                NutriFit.getInstance().getUserDatabase().addUserMealLog(
                        NutriFit.getInstance().getLoadedProfile().getId(),
                        new Meal(
                                formattedDateTime,
                                mealType,
                                foodIDToFoodAmountMap,
                                NutriFit.getInstance().getMeal().calculateTotalMealCalories(foodIDToFoodAmountMap)
                        )
                );

                // Reset UI fields after successful logging
                foodField.setText("");
                clearFoodsTable();
                showMessageDialog("Meal Logging Successful!");
            } catch (NumberFormatException e) {
                showMessageDialog("Please enter a valid time in the format HH:mm.");
            } catch (NullPointerException e) {
                showMessageDialog("Please enter a valid date and time.");
            }

        });

        // Adding the Back button and build UI
        this.addBackButton(MainMealMenu.getInstance());
        this.build();
    }

    // Method to get food based on user input
    private String getFood(String inputtedUserText) {
        List<String> foodNames = NutriFit.getInstance().getNutrientDatabase().getFoodTypesSimilar(inputtedUserText);

        if (foodNames.isEmpty()) {
            return null;
        }

        return openDropdownDialog("Select Food", "Select Food", 0, foodNames.toArray(new String[0]));
    }

    public String getFoodToDelete() {
        if (this.foodMap.isEmpty()) return null;

        return openDropdownDialog("Select Food", "Select Food", 0, this.foodMap.keySet().toArray(new String[0]));
    }

    // Method to create a map of food info and quantity
    private Map<FoodInfo, Integer> createFoodMap() {
        Map<FoodInfo, Integer> foodNamesMappedToFoodIDs = Maps.newHashMap();

        for (Map.Entry<String, Integer> stringIntegerEntry : this.foodMap.entrySet()) {
            foodNamesMappedToFoodIDs.put(NutriFit.getInstance().getNutrientDatabase().getFoodInfo(stringIntegerEntry.getKey()), stringIntegerEntry.getValue());
        }

        return foodNamesMappedToFoodIDs;
    }

    // Method to update the foods table in the UI
    private void updateFoodsTable() {
        clearFoodsTable();

        for (Map.Entry<String, Integer> entry : foodMap.entrySet()) {
            String food = entry.getKey();
            Integer quantity = entry.getValue();
            tableModel.addRow(new Object[]{food, quantity});
        }
    }

    // Method to clear the foods table in the UI
    private void clearFoodsTable() {
        tableModel.setRowCount(0);
    }

    // Method to reset the UI by clearing food table and map
    public void reset() {
        this.clearFoodsTable();
        this.foodMap.clear();
    }

}
