package co.yorku.nutrifit.object;

public class FoodGroupData {

    private int foodGroupID;
    private int foodGroupCode;
    private String foodGroupName;
    private int parentFoodGroupID;
    private int percentage;

    public FoodGroupData(int foodGroupID, int foodGroupCode, String foodGroupName, int parentFoodGroupID, int percentage) {
        this.foodGroupID = foodGroupID;
        this.foodGroupCode = foodGroupCode;
        this.foodGroupName = foodGroupName;
        this.parentFoodGroupID = parentFoodGroupID;
        this.percentage = percentage;
    }

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
