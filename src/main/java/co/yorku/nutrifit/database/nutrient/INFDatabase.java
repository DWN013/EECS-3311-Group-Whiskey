package co.yorku.nutrifit.database.nutrient;

import co.yorku.nutrifit.database.IDatabase;
import co.yorku.nutrifit.object.FoodGroupData;
import co.yorku.nutrifit.object.FoodInfo;
import co.yorku.nutrifit.object.NutrientData;
import co.yorku.nutrifit.object.FoodNutrientInfo;

import java.util.List;
import java.util.Map;

/*

    This is the INFDatabase interface.
    It holds methods related to Nutrient File information.
    It extends the IDatabase interface for some generic methods
    like connecting to the database, setting up tables, etc.

 */
public interface INFDatabase extends IDatabase {

    // This method returns a FoodInfo object given a food as a string
    FoodInfo getFoodInfo(String food);

    // This method returns a FoodInfo object given a foodID as an integer
    FoodInfo getFoodInfo(int foodID);

    // This method returns a FoodNutrientInfo object given a foodID (Nutrient Information about a specific food)
    FoodNutrientInfo getNutrientInfo(int foodID);

    // This method returns a NutrientData object given a NutrientID (Nutrient Information about a specific Nutrient)
    NutrientData getNutrientData(int nutrientID);

    // This method returns a Food Group Name given a Food Group ID
    String getFoodGroupName(int foodGroupID);

    // This method returns a list of FoodGroupData objects which contain information about all the food groups in the DB
    List<FoodGroupData> getAllFoodGroups();

    // This method returns a list of similar food types in the database given a name of a food
    List<String> getFoodTypesSimilar(String checkFoodType);

}
