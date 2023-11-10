package co.yorku.nutrifit.database.nutrient;

import co.yorku.nutrifit.database.IDatabase;
import co.yorku.nutrifit.object.FoodInfo;
import co.yorku.nutrifit.object.NutrientData;
import co.yorku.nutrifit.object.FoodNutrientInfo;

import java.util.List;
import java.util.Map;

public interface INFDatabase extends IDatabase {

    FoodInfo getFoodInfo(String ingredient);

    FoodInfo getFoodInfo(int foodID);

    FoodNutrientInfo getNutrientInfo(int foodID);

    NutrientData getNutrientData(int nutrientID);

    String getFoodGroupName(int foodGroupID);

    Map<Integer, String> getAllFoodGroups();

    List<String> getFoodTypesSimilar(String checkFoodType);

}
