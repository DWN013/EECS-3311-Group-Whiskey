package co.yorku.nutrifit.meal.impl;

import co.yorku.nutrifit.NutriFit;
import co.yorku.nutrifit.object.FoodNutrientInfo;

import java.util.Map;

/*
 * MealCalculator class responsible for calculating total calories of a meal
 */

public class MealCalculator {

    // Method to calculate total calories based on a map of food information
    public int calculateTotalCalories(Map<Integer, Integer> foodInfoMap) {

        // Initialize total calories
        int totalCalories = 0;

        // Iterate through each entry in the foodInfoMap
        for (Map.Entry<Integer, Integer> foodInfoIntegerEntry : foodInfoMap.entrySet()) {
            // Retrieve nutrient information for the food ID from the NutriFit database
            FoodNutrientInfo foodNutrientInfo = NutriFit.getInstance().getNutrientDatabase().getNutrientInfo(foodInfoIntegerEntry.getKey());

            // Calculate calories for each food and accumulate to total calories
            totalCalories += (foodNutrientInfo.getCalories() * foodInfoIntegerEntry.getValue());
        }

        // Return the calculated total calories for the meal
        return totalCalories;
    }

}

