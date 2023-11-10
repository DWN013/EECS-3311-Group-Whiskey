package co.yorku.nutrifit.logs.impl;

import co.yorku.nutrifit.logs.ILog;
import co.yorku.nutrifit.object.MealType;

import java.util.Date;
import java.util.Map;

public class Meal extends ILog {
    private MealType mealType;
    private Map<Integer, Integer> foodIDAndAmounts;
    private int totalMealCalories;

    public Meal(Date date, MealType mealType, Map<Integer, Integer> foodIDAndAmounts, int totalMealCalories) {
        super(date);
        this.mealType = mealType;
        this.foodIDAndAmounts = foodIDAndAmounts;
        this.totalMealCalories = totalMealCalories;
    }

    public MealType getMealType() {
        return mealType;
    }

    public Map<Integer, Integer> getFoodIDAndAmounts() {
        return foodIDAndAmounts;
    }

    public int getTotalMealCalories() {
        return totalMealCalories;
    }
}
