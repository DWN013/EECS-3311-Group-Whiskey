package co.yorku.nutrifit.object;

// Class FoodGroupData for storing information related to food groups
public class FoodGroupData {

    // Private instance variables representing food group data
    private int foodGroupID;         // Unique ID for the food group
    private int foodGroupCode;       // Code representing the food group
    private String foodGroupName;    // Name of the food group
    private int parentFoodGroupID;   // ID for the parent food group
    private int percentage;          // Percentage associated with the food group

    // Constructor to initialize FoodGroupData with specific attributes
    public FoodGroupData(int foodGroupID, int foodGroupCode, String foodGroupName, int parentFoodGroupID, int percentage) {
        this.foodGroupID = foodGroupID;
        this.foodGroupCode = foodGroupCode;
        this.foodGroupName = foodGroupName;
        this.parentFoodGroupID = parentFoodGroupID;
        this.percentage = percentage;
    }

    // Getter methods to retrieve various attributes of the food group
    public int getFoodGroupID() {
        return foodGroupID;
    }

    public int getFoodGroupCode() {
        return foodGroupCode;
    }

    public String getFoodGroupName() {
        return foodGroupName;
    }

    public int getParentFoodGroupID() {
        return parentFoodGroupID;
    }

    public int getPercentage() {
        return percentage;
    }
}
