package co.yorku.nutrifit.database.nutrient;

import co.yorku.nutrifit.database.IDatabase;
import co.yorku.nutrifit.object.NutrientInfo;

import java.util.List;

public interface INFDatabase extends IDatabase {

    NutrientInfo getNutrientInfo(String ingredient);

    boolean isValidFoodType(String foodType);

    List<String> getOtherFoodTypes(String checkFoodType);

}
