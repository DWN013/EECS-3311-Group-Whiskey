package co.yorku.nutrifit.ui.impl.meal;

import co.yorku.nutrifit.ui.NutrifitWindow;
import co.yorku.nutrifit.ui.impl.main.NutriFitMainUI;

import java.awt.*;

// MainMealMenu class handling the meal menu UI
public class MainMealMenu extends NutrifitWindow {

    // Singleton instance
    private static MainMealMenu instance;

    // Method to retrieve a singleton instance of MainMealMenu
    public static MainMealMenu getInstance() {
        if (instance == null) {
            instance = new MainMealMenu(); //Create new instance of meal menu if does not exist
        }
        return instance;
    }

    // Constructor initializes the MainMealMenu UI layout
    private MainMealMenu() {
        super("Meal Menu", new GridLayout());

        // Add buttons for logging a meal and showing meal logs
        // Log Meal button
        addButton("Log Meal", event -> {
            this.hideWindow(); // Hide the current window
            MealInputUI.getInstance().reset(); // Reset the MealInputUI instance
            MealInputUI.getInstance().showWindow(); // Show the MealInputUI window
        });
        // Meal menu button
        addButton("Show Meal Log", event -> {
            this.hideWindow(); // Hide the current window
            new MealDisplayUI().showWindow(); // Show the MealDisplayUI window
        });

        // Add a back button to return to the main NutriFit UI
        this.addBackButton(NutriFitMainUI.getInstance());
        this.build(); // Build the UI
    }
}
