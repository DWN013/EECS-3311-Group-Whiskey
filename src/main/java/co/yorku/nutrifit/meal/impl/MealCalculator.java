package co.yorku.nutrifit.meal.impl;

import co.yorku.nutrifit.NutriFit;
import co.yorku.nutrifit.object.FoodNutrientInfo;

import java.util.Map;

public class MealCalculator {

    public int calculateTotalCalories(Map<Integer, Integer> foodInfoMap) {

        int totalCalories = 0;
        for (Map.Entry<Integer, Integer> foodInfoIntegerEntry : foodInfoMap.entrySet()) {
            FoodNutrientInfo foodNutrientInfo = NutriFit.getInstance().getNutrientDatabase().getNutrientInfo(foodInfoIntegerEntry.getKey());
            totalCalories+=(foodNutrientInfo.getCalories() * foodInfoIntegerEntry.getValue());
        }

        return totalCalories;
    }

}
