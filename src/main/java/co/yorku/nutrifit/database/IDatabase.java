package co.yorku.nutrifit.database;

public interface IDatabase {

    boolean connect();

    boolean setupDatabase();

    void closeConnection();

}
