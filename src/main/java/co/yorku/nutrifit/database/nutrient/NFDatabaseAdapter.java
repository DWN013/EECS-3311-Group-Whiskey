package co.yorku.nutrifit.database.nutrient;

public class NFDatabaseAdapter implements INFDatabase {

    private INFDatabase nfDatabaseInterface;

    public NFDatabaseAdapter(INFDatabase nfDatabaseInterface) {
        this.nfDatabaseInterface = nfDatabaseInterface;
    }
}
