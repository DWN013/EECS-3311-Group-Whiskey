package co.yorku.nutrifit.logs.impl;

import co.yorku.nutrifit.logs.ILog;
import co.yorku.nutrifit.object.MealType;

import java.util.Date;
import java.util.Map;

//  Meal class extending ILog
public class Meal extends ILog {

    // Private instance variables
    private MealType mealType;
    private Map<Integer, Integer> foodIDAndAmounts;
    private int totalMealCalories;

    // Constructor initializing a Meal object with date, meal type, food information, and total calories
    public Meal(Date date, MealType mealType, Map<Integer, Integer> foodIDAndAmounts, int totalMealCalories) {
        super(date); // Invoke superclass constructor with date
        this.mealType = mealType; // Assign meal type
        this.foodIDAndAmounts = foodIDAndAmounts; // Assign food information
        this.totalMealCalories = totalMealCalories; // Assign total calories
    }

    // Getter method to retrieve the meal type
    public MealType getMealType() {
        return mealType;
    }

    // Getter method to retrieve food IDs and amounts map
    public Map<Integer, Integer> getFoodIDAndAmounts() {
        return foodIDAndAmounts;
    }

    // Getter method to retrieve the total meal calories
    public int getTotalMealCalories() {
        return totalMealCalories;
    }
}

