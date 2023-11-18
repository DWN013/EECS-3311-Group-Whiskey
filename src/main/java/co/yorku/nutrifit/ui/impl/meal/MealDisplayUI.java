package co.yorku.nutrifit.ui.impl.meal;

import co.yorku.nutrifit.NutriFit;
import co.yorku.nutrifit.logs.impl.Meal;
import co.yorku.nutrifit.object.FoodInfo;
import co.yorku.nutrifit.object.FoodNutrientInfo;
import co.yorku.nutrifit.object.NutrientData;
import co.yorku.nutrifit.ui.NutrifitWindow;
import com.google.common.collect.Maps;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

/*
    This class extends NutrifitWindow.
    The purpose of this class is to show a breakdown for a specific meal.
 */
public class MealDisplayUI extends NutrifitWindow {

    private DecimalFormat decimalFormat = new DecimalFormat(); // Create decimal format instance

    public MealDisplayUI(NutrifitWindow parent, Meal meal) {
        super("Meal Display", new GridLayout(4, 1));

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM dd yyyy"); // Create a SimpleDateFormat object to format Date objects

        // Create JLabel with meal info
        JLabel mainInfoLabel = addLabel("<html>Date: " + simpleDateFormat.format(meal.getDate()) + "<br/>" + "Meal Type: " + meal.getMealType().getDisplayName() + "<br/>" + "Total Calories: " + meal.getTotalMealCalories() + "</html>");
        mainInfoLabel.setFont(new Font(mainInfoLabel.getFont().getName(), mainInfoLabel.getFont().getStyle(), 18)); // Set the Font Size

        DefaultTableModel foods = new DefaultTableModel() // Create DefaultTableModel
        {
            @Override
            public boolean isCellEditable(int row, int column) { // Override the isCellEditable method and return false
                return false;
            }
        };

        foods.addColumn("Food"); // Add a Food Column
        foods.addColumn("Nutrient Data"); // Add a Nutrient Data Column

        JTable foodsTable = new JTable(foods); // Create a JTable
        JScrollPane foodScroller = new JScrollPane(foodsTable); // Create a Scroller Pane with the table
        foodScroller.setPreferredSize(new Dimension(600, 300)); // Set the desired size

        int maxEntries = 1; // Total entries (to figure out the maximum row height)
        Map<NutrientData, Double> nutrientSums = Maps.newHashMap(); // Nutrient Sums map that has all the nutrients in this specific meal

        for (Map.Entry<Integer, Integer> integerIntegerEntry : meal.getFoodIDAndAmounts().entrySet()) { // Loop through all the foods in this meal

            FoodInfo foodInfo = NutriFit.getInstance().getNutrientDatabase().getFoodInfo(integerIntegerEntry.getKey()); // Query for Food Info
            if (foodInfo == null) continue;

            FoodNutrientInfo foodNutrientInfo = NutriFit.getInstance().getNutrientDatabase().getNutrientInfo(foodInfo.getFoodID()); // Query for Nutrient Info
            if (foodNutrientInfo == null) continue;

            StringBuilder sb = new StringBuilder(); // Create a string builder
            sb.append("<html>");
            int total = 0;
            for (Map.Entry<Integer, Double> integerDoubleEntry : foodNutrientInfo.getNutrientIDAndNutrientValue().entrySet()) { // Loop through every nutrient in the food

                NutrientData nutrientData = NutriFit.getInstance().getNutrientDatabase().getNutrientData(integerDoubleEntry.getKey()); // Get Nutrient info for the food
                if (nutrientData == null) continue;

                if (integerDoubleEntry.getValue() <= 0.1) continue; // Skip any values less than 0.1 as they are negligible

                // Append the data to the string builder
                sb.append(nutrientData.getName()).append(":").append(" ").append(decimalFormat.format(integerDoubleEntry.getValue())).append(nutrientData.getUnit()).append("<br/>");
                nutrientSums.put(nutrientData, nutrientSums.getOrDefault(nutrientData, 0.0) + (integerDoubleEntry.getValue() * integerIntegerEntry.getValue())); // Increment the nutrient to the total nutrients for this meal
                total++; // Increment the total
            }

            if (total <= 0) continue; // Skip if we didn't add anything
            maxEntries = Math.max(maxEntries, total); // Set maxEntries to the larger number

            sb.append("</html>");
            foods.addRow(new Object[] { "<html>" + foodInfo.getFoodName() + " <br/>Quantity: " + integerIntegerEntry.getValue() + "</html>",  sb.toString()}); // Add the food name and nutrients to
        }

        foodsTable.setRowHeight(foodsTable.getRowHeight() * maxEntries); // Set the row height

        StringBuilder sb = new StringBuilder(); // Create a String Builder
        sb.append("<html>");
        sb.append("Total Nutrient Info Meal:");
        sb.append("<br/>");
        sb.append("<br/>");
        for (Map.Entry<NutrientData, Double> nutrientDataDoubleEntry : nutrientSums.entrySet().stream()
                .sorted(Map.Entry.<NutrientData, Double>comparingByValue().reversed()) // Reverse it
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                )).entrySet()) { // Sort then loop through the nutrients

            NutrientData nutrientData = nutrientDataDoubleEntry.getKey(); // Get Nutrient Data

            // Append the data to the string builder
            sb.append(nutrientData.getName()).append(":").append(" ").append(decimalFormat.format(nutrientDataDoubleEntry.getValue())).append(nutrientData.getUnit()).append("<br/>");
        }
        sb.append("</html>");

        // Create a label with all the nutrient info
        JLabel totalNutrientInfoLabel = new JLabel(sb.toString());
        totalNutrientInfoLabel.setFont(new Font(totalNutrientInfoLabel.getFont().getName(), totalNutrientInfoLabel.getFont().getStyle(), 18)); // Set the font size

        JScrollPane nutrientScroller = new JScrollPane(totalNutrientInfoLabel); // Create a scroller pane
        nutrientScroller.setPreferredSize(new Dimension(600, 300)); // Set the size

        this.addComponent(foodScroller); // Add the food scroller
        this.addComponent(nutrientScroller); // Add the nutrient scroller
        this.addBackButton(parent); // Add the back button
        this.build(); // Build the window
    }
}
