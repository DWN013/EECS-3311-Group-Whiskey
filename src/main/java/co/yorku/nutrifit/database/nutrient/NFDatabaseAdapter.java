package co.yorku.nutrifit.database.nutrient;

import co.yorku.nutrifit.object.FoodGroupData;
import co.yorku.nutrifit.object.FoodInfo;
import co.yorku.nutrifit.object.NutrientData;
import co.yorku.nutrifit.object.FoodNutrientInfo;

import java.util.List;

/*

    This NFDatabaseAdapter wraps an implementation for the INFDatabase interface
    so that we can pass whatever implementation of INFDatabase into it.
    This also uses the "Adapter" design pattern

 */
public class NFDatabaseAdapter implements INFDatabase
{

    // This variable holds the instance of INFDatabase
    private INFDatabase nfDatabaseInterface;

    // Constructor for NFDatabaseAdapter
    public NFDatabaseAdapter(INFDatabase nfDatabaseInterface) {
        this.nfDatabaseInterface = nfDatabaseInterface;
    }

    /*

        Below are methods used to interact with the NFDatabase
        Specific comments about these methods can be found
        in the respective interface classes

     */

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
    public FoodInfo getFoodInfo(String food) {
        return this.nfDatabaseInterface.getFoodInfo(food);
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
    public List<FoodGroupData> getAllFoodGroups() {
        return this.nfDatabaseInterface.getAllFoodGroups();
    }

    @Override
    public List<String> getFoodTypesSimilar(String checkFoodType) {
        return this.nfDatabaseInterface.getFoodTypesSimilar(checkFoodType);
    }
}
