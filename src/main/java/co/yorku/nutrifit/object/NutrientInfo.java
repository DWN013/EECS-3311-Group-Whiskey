package co.yorku.nutrifit.object;

public class NutrientInfo {

    private String food;
    private int calories;
    private int sugar;
    private int carbohydrates;
    private int proteins;

    public NutrientInfo(String food, int calories, int sugar, int carbohydrates, int proteins) {
        this.food = food;
        this.calories = calories;
        this.sugar = sugar;
        this.carbohydrates = carbohydrates;
        this.proteins = proteins;
    }

    public String getFood() {
        return food;
    }

    public int getCalories() {
        return calories;
    }

    public int getSugar() {
        return sugar;
    }

    public int getCarbohydrates() {
        return carbohydrates;
    }

    public int getProteins() {
        return proteins;
    }
}
