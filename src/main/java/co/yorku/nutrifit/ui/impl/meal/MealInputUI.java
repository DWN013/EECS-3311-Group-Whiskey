package co.yorku.nutrifit.ui.impl.meal;

import co.yorku.nutrifit.NutriFit;
import co.yorku.nutrifit.logs.LogIterator;
import co.yorku.nutrifit.logs.impl.Meal;
import co.yorku.nutrifit.object.Ingredients;
import co.yorku.nutrifit.object.MealType;
import co.yorku.nutrifit.ui.NutrifitWindow;
import co.yorku.nutrifit.ui.impl.main.NutriFitMainUI;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MealInputUI extends NutrifitWindow {

    private static MealInputUI instance;
    private DefaultTableModel tableModel;
    private JTable ingredientsTable;


    public static MealInputUI getInstance() {
        if (instance == null) {
            instance = new MealInputUI();
        }

        return instance;
    }

    private Map<String, Integer> ingredientsMap = new HashMap<>();


    private MealInputUI() {
        super("Log Meal", new GridLayout(8, 2));

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
        JComboBox<Enum<?>> ingredientsField = addComboBox(Ingredients.values());

        addLabel("Quantity:");
        JSpinner quantityDropdown = addSpinner();

        tableModel = new DefaultTableModel();
        tableModel.addColumn("Ingredients");
        tableModel.addColumn("Quantity");

        ingredientsTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(ingredientsTable);
        addComponent(scrollPane);


        JButton addIngredientButton = addButton("Add Ingredient", event -> {
            String ingredient = ingredientsField.getSelectedItem().toString();
            int quantity = Integer.parseInt(quantityDropdown.getValue().toString());

            // Validate input
            if (ingredient.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please fill in the ingredient field.");
                return;
            }

            ingredientsMap.put(ingredient, quantity);
            quantityDropdown.setValue(1);
            updateIngredientsTable();
        });

        addButton("Edit Ingredient", event -> {
            // TODO: Implement the edit ingredient functionality
            String selectedIngredient = ingredientsField.getSelectedItem().toString();
            if (ingredientsMap.containsKey(selectedIngredient)) {
                int currentQuantity = ingredientsMap.get(selectedIngredient);
                String input = JOptionPane.showInputDialog("Enter the new quantity for " + selectedIngredient + " (current: " + currentQuantity + ")");
                if (input != null && !input.isEmpty()) {
                    try {
                        int newQuantity = Integer.parseInt(input);
                        ingredientsMap.put(selectedIngredient, newQuantity);
                        updateIngredientsTable();
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(null, "Please enter a valid integer for the quantity.");
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "Please add the ingredient before editing.");
            }

        });
        addButton("Delete Ingredient", event -> {
            // TODO: Implement the delete ingredient functionality
            String selectedIngredient = ingredientsField.getSelectedItem().toString();
            if (ingredientsMap.containsKey(selectedIngredient)) {
                int option = JOptionPane.showConfirmDialog(null, "Do you want to delete " + selectedIngredient + " from the ingredients list?", "Delete Ingredient", JOptionPane.YES_NO_OPTION);
                if (option == JOptionPane.YES_OPTION) {
                    ingredientsMap.remove(selectedIngredient);
                    updateIngredientsTable();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Please add the ingredient before deleting.");
            }
        });

        addButton("Submit", event -> {

            if (timeField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter a valid time.");
                return;
            }

            if (dateChooser.getDate() == null) {
                JOptionPane.showMessageDialog(null, "Please enter a valid date.");
                return;
            }

            // Get user input from fields in the new profile layout
            MealType mealType = (MealType) mealTypeDropdown.getSelectedItem();

            Date formattedDateTime = dateChooser.getDate();
            Date startOfDay = new Date(formattedDateTime.getTime());
            Date endOfDay = new Date(formattedDateTime.getTime());

            startOfDay.setHours(0);
            startOfDay.setMinutes(0);
            startOfDay.setSeconds(0);

            endOfDay.setHours(23);
            endOfDay.setMinutes(59);
            endOfDay.setSeconds(59);

            LogIterator logIterator = NutriFit.getInstance().getUserDatabase().getUserMealLogs(NutriFit.getInstance().getLoadedProfile().getId(), startOfDay, endOfDay);

            // Check top make sure that we are only logging one type of meal type for that day
            while (mealType != MealType.SNACK && logIterator.hasNext()) {
                Meal meal = (Meal) logIterator.getNext();
                if (meal.getMealType() == mealType) {
                    JOptionPane.showMessageDialog(null, "You can only log one " + mealType.getDisplayName() + " once per day!");
                    return;
                }
            }


            try {
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
                clearIngredientsTable();
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Please enter a valid time in the format HH:mm.");
            } catch (NullPointerException e) {
                JOptionPane.showMessageDialog(null, "Please enter a valid date and time.");
            }

        });

        this.addBackButton(NutriFitMainUI.getInstance());
        this.build();
    }

    private void updateIngredientsTable() {
        clearIngredientsTable();

        for (Map.Entry<String, Integer> entry : ingredientsMap.entrySet()) {
            String ingredient = entry.getKey();
            Integer quantity = entry.getValue();
            tableModel.addRow(new Object[]{ingredient, quantity});
        }
    }

    private void clearIngredientsTable() {
        tableModel.setRowCount(0);
    }
    public void reset() {
        this.clearIngredientsTable();
        this.ingredientsMap.clear();
    }

}
