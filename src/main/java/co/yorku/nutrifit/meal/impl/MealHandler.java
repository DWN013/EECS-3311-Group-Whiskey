package co.yorku.nutrifit.meal.impl;

import co.yorku.nutrifit.database.nutrient.INFDatabase;
import co.yorku.nutrifit.meal.IMeal;

import java.util.Map;

public class MealHandler implements IMeal {

    private MealCalculator mealCalculator;
    private INFDatabase infDatabaseInterface;

    public MealHandler(MealCalculator mealCalculator, INFDatabase infDatabaseInterface) {
        this.mealCalculator = mealCalculator;
        this.infDatabaseInterface = infDatabaseInterface;
    }

    @Override
    public int calculateTotalMealCalories(Map<String, Integer> ingredientsAndQuantities) {
        // TODO: implement
        return 100;
    }
}
