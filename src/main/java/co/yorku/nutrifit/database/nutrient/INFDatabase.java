package co.yorku.nutrifit.database.nutrient;

import co.yorku.nutrifit.object.NutrientInfo;

public interface INFDatabase {

    void setupDatabase();

    NutrientInfo getNutrientInfo(String ingredient);

}
