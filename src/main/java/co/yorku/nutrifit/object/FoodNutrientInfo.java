package co.yorku.nutrifit.object;

import java.util.Map;

public class FoodNutrientInfo {

    private int foodID;
    private int calories;
    private int energy;
    private Map<Integer, Double> nutrientIDAndNutrientValue;

    public FoodNutrientInfo(int foodID, int calories, int energy, Map<Integer, Double> nutrientIDAndNutrientValue) {
        this.foodID = foodID;
        this.calories = calories;
        this.energy = energy;
        this.nutrientIDAndNutrientValue = nutrientIDAndNutrientValue;
    }

    public int getFoodID() {
        return foodID;
    }

    public int getCalories() {
        return calories;
    }

    public int getEnergy() {
        return energy;
    }

    public Map<Integer, Double> getNutrientIDAndNutrientValue() {
        return nutrientIDAndNutrientValue;
    }
}
