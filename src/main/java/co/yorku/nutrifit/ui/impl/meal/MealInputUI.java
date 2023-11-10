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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MealInputUI extends NutrifitWindow {

    private static MealInputUI instance;
    private DefaultTableModel tableModel;
    private JTable foodsTable;


    public static MealInputUI getInstance() {
        if (instance == null) {
            instance = new MealInputUI();
        }

        return instance;
    }

    private Map<String, Integer> foodMap = new HashMap<>();


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

        addLabel("Food:");
        JTextField foodField = addTextField(20);

        addLabel("Quantity:");
        JSpinner quantityDropdown = addSpinner();

        tableModel = new DefaultTableModel();
        tableModel.addColumn("Foods");
        tableModel.addColumn("Quantity");

        foodsTable = new JTable(tableModel);

        JScrollPane jScrollPane = new JScrollPane(foodsTable);
        jScrollPane.setPreferredSize(new Dimension(150, 75));

        addComponent(jScrollPane);


        addButton("Add Food", event -> {
            String food = getFood(foodField.getText());
            int quantity = Integer.parseInt(quantityDropdown.getValue().toString());

            // Validate input
            if (food == null) {
                showMessageDialog( "Could not find food specified. Please try again.");
                return;
            }

            foodMap.put(food, quantity);
            quantityDropdown.setValue(1);
            updateFoodsTable();
        });

        addButton("Edit Food", event -> {

            String inputtedFood = getFood(foodField.getText());

            if (inputtedFood != null && foodMap.containsKey(inputtedFood)) {
                int currentQuantity = foodMap.get(inputtedFood);
                String input = openTextInputDialog("Enter the new quantity for " + inputtedFood + " (current: " + currentQuantity + ")");
                if (input != null && !input.isEmpty()) {
                    try {
                        int newQuantity = Integer.parseInt(input);
                        foodMap.put(inputtedFood, newQuantity);
                        updateFoodsTable();
                    } catch (NumberFormatException e) {
                        showMessageDialog("Please enter a valid integer for the quantity.");
                    }
                }
            } else {
                showMessageDialog( "Please add the food before editing.");
            }

        });
        addButton("Delete Food", event -> {

            String selectedFood = getFood(foodField.getText());

            if (selectedFood != null && foodMap.containsKey(selectedFood)) {
                int option = showConfirmationDialog( "Do you want to delete " + selectedFood + " from the food list?");
                if (option == JOptionPane.YES_OPTION) {
                    foodMap.remove(selectedFood);
                    updateFoodsTable();
                }
            } else {
                showMessageDialog( "Please add the food before deleting.");
            }
        });

        addButton("Submit", event -> {

            if (timeField.getText().isEmpty()) {
                showMessageDialog("Please enter a valid time.");
                return;
            }

            if (dateChooser.getDate() == null) {
                showMessageDialog("Please enter a valid date.");
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
                    showMessageDialog("You can only log one " + mealType.getDisplayName() + " once per day!");
                    return;
                }
            }


            try {
                String[] timeSplit = timeField.getText().split(":");
                formattedDateTime.setHours(Integer.parseInt(timeSplit[0]));
                formattedDateTime.setMinutes(Integer.parseInt(timeSplit[1]));

                Map<FoodInfo, Integer> foodInfo = createFoodMap();
                Map<Integer, Integer> foodIDToFoodAmountMap = foodInfo.entrySet().stream().collect(Collectors.toMap(e -> e.getKey().getFoodID(), Map.Entry::getValue));

                NutriFit.getInstance().getUserDatabase().addUserMealLog(
                        NutriFit.getInstance().getLoadedProfile().getId(),
                        new Meal(
                                formattedDateTime,
                                mealType,
                                foodIDToFoodAmountMap,
                                NutriFit.getInstance().getMeal().calculateTotalMealCalories(foodIDToFoodAmountMap)
                        )
                );

                foodField.setText("");
                clearFoodsTable();
                showMessageDialog( "Meal Logging Successful!");
            } catch (NumberFormatException e) {
                showMessageDialog("Please enter a valid time in the format HH:mm.");
            } catch (NullPointerException e) {
                showMessageDialog("Please enter a valid date and time.");
            }

        });

        this.addBackButton(MainMealMenu.getInstance());
        this.build();
    }

    private String getFood(String inputtedUserText) {
        List<String> foodNames = NutriFit.getInstance().getNutrientDatabase().getFoodTypesSimilar(inputtedUserText);

        if (foodNames.isEmpty()) {
            return null;
        }

        return openDropdownDialog("Select Food", "Select Food", 0, foodNames.toArray(new String[0]));
    }

    private Map<FoodInfo, Integer> createFoodMap() {
        Map<FoodInfo, Integer> foodNamesMappedToFoodIDs = Maps.newHashMap();

        for (Map.Entry<String, Integer> stringIntegerEntry : this.foodMap.entrySet()) {
            foodNamesMappedToFoodIDs.put(NutriFit.getInstance().getNutrientDatabase().getFoodInfo(stringIntegerEntry.getKey()), stringIntegerEntry.getValue());
        }

        return foodNamesMappedToFoodIDs;
    }

    private void updateFoodsTable() {
        clearFoodsTable();

        for (Map.Entry<String, Integer> entry : foodMap.entrySet()) {
            String food = entry.getKey();
            Integer quantity = entry.getValue();
            tableModel.addRow(new Object[]{food, quantity});
        }
    }

    private void clearFoodsTable() {
        tableModel.setRowCount(0);
    }
    public void reset() {
        this.clearFoodsTable();
        this.foodMap.clear();
    }

}
