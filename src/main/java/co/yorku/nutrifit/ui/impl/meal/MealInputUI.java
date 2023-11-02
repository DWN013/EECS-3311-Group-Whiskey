package co.yorku.nutrifit.ui.impl.meal;

import co.yorku.nutrifit.NutriFit;
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
    private static boolean breakfastLogged = false;
    private static Date lastLoggedBreakfastDate;
    private static boolean lunchLogged = false;
    private static Date lastLoggedLunchDate;
    private static boolean dinnerLogged = false;
    private static Date lastLoggedDinnerDate;
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
            // Get user input from fields in the new profile layout
            MealType mealType = (MealType) mealTypeDropdown.getSelectedItem();

            if (mealType == MealType.BREAKFAST) {
                if (breakfastLogged && isSameDate(lastLoggedBreakfastDate, dateChooser.getDate())) {
                    JOptionPane.showMessageDialog(null, "You can only log breakfast once per day!");
                    return;
                } else {
                    lastLoggedBreakfastDate = dateChooser.getDate();
                    breakfastLogged = true;
                }
            } else if (mealType == MealType.LUNCH) {
                if (lunchLogged && isSameDate(lastLoggedLunchDate, dateChooser.getDate())) {
                    JOptionPane.showMessageDialog(null, "You can only log lunch once per day!");
                    return;
                } else {
                    lastLoggedLunchDate = dateChooser.getDate();
                    lunchLogged = true;
                }
            } else if (mealType == MealType.DINNER) {
                if (dinnerLogged && isSameDate(lastLoggedDinnerDate, dateChooser.getDate())) {
                    JOptionPane.showMessageDialog(null, "You can only log dinner once per day!");
                    return;
                } else {
                    lastLoggedDinnerDate = dateChooser.getDate();
                    dinnerLogged = true;
                }
            }

            if (timeField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter a valid time.");
                return;
            }

            if (dateChooser.getDate() == null) {
                JOptionPane.showMessageDialog(null, "Please enter a valid date.");
                return;
            }

            try {
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

    private boolean isSameDate(Date date1, Date date2) {
        return date1.getYear() == date2.getYear() && date1.getMonth() == date2.getMonth() && date1.getDate() == date2.getDate();
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
    public void clearInputtedIngredients() {
        this.ingredientsMap.clear();
    }

}
