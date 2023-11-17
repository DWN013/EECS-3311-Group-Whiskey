package co.yorku.nutrifit.database.nutrient.impl;

import co.yorku.nutrifit.database.nutrient.INFDatabase;
import co.yorku.nutrifit.object.FoodGroupData;
import co.yorku.nutrifit.object.FoodInfo;
import co.yorku.nutrifit.object.NutrientData;
import co.yorku.nutrifit.object.FoodNutrientInfo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.io.File;
import java.sql.*;
import java.util.List;
import java.util.Map;

/*

    This class is the implementation of INFDatabase.
    This specific implementation uses SQLite for the database.

 */
public class NFDatabase implements INFDatabase {

    // SQL Statement for getting FoodInfo based on a Food description (or food name)
    private String GET_FOOD_DATA_FROM_NAME = "SELECT * FROM food_names WHERE FoodDescription=?";

    // SQL Statement for getting FoodInfo based on a foodID
    private String GET_FOOD_DATA_FROM_FOODID = "SELECT * FROM food_names WHERE foodID=?";

    // SQL Statement for getting Nutrient Info based on a foodID
    private String GET_FOOD_NUTRIENT_INFO_FROM_FOODID = "SELECT * FROM nutrient_amounts WHERE foodID=?";

    // SQL Statement for getting information about a nutrient based on a nutrientID
    private String GET_SPECIFIC_NUTRIENT_DATA_FROM_NUTRIENTID = "SELECT * FROM nutrient_names WHERE nutrientID=?";

    // SQL Statement for getting all the food groups
    private String GET_ALL_FOOD_GROUPS = "SELECT * FROM food_groups;";

    // SQL Statement for getting similar food types
    private String GET_SIMILAR_FOOD_TYPES = "SELECT * FROM food_names WHERE FoodDescription LIKE ?;";

    // The variable is the instance of our Database connection (SQLite)
    private Connection databaseConnection;
    /*

        Below are implementations for the "INFDatabase" interface and subsequently
        the "IDatabase" (which "INFDatabase" extends). Specific details about the purpose
        of each method can be found in its respective interface class

     */

    @Override
    public boolean connect() {
        // Create an instance of the file
        File dbFile = new File("nfdatabase.db");
        if (!dbFile.exists()) { // Check if the file exists
            System.out.println("Could not find nfdatabase."); // If the file does not exist, return false
            return false;
        }

        // Try/catch for connecting to your Database
        try {
            Class.forName("org.sqlite.JDBC");
            // Attempt to establish a connection to the Database (nfdatabase.db file)
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
            // We want to make sure that we are connected to the database
            return this.databaseConnection != null && !this.databaseConnection.isClosed();
        } catch (SQLException e) {
            // For whatever reason if we are not connected, print whatever exception was thrown and return false indicating a failure
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
    public FoodInfo getFoodInfo(String food) {

        try {

            // FoodInfo object variable that will hold the instance that will be returned
            FoodInfo foodInfo = null;

            // Prepare a statement using the "GET_FOOD_DATA_FROM_NAME" SQL Statement
            PreparedStatement preparedStatement = this.databaseConnection.prepareStatement(this.GET_FOOD_DATA_FROM_NAME);
            preparedStatement.setString(1, food); // Set the first variable in the statement to the food name

            ResultSet resultSet = preparedStatement.executeQuery(); // Execute the query, store the result in a variable

            if (resultSet != null && resultSet.next()) { // Check if our query has data
                foodInfo = buildFoodInfo(resultSet); // Build the food info object
                resultSet.close(); // Close the ResultSet object
            }

            preparedStatement.close(); // Close the PreparedStatement

            return foodInfo; // Return the FoodInfo object
        } catch (Exception e) {
            // If for whatever reason an exception is thrown, log it and return null
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public FoodInfo getFoodInfo(int foodID) {
        try {

            // FoodInfo object variable that will hold the instance that will be returned
            FoodInfo foodInfo = null;

            // Prepare a statement using the "GET_FOOD_DATA_FROM_FOODID" SQL Statement
            PreparedStatement preparedStatement = this.databaseConnection.prepareStatement(this.GET_FOOD_DATA_FROM_FOODID);
            preparedStatement.setInt(1, foodID); // Set the first variable in the statement to the foodID

            ResultSet resultSet = preparedStatement.executeQuery(); // Execute the query, store the result in a variable

            if (resultSet != null && resultSet.next()) { // Check if our query has data
                foodInfo = buildFoodInfo(resultSet); // Build the food info object
                resultSet.close(); // Close the ResultSet object
            }

            preparedStatement.close(); // Close the PreparedStatement

            return foodInfo; // Return the FoodInfo object
        } catch (Exception e) {
            // If for whatever reason an exception is thrown, log it and return null
            e.printStackTrace();
            return null;
        }
    }


    @Override
    public FoodNutrientInfo getNutrientInfo(int foodID) {
        try {

            // Prepare a statement using the "GET_FOOD_NUTRIENT_INFO_FROM_FOODID" SQL Statement
            PreparedStatement preparedStatement = this.databaseConnection.prepareStatement(this.GET_FOOD_NUTRIENT_INFO_FROM_FOODID);
            preparedStatement.setInt(1, foodID); // set the first variable in the statement to foodID

            ResultSet resultSet = preparedStatement.executeQuery(); // Execute the query, store the result in a variable

            int calories = -1; // The calorie value for the food
            int energy = -1; // The energy value for the food
            Map<Integer, Double> nutrientIDAndValue = Maps.newHashMap(); // This map stores a Key-Value Pair of NutrientID and nutrientValue for a specific food

            if (resultSet != null) { // Check if we have returned database

                while (resultSet.next()) { // loop through all the returned data
                    int nutrientID = resultSet.getInt("nutrientID"); // the nutrientID
                    double nutrientValue = resultSet.getDouble("nutrientValue"); // the nutrientValue

                    if (nutrientID == 208) { // If nutrientID it is 208 then we know it's calories
                        calories = (int) Math.round(nutrientValue); // Store calories in the variable created above
                    } else if (nutrientID == 268) { // if nutrientID is 268 it is energy
                        energy = (int) Math.round(nutrientValue); // Store calories in the variable created above
                    } else { // Otherwise, it's not calories or energy, just put it into our nutrients map
                        nutrientIDAndValue.put(nutrientID, nutrientValue); // Put into the nutrientIDAndValue map
                    }
                }

                resultSet.close(); // Close the resultset
            }

            preparedStatement.close(); // Close the PreparedStatement

            // Build and return a FoodNutrientInfo object based on the data returned from the Database
            return new FoodNutrientInfo(foodID, calories, energy, nutrientIDAndValue);
        } catch (Exception e) {
            // If for whatever reason an exception is thrown, log it and return null
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public NutrientData getNutrientData(int nutrientID) {
        try {

            // NutrientData object variable that will hold the instance that will be returned
            NutrientData nutrientData = null;

            // Prepare a statement using the "GET_SPECIFIC_NUTRIENT_DATA_FROM_NUTRIENTID" SQL Statement
            PreparedStatement preparedStatement = this.databaseConnection.prepareStatement(this.GET_SPECIFIC_NUTRIENT_DATA_FROM_NUTRIENTID);
            preparedStatement.setInt(1, nutrientID); // Set the first variable in the statement to nutrientID

            ResultSet resultSet = preparedStatement.executeQuery(); // Query the database, store result in a variable

            if (resultSet != null && resultSet.next()) { // Check if our query returned any database

                int nutrientCode = resultSet.getInt("nutrientCode"); // Get the NutrientCode from the query
                String symbol = resultSet.getString("symbol"); // Get the symbol from the query
                String unit = resultSet.getString("unit"); // Get the unit from the query
                String name = resultSet.getString("name"); // Get the name from the query
                String tag = resultSet.getString("tag"); // Get the tag from the query

                // Construct a new NutrientData object with the queried information and assign it to "nutrientData"
                nutrientData = new NutrientData(nutrientID, nutrientCode, symbol, unit, name, tag);

                resultSet.close(); // Close the resultset
            }

            preparedStatement.close(); // Close the prepared stateement

            return nutrientData; // return the nutrientData object
        } catch (Exception e) {
            // If for whatever reason an exception is thrown, log it and return null
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String getFoodGroupName(int foodGroupID) {

        // call the "getAllFoodGroups()" method and search it for the specific foodGroupID we want
        FoodGroupData foodGroupData = this.getAllFoodGroups().stream().filter(f -> f.getFoodGroupID() == foodGroupID).findFirst().orElse(null);
        if (foodGroupData == null) { // If we do not find food group data based on the food group ID then we return null
            return null;
        }

        return foodGroupData.getFoodGroupName(); // Return the food group name from the food group data object
    }

    @Override
    public List<FoodGroupData> getAllFoodGroups() {

        // This list will a list of FoodGroupData objects
        List<FoodGroupData> data = Lists.newArrayList();

        try {

            // Prepare a statement using the "GET_ALL_FOOD_GROUPS" SQL Statement
            PreparedStatement preparedStatement = this.databaseConnection.prepareStatement(this.GET_ALL_FOOD_GROUPS);
            ResultSet resultSet = preparedStatement.executeQuery(); // Execute the query, store result in this variable

            if (resultSet != null) { // Check if our query returned any database

                while (resultSet.next()) { // Loop through every food group that was returned from the DB

                    // Create a FoodGroupData object based on the data returend from the database
                    FoodGroupData foodGroupData = new FoodGroupData(
                            resultSet.getInt("FoodGroupID"),
                            resultSet.getInt("FoodGroupCode"),
                            resultSet.getString("FoodGroupName"),
                            resultSet.getInt("ParentFoodGroupID"),
                            resultSet.getInt("Percentage")
                    );

                    data.add(foodGroupData); // Add the object we just created to the list to be returned by this method
                }
                resultSet.close(); // Close the resutset object
            }

            preparedStatement.close(); // Close the prepared statement

            return data; // Return the data we just queried
        } catch (Exception e) {
            // If for whatever reason an exception is thrown, log it and return null
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<String> getFoodTypesSimilar(String checkFoodType) {

        try {

            // This holds a list of strings
            List<String> foodTypes = Lists.newArrayList();

            // Prepare a statement using the "GET_SIMILAR_FOOD_TYPES" SQL Statement
            PreparedStatement preparedStatement = this.databaseConnection.prepareStatement(this.GET_SIMILAR_FOOD_TYPES);
            preparedStatement.setString(1, "%" + checkFoodType + "%"); // Set the first variable in the SQL Statement

            ResultSet resultSet = preparedStatement.executeQuery(); // Execute the query, store the result in a variable

            if (resultSet != null) { // Check to see if we got any data returned

                while (resultSet.next()) { // Loop through all data returned
                    String foodDesc = resultSet.getString("FoodDescription"); // Read the "FoodDescription" returned
                    if (foodDesc != null) { // If the returned food desc is not null, add it to our foodTypes list
                        foodTypes.add(foodDesc);
                    }
                }

                resultSet.close(); // Close the resultset
            }

            preparedStatement.close(); // Close the prepared statement

            return foodTypes; // Return the list of similar food types
        } catch (Exception e) {
            // If for whatever reason an exception is thrown, log it and return null
            e.printStackTrace();
            return null;
        }
    }


    // This method is used to build a FoodInfo object given a ResultSet object (which we just queried from the database)
    private FoodInfo buildFoodInfo(ResultSet resultSet) throws SQLException {
        String foodDesc = resultSet.getString("FoodDescription"); // Get the food description
        int foodID = resultSet.getInt("foodID"); // get the food id
        int foodCode = resultSet.getInt("foodCode"); // get the food c ode
        int foodGroupID = resultSet.getInt("foodGroupID"); // get the food group id
        int foodSourceID = resultSet.getInt("foodSourceID"); // get the food source id

        return new FoodInfo(foodDesc, foodID, foodCode, foodGroupID, foodSourceID); // construct and return a FoodInfo object
    }
}
