package co.yorku.nutrifit.database.nutrient;

import co.yorku.nutrifit.object.FoodInfo;
import co.yorku.nutrifit.object.NutrientData;
import co.yorku.nutrifit.object.FoodNutrientInfo;

import java.util.List;
import java.util.Map;

public class NFDatabaseAdapter implements INFDatabase {

    private INFDatabase nfDatabaseInterface;

    public NFDatabaseAdapter(INFDatabase nfDatabaseInterface) {
        this.nfDatabaseInterface = nfDatabaseInterface;
    }

    @Override
    public boolean connect() {
        return this.nfDatabaseInterface.connect();
    }

    @Override
    public boolean setupDatabase() {
        return this.nfDatabaseInterface.setupDatabase();
    }

    @Override
    public void closeConnection() {
        this.nfDatabaseInterface.closeConnection();
    }

    @Override
    public FoodInfo getFoodInfo(String ingredient) {
        return this.nfDatabaseInterface.getFoodInfo(ingredient);
    }

    @Override
    public FoodInfo getFoodInfo(int foodID) {
        return this.nfDatabaseInterface.getFoodInfo(foodID);
    }

    @Override
    public FoodNutrientInfo getNutrientInfo(int foodID) {
        return this.nfDatabaseInterface.getNutrientInfo(foodID);
    }

    @Override
    public NutrientData getNutrientData(int nutrientID) {
        return this.nfDatabaseInterface.getNutrientData(nutrientID);
    }

    @Override
    public String getFoodGroupName(int foodGroupID) {
        return this.nfDatabaseInterface.getFoodGroupName(foodGroupID);
    }

    @Override
    public Map<Integer, String> getAllFoodGroups() {
        return this.nfDatabaseInterface.getAllFoodGroups();
    }

    @Override
    public List<String> getFoodTypesSimilar(String checkFoodType) {
        return this.nfDatabaseInterface.getFoodTypesSimilar(checkFoodType);
    }
}
