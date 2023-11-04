package co.yorku.nutrifit.database.nutrient;

import co.yorku.nutrifit.object.NutrientInfo;

public class NFDatabaseAdapter implements INFDatabase {

    private INFDatabase nfDatabaseInterface;

    public NFDatabaseAdapter(INFDatabase nfDatabaseInterface) {
        this.nfDatabaseInterface = nfDatabaseInterface;
    }

    @Override
    public void setupDatabase() {
        this.nfDatabaseInterface.setupDatabase();
    }

    @Override
    public NutrientInfo getNutrientInfo(String ingredient) {
        return this.nfDatabaseInterface.getNutrientInfo(ingredient);
    }

    @Override
    public void close() {
        this.nfDatabaseInterface.close();
    }
}
