package co.yorku.nutrifit.database.nutrient;

import co.yorku.nutrifit.object.NutrientInfo;

import java.util.List;

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
    public NutrientInfo getNutrientInfo(String ingredient) {
        return this.nfDatabaseInterface.getNutrientInfo(ingredient);
    }

    @Override
    public boolean isValidFoodType(String foodType) {
        return this.nfDatabaseInterface.isValidFoodType(foodType);
    }

    @Override
    public List<String> getOtherFoodTypes(String checkFoodType) {
        return this.nfDatabaseInterface.getOtherFoodTypes(checkFoodType);
    }
}
