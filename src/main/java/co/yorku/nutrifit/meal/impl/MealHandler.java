package co.yorku.nutrifit.meal.impl;

import co.yorku.nutrifit.database.nutrient.INFDatabase;
import co.yorku.nutrifit.logs.impl.Meal;
import co.yorku.nutrifit.meal.IMeal;
import co.yorku.nutrifit.object.FoodInfo;

import java.util.Map;

public class MealHandler implements IMeal {

    private MealCalculator mealCalculator;

    public MealHandler() {
        this.mealCalculator = new MealCalculator();
    }

    @Override
    public int calculateTotalMealCalories(Map<Integer, Integer> foodInfoMap) {
        return mealCalculator.calculateTotalCalories(foodInfoMap);
    }
}
