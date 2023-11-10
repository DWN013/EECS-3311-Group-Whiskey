package co.yorku.nutrifit.object;

public class FoodInfo {

    private String foodName;
    private int foodID;
    private int foodCode;
    private int foodGroupID;
    private int foodSourceID;

    public FoodInfo(String foodName, int foodID, int foodCode, int foodGroupID, int foodSourceID) {
        this.foodName = foodName;
        this.foodID = foodID;
        this.foodCode = foodCode;
        this.foodGroupID = foodGroupID;
        this.foodSourceID = foodSourceID;
    }

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
