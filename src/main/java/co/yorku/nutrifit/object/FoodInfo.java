package co.yorku.nutrifit.object;

// Class FoodInfo for storing information related to food items
public class FoodInfo {

    // Private instance variables representing food item data
    private String foodName;
    private int foodID;
    private int foodCode;
    private int foodGroupID;
    private int foodSourceID;

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

