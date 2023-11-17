package co.yorku.nutrifit.database.userdata.impl;

import co.yorku.nutrifit.NutriFit;
import co.yorku.nutrifit.database.userdata.IUserDatabase;
import co.yorku.nutrifit.logs.ILog;
import co.yorku.nutrifit.logs.LogIterator;
import co.yorku.nutrifit.logs.impl.Exercise;
import co.yorku.nutrifit.logs.impl.Meal;
import co.yorku.nutrifit.object.*;
import co.yorku.nutrifit.profile.IProfile;
import co.yorku.nutrifit.profile.impl.ProfileHandler;
import com.google.common.collect.Lists;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
/*

    This class is the implementation of IUSerDatabase.
    This specific implementation uses SQLite for the database.

 */
public class UserDatabase implements IUserDatabase {

    /*
        User Related SQL Statements
     */

    // SQL Statement for Creating the User Table
    private String CREATE_USER_TABLE = "CREATE TABLE IF NOT EXISTS profiles ("
            + "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
            + "name TEXT NOT NULL, "
            + "isMale BOOL NOT NULL, "
            + "height FLOAT NOT NULL, "
            + "age INTEGER NOT NULL,"
            + "weight FLOAT NOT NULL,"
            + "isMetric BOOL NOT NULL);";

    // SQL Statement for inserting a user
    private String INSERT_USER = "INSERT INTO profiles (name, isMale, height, age, weight, isMetric) " +
            "VALUES (?, ?, ?, ?, ?, ?);";

    // SQL Statement for updating a user
    private String UPDATE_USER = "UPDATE profiles SET name=?, isMale=?, height=?, age=?, weight=?, isMetric=? WHERE id=?;";

    // SQL Statement for getting getting a user
    private String GET_USER = "SELECT * FROM profiles WHERE id=?;";

    // SQL Statement for getting the last added user
    private String GET_LAST_INSERTED_USER = "SELECT * FROM profiles;";


    /*
        Exercise Related SQL Statements
     */
    // SQL Statement for Creating the exercise logs table
    private String CREATE_EXERCISE_TABLE = "CREATE TABLE IF NOT EXISTS user_exercise_logs ("
            + "userId INTEGER NOT NULL, "
            + "date DATETIME NOT NULL, "
            + "timeSpentInSeconds INTEGER NOT NULL, "
            + "activityType VARCAR(64) NOT NULL, "
            + "intensity VARCAR(12) NOT NULL,"
            + "totalCaloriesBurned INTEGER NOT NULL);";

    // SQL Statement for adding a user log
    private String INSERT_USER_EXERCISE = "INSERT INTO user_exercise_logs " +
            "(userId, date, timeSpentInSeconds, activityType, intensity, totalCaloriesBurned) " +
            "VALUES (?, ?, ?, ?, ?, ?);";

    // SQL Statement for getting a user log
    private String GET_USER_EXERCISE_LOGS = "SELECT * FROM user_exercise_logs WHERE userId=? AND date BETWEEN ? and ?;";


    /*
        Exercise Related SQL Statements
     */

    // SQL Statement for Creating the Meal logs table
    private String CREATE_MEALS_TABLE = "CREATE TABLE IF NOT EXISTS user_meal_logs ("
            + "userId INTEGER NOT NULL, "
            + "date DATETIME NOT NULL, "
            + "mealType VARCHAR(12) NOT NULL, "
            + "ingredients TEXT NOT NULL,"
            + "totalMealCalories INTEGER NOT NULL);";

    // SQL Statement for adding a user meal log
    private String INSERT_USER_MEAL = "INSERT INTO user_meal_logs (userId, date, mealType, ingredients, totalMealCalories) " +
            "VALUES (?, ?, ?, ?, ?);";

    // SQL Statement for getting a user meal log
    private String GET_USER_MEAL_LOGS = "SELECT * FROM user_meal_logs WHERE userId=? AND date BETWEEN ? and ?;";
    private Connection databaseConnection;

    /*

        Below are implementations for the "IUserDatabase" interface and subsequently
        the "IDatabase" (which "IUserDatabase" extends). Specific details about the purpose
        of each method can be found in its respective interface class

     */

    @Override
    public boolean connect() {
        // Create an instance of the file
        File dbFile = new File("userdatabase.db");
        if (!dbFile.exists()) { // Check if the file exists
            try {
                dbFile.createNewFile(); // Create the file if it does not exist
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }

        // Try/catch for connecting to your Database
        try {
            Class.forName("org.sqlite.JDBC");
            // Attempt to establish a connection to the Database (userdatabase.db file)
            this.databaseConnection = DriverManager.getConnection("jdbc:sqlite:" + dbFile.getAbsolutePath());
            // We have connected to the database successfully
            return true;
        } catch (Exception e) {
            // For whatever reason, print the exception that is thrown and return false indicating a failure
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean setupDatabase() {

        try {
            PreparedStatement preparedStatement = this.databaseConnection.prepareStatement(this.CREATE_USER_TABLE); // Prepare Statement to create user table
            preparedStatement.execute(); // Execute the statement
            preparedStatement.close(); // Close the statement

            preparedStatement = this.databaseConnection.prepareStatement(this.CREATE_EXERCISE_TABLE); // Prepare Statement to create the exercise logs table
            preparedStatement.execute(); // Execute the statement
            preparedStatement.close(); // Close the statement

            preparedStatement = this.databaseConnection.prepareStatement(this.CREATE_MEALS_TABLE); // Prepare Statement to create the meal logs table
            preparedStatement.execute(); // Execute the statement
            preparedStatement.close(); // Close the statement
            return true;
        } catch (Exception e) {
            // For whatever reason if we encounter a failure, print the exception and return false
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void closeConnection() {
        // If the database connection is not null, close the connection to the database
        if (this.databaseConnection != null) {
            try {
                this.databaseConnection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public int setupProfile(IProfile profile) {
        int id = -1;
        try {
            PreparedStatement preparedStatement = this.databaseConnection.prepareStatement(this.INSERT_USER); // Prepare the SQL Statement

            // Set the parameters required for the sql statement
            preparedStatement.setString(1, profile.getName());
            preparedStatement.setBoolean(2, profile.isMale());
            preparedStatement.setFloat(3, profile.getHeight());
            preparedStatement.setInt(4, profile.getAge());
            preparedStatement.setFloat(5, profile.getWeight());
            preparedStatement.setBoolean(6, profile.isMetric());
            preparedStatement.execute(); // Execute the statement
            preparedStatement.close(); // Close the statement

            preparedStatement = this.databaseConnection.prepareStatement(this.GET_LAST_INSERTED_USER); // Prepare the SQL Statement

            ResultSet data = preparedStatement.executeQuery(); // Execute the query
            if (data != null) { // If there was data returned from the query
                while (data.next()) { // loop through all the data, once we get to the end we know it will be the last added user
                    id = Math.max(id, data.getInt("id")); // read the "id" from the data returned
                }
                data.close(); // Close the ResultSet object
            }
            preparedStatement.close(); // Close the PreparedStatement
        } catch (Exception e) {
            // If for whatever reason an exception is thrown, log it
            e.printStackTrace();
        }

        return id;
    }

    @Override
    public boolean updateProfile(IProfile profile) {

        try {

            PreparedStatement preparedStatement = this.databaseConnection.prepareStatement(this.UPDATE_USER); // Prepare the SQL Statenment

            // Set the parameters required for the sql statement
            preparedStatement.setString(1, profile.getName());
            preparedStatement.setBoolean(2, profile.isMale());
            preparedStatement.setFloat(3, profile.getHeight());
            preparedStatement.setInt(4, profile.getAge());
            preparedStatement.setFloat(5, profile.getWeight());
            preparedStatement.setBoolean(6, profile.isMetric());
            preparedStatement.setInt(7, profile.getId());
            preparedStatement.execute(); // Execute the statement
            preparedStatement.close(); // Close the statement

            return true; // return true indicating the profile update was a success
        } catch (Exception e) {
            // If for whatever reason an exception is thrown, log it
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public List<IProfile> getAllProfiles() {

        List<IProfile> profiles = Lists.newArrayList();

        try {

            PreparedStatement preparedStatement = this.databaseConnection.prepareStatement(this.GET_LAST_INSERTED_USER); // Prepare SQL Statement

            ResultSet data = preparedStatement.executeQuery(); // Execute the query
            if (data != null) { // Check to see if there was data returned from the query
                while (data.next()) { // Loop through all the profiles returned

                    // Read data for the profile
                    int id = data.getInt("id");
                    String name = data.getString("name");
                    boolean isMale = data.getBoolean("isMale");
                    float height = data.getFloat("height");
                    int age = data.getInt("age");
                    float weight = data.getFloat("weight");
                    boolean isMetric = data.getBoolean("isMetric");

                    // Create and add the profile to the profiles list
                    profiles.add(new ProfileHandler(id, name, isMale, height, age, weight, isMetric));
                }

                data.close(); // Close the ResultSet object
            }
            preparedStatement.close(); // Close the PreparedStatement
        } catch (Exception e) {
            // If for whatever reason an exception is thrown, log it
            e.printStackTrace();
        }

        return profiles;
    }

    @Override
    public IProfile getProfile(int id) {

        IProfile profile = null; // Profile that will be returned

        try {

            PreparedStatement preparedStatement = this.databaseConnection.prepareStatement(this.GET_USER); // Prepare the SQL Statement
            preparedStatement.setInt(1, id); // Set the id in the SQL Statement to the one provided

            ResultSet data = preparedStatement.executeQuery(); // Execute the query

            if (data != null && data.next()) { // Check to see if we have some returned data

                // Extract the data returned from the query
                int userId = data.getInt("id");
                String name = data.getString("name");
                boolean isMale = data.getBoolean("isMale");
                float height = data.getFloat("height");
                int age = data.getInt("age");
                float weight = data.getFloat("weight");
                boolean isMetric = data.getBoolean("isMetric");

                profile = new ProfileHandler(userId, name, isMale, height, age, weight, isMetric); // Create a new instance of ProfileHandler with the data provided

                data.close(); // Close the ResultSet object
            }

            preparedStatement.close(); // Close the PreparedStatement
        } catch (Exception e) {
            // If for whatever reason an exception is thrown, log it
            e.printStackTrace();
        }

        return profile;
    }

    @Override
    public LogIterator getUserExerciseLogs(int userId, java.util.Date fromDate, java.util.Date toDate) {

        List<ILog> logs = Lists.newArrayList();

        try {

            PreparedStatement preparedStatement = this.databaseConnection.prepareStatement(this.GET_USER_EXERCISE_LOGS); // Prepare SQL Statement

            // Set the parameters required for the sql statement
            preparedStatement.setInt(1, userId);
            preparedStatement.setDate(2, new Date(fromDate.getTime()));
            preparedStatement.setDate(3, new Date(toDate.getTime()));

            ResultSet results = preparedStatement.executeQuery(); // Execute the query

            if (results != null) { // Check to see if we have some data returned
                while (results.next()) { // Loop through every exercise log returned

                    // Read values returned
                    java.util.Date date = new java.util.Date(results.getDate("date").getTime());
                    int timeSpentInSeconds = results.getInt("timeSpentInSeconds");
                    ActivityType activityType = ActivityType.valueOf(results.getString("activityType"));
                    Intensity intensity = Intensity.valueOf(results.getString("intensity"));
                    int totalCaloriesBurned = results.getInt("totalCaloriesBurned");

                    // Create and add an exercise object to the logs list
                    logs.add(new Exercise(date, timeSpentInSeconds, activityType, intensity, totalCaloriesBurned));
                }

                results.close(); // Close the ResultSet object
            }

            preparedStatement.close(); // Close the PreparedStatement
        } catch (Exception e) {
            // If for whatever reason an exception is thrown, log it and return null
            e.printStackTrace();
            return null;
        }
        return new LogIterator(logs); // Return a new LogIterator (Iterator Design Pattern)
    }


    @Override
    public boolean addUserExerciseLog(int userId, Exercise exerciseLog) {

        try {
            PreparedStatement preparedStatement = this.databaseConnection.prepareStatement(this.INSERT_USER_EXERCISE); // Prepare SQL Statement

            // Set the parameters required for the sql statement
            preparedStatement.setInt(1, userId);
            preparedStatement.setDate(2, new Date(exerciseLog.getDate().getTime()));
            preparedStatement.setInt(3, exerciseLog.getTimeSpentInSeconds());
            preparedStatement.setString(4, exerciseLog.getActivityType().toString());
            preparedStatement.setString(5, exerciseLog.getIntensity().toString());
            preparedStatement.setInt(6, exerciseLog.getTotalCaloriesBurned());

            preparedStatement.execute(); // Execute the prepared statement
            preparedStatement.close(); // Close the PreparedStatement
        } catch (Exception e) {
            // If for whatever reason an exception is thrown, log it and return false
            e.printStackTrace();
            return false;
        }

        return true; // Return true indicating a success
    }

    @Override
    public LogIterator getUserMealLogs(int userId, java.util.Date fromDate, java.util.Date toDate) {

        List<ILog> logs = Lists.newArrayList();

        try {

            PreparedStatement preparedStatement = this.databaseConnection.prepareStatement(this.GET_USER_MEAL_LOGS); // Prepare SQL Statement

            // Set the parameters required for the sql statement
            preparedStatement.setInt(1, userId);
            preparedStatement.setDate(2, new Date(fromDate.getTime()));
            preparedStatement.setDate(3, new Date(toDate.getTime()));

            ResultSet results = preparedStatement.executeQuery(); // Execute the query


            if (results != null) { // Check to see if we have some data returned
                while (results.next()) { // Loop through every meal log returned

                    // Read values returned
                    java.util.Date date = new java.util.Date(results.getDate("date").getTime());
                    MealType mealType = MealType.valueOf(results.getString("mealType"));
                    Map<Integer, Integer> ingredients = NutriFit.getInstance().getGson().fromJson(results.getString("ingredients"), new TypeToken<Map<Integer, Integer>>() {}.getType());
                    int totalMealCalories = results.getInt("totalMealCalories");

                    // Create and add a Meal object to the logs list
                    logs.add(new Meal(date, mealType, ingredients, totalMealCalories));
                }

                results.close(); // Close the ResultSet object
            }

            preparedStatement.close(); // Close the PreparedStatement
        } catch (Exception e) {
            // If for whatever reason an exception is thrown, log it and return null
            e.printStackTrace();
            return null;
        }
        return new LogIterator(logs); // Return a new LogIterator (Iterator Design Pattern)
    }

    @Override
    public boolean addUserMealLog(int userId, Meal mealLog) {

        try {
            PreparedStatement preparedStatement = this.databaseConnection.prepareStatement(this.INSERT_USER_MEAL); // Prepare SQL Statement

            // Set the parameters required for the sql statement
            preparedStatement.setInt(1, userId);
            preparedStatement.setDate(2, new Date(mealLog.getDate().getTime()));
            preparedStatement.setString(3, mealLog.getMealType().toString());
            preparedStatement.setString(4, NutriFit.getInstance().getGson().toJson(mealLog.getFoodIDAndAmounts()));
            preparedStatement.setInt(5, mealLog.getTotalMealCalories());

            preparedStatement.execute(); // Execute the Prepared Statement
            preparedStatement.close(); // Close the ResultSet object
        } catch (Exception e) {
            // If for whatever reason an exception is thrown, log it and return false
            e.printStackTrace();
            return false;
        }

        return true; // Return true indicating a success
    }
}
