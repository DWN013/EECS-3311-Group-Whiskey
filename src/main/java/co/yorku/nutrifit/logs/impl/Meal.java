package co.yorku.nutrifit.logs.impl;

import co.yorku.nutrifit.logs.ILog;
import co.yorku.nutrifit.object.MealType;

import java.util.Date;
import java.util.Map;

public class Meal extends ILog {
    private MealType mealType;
    private Map<String, Integer> ingredientsAndQuantities;
    private int totalMealCalories;

    public Meal(Date date, MealType mealType, Map<String, Integer> ingredientsAndQuantities, int totalMealCalories) {
        super(date);
        this.mealType = mealType;
        this.ingredientsAndQuantities = ingredientsAndQuantities;
        this.totalMealCalories = totalMealCalories;
    }

    public MealType getMealType() {
        return mealType;
    }

    public Map<String, Integer> getIngredientsAndQuantities() {
        return ingredientsAndQuantities;
    }

    public int getTotalMealCalories() {
        return totalMealCalories;
    }
}
