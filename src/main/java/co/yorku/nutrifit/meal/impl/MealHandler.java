package co.yorku.nutrifit.meal.impl;

import co.yorku.nutrifit.meal.IMeal;
import java.util.Map;

// MealHandler class implementing the IMeal interface
public class MealHandler implements IMeal {

    // Private instance variable of type MealCalculator
    private MealCalculator mealCalculator;

    // Constructor initializing the mealCalculator instance
    public MealHandler() {
        this.mealCalculator = new MealCalculator();
    }

    // Method to calculate total meal calories based on a map of food information
    @Override
    public int calculateTotalMealCalories(Map<Integer, Integer> foodInfoMap) {

        return mealCalculator.calculateTotalCalories(foodInfoMap);
    }
}

