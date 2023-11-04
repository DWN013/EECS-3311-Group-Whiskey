package co.yorku.nutrifit.database.nutrient.impl;

import co.yorku.nutrifit.database.nutrient.INFDatabase;
import co.yorku.nutrifit.object.NutrientInfo;

public class NFDatabase implements INFDatabase {

    @Override
    public void setupDatabase() {
        // TODO
    }

    @Override
    public NutrientInfo getNutrientInfo(String ingredient) {
        // TODO: connect to DB and stuff
        return new NutrientInfo(ingredient, 100, 25, 20, 5);
    }

    @Override
    public void close() {

    }
}
