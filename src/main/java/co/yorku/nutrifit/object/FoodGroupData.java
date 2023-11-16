package co.yorku.nutrifit.object;

// Class FoodGroupData for storing information related to food groups
public class FoodGroupData {

    // Private instance variables representing food group data
    private int foodGroupID;
    private int foodGroupCode;
    private String foodGroupName;
    private int parentFoodGroupID;
    private int percentage;

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
