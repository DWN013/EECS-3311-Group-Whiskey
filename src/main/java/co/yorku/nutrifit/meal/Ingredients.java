package co.yorku.nutrifit.meal;

import co.yorku.nutrifit.database.nutrient.NFDatabaseInterface;

public class Ingredients {

    private NFDatabaseInterface nfDatabaseInterface;

    public Ingredients(NFDatabaseInterface nfDatabaseInterface) {
        this.nfDatabaseInterface = nfDatabaseInterface;
    }
}
