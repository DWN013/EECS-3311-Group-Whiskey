package co.yorku.nutrifit.ui.impl.meal;

import co.yorku.nutrifit.NutriFit;
import co.yorku.nutrifit.logs.LogIterator;
import co.yorku.nutrifit.logs.impl.Meal;
import co.yorku.nutrifit.object.daterange.DateRange;
import co.yorku.nutrifit.ui.NutrifitWindow;
import co.yorku.nutrifit.ui.impl.main.NutriFitMainUI;
import com.google.common.collect.Maps;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/*
 * MealDisplayUI will show all the meal logs existing in the database to the user 
 */

public class MealJournalUI extends NutrifitWindow {

    private Map<Integer, Meal> cachedMeals = Maps.newHashMap();

    public MealJournalUI() {
        super("Meal Journal View", new GridLayout(2, 1));
        // Set the date range from 9999999 days ago to 9999999 days in the future
        Date fromDate = new Date(System.currentTimeMillis() - TimeUnit.DAYS.toMillis(9999999));
        Date toDate = new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(9999999));
        // Retrieve meal logs for the user within the specified date range
        LogIterator meals = NutriFit.getInstance().getUserDatabase().getUserMealLogs(NutriFit.getInstance().getLoadedProfile().getId(), new DateRange(fromDate, toDate));
        meals.sortByDateAscending(); // Sort

        DefaultTableModel defaultTableModel = new DefaultTableModel() // Create the default table model
        {
            @Override
            public boolean isCellEditable(int row, int column) { // Override isCellEditable and return false because we don't want the user editing the cell
                return false;
            }
        };

        // Add our columns
        defaultTableModel.addColumn("Date");
        defaultTableModel.addColumn("Meal Type");
        defaultTableModel.addColumn("Total Calories");

        JTable table = new JTable(defaultTableModel); // Create a JTable
        JScrollPane jScrollPane = new JScrollPane(table); // Create a scroller pane
        jScrollPane.setPreferredSize(new Dimension(600, 300)); // Set the height

        table.addMouseListener(new MouseListener() { // Add a mouse listener so we can listen for when a user clicks a specific meal
            @Override
            public void mouseClicked(MouseEvent e) { // When the user clicks a meal
                Meal meal = cachedMeals.get(table.getSelectedRow()); // Get the cached meal based on the selected row
                hideWindow(); // Hide the current window
                new MealDisplayUI(MealJournalUI.this, meal).showWindow(); // Open the MealDisplayUI and pass in this current window (for the back button) and the meal the user selected
            }

            @Override
            public void mousePressed(MouseEvent e) {}
            @Override
            public void mouseReleased(MouseEvent e) {}
            @Override
            public void mouseEntered(MouseEvent e) {}
            @Override
            public void mouseExited(MouseEvent e) {}
        });

        int index = 0; // Used for the cache
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM dd yyyy"); // Create a SimpleDateFormat object to format Date objects
        //Loop through meal logs
        while (meals.hasNext()) {
            // Get the next meal from the iterator
            Meal meal = (Meal) meals.getNext();
            // Format the day
            String day = simpleDateFormat.format(meal.getDate());

            // Add the row
            defaultTableModel.addRow(new Object[] { day, meal.getMealType().getDisplayName(), String.valueOf(meal.getTotalMealCalories())});
            cachedMeals.put(index, meal); // Cache the meal
            index++; // Increment the index
        }

        this.addComponent(jScrollPane); // Add the scroller
        this.addBackButton(MainMealMenu.getInstance()); // Add the back button
        this.build(); // Build the window
    }
}
