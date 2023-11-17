package co.yorku.nutrifit.database;

/*

    This IDatabase is a generic Database interface which holds
    methods that will be present in subsequent interfaces like
    the IUserDatabase and the NFDatabase

 */
public interface IDatabase {

    // This method Connects to the database
    boolean connect();

    // This method setups up the database (such as creating tables)
    boolean setupDatabase();

    // This method closes the connection to the database
    void closeConnection();

}
