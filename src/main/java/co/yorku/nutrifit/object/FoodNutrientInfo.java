package co.yorku.nutrifit.object;

import java.util.Map;

// Class FoodNutrientInfo for storing nutrient information related to food items
public class FoodNutrientInfo {

    // Private instance variables representing nutrient information
    private int foodID;
    private int calories;
    private int energy;
    private Map<Integer, Double> nutrientIDAndNutrientValue;

    // Constructor to initialize FoodNutrientInfo with specific attributes
    public FoodNutrientInfo(int foodID, int calories, int energy, Map<Integer, Double> nutrientIDAndNutrientValue) {
        this.foodID = foodID;
        this.calories = calories;
        this.energy = energy;
        this.nutrientIDAndNutrientValue = nutrientIDAndNutrientValue;
    }

    // Getter methods to retrieve various nutrient attributes of the food item
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

