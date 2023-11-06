package co.yorku.nutrifit.database.nutrient;

import co.yorku.nutrifit.object.NutrientInfo;

public interface INFDatabase {

    boolean setupDatabase();

    NutrientInfo getNutrientInfo(String ingredient);

    void close();

}
