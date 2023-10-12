package co.yorku.nutrifit.database.nutrient;

public class NFDatabaseAdapter implements NFDatabaseInterface {

    private NFDatabaseInterface nfDatabaseInterface;

    public NFDatabaseAdapter(NFDatabaseInterface nfDatabaseInterface) {
        this.nfDatabaseInterface = nfDatabaseInterface;
    }
}
