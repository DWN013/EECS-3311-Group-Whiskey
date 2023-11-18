package co.yorku.nutrifit.object;

/*
 * Class FoodInfo for storing information related to food items
 */

public class FoodInfo {

    // Private instance variables representing food item data
    private String foodName;    // Name of the food item
    private int foodID;         // Unique ID for the food item
    private int foodCode;       // Code representing the food item
    private int foodGroupID;    // ID for the food group to which the item belongs
    private int foodSourceID;   // ID for the source of the food item

    // Constructor to initialize FoodInfo with specific attributes
    public FoodInfo(String foodName, int foodID, int foodCode, int foodGroupID, int foodSourceID) {
        this.foodName = foodName;
        this.foodID = foodID;
        this.foodCode = foodCode;
        this.foodGroupID = foodGroupID;
        this.foodSourceID = foodSourceID;
    }

    // Getter methods to retrieve various attributes of the food item
    public String getFoodName() {
        return foodName;
    }

    public int getFoodID() {
        return foodID;
    }

    public int getFoodCode() {
        return foodCode;
    }

    public int getFoodGroupID() {
        return foodGroupID;
    }

    public int getFoodSourceID() {
        return foodSourceID;
    }
}

