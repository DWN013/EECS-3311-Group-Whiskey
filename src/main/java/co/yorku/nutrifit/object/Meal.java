package co.yorku.nutrifit.object;

import java.util.Date;
import java.util.Map;

public class Meal {
    private Date date;
    private MealType mealType;
    private Map<String, Integer> ingredientsAndQuantities;
    private int totalMealCalories;

    public Meal(Date date, MealType mealType, Map<String, Integer> ingredientsAndQuantities, int totalMealCalories) {
        this.date = date;
        this.mealType = mealType;
        this.ingredientsAndQuantities = ingredientsAndQuantities;
        this.totalMealCalories = totalMealCalories;
    }

    public Date getDate() {
        return date;
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
