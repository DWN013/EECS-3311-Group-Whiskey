package co.yorku.nutrifit.database.nutrient.impl;

import co.yorku.nutrifit.database.nutrient.INFDatabase;
import co.yorku.nutrifit.object.NutrientInfo;
import com.google.common.collect.Lists;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.Collections;
import java.util.List;

public class NFDatabase implements INFDatabase {

    private String GET_FOOD_TYPE = "SELECT * FROM food_names WHERE FoodDescription=?";

    private String GET_SIMILAR_FOOD_TYPES = "SELECT * FROM food_names WHERE FoodDescription LIKE ?;";

    private Connection databaseConnection;

    @Override
    public boolean connect() {
        File dbFile = new File("nfdatabase.db");
        if (!dbFile.exists()) {
            try {
                dbFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }

        try {
            Class.forName("org.sqlite.JDBC");
            this.databaseConnection = DriverManager.getConnection("jdbc:sqlite:" + dbFile.getAbsolutePath());
            System.out.println("Established connection with sqlite database. [NFDatabase]");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    @Override
    public boolean setupDatabase() {
        try {
            return this.databaseConnection != null && !this.databaseConnection.isClosed();// tmp
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void closeConnection() {
        if (this.databaseConnection != null) {
            try {
                this.databaseConnection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public NutrientInfo getNutrientInfo(String ingredient) {
        // TODO: connect to DB and stuff
        return new NutrientInfo(ingredient, 100, 25, 20, 5);
    }

    @Override
    public boolean isValidFoodType(String foodType) {

        try {

            PreparedStatement preparedStatement = this.databaseConnection.prepareStatement(this.GET_FOOD_TYPE);
            preparedStatement.setString(1, foodType);

            boolean matches = false;
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet != null) {

                while (resultSet.next() && !matches) {
                    if (resultSet.getString("FoodDescription").equalsIgnoreCase(foodType)) {
                        matches = true;
                    }
                }

                resultSet.close();
            }

            preparedStatement.close();

            return matches;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public List<String> getOtherFoodTypes(String checkFoodType) {

        try {

            List<String> foodTypes = Lists.newArrayList();

            PreparedStatement preparedStatement = this.databaseConnection.prepareStatement(this.GET_SIMILAR_FOOD_TYPES);
            preparedStatement.setString(1, checkFoodType);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet != null) {

                while (resultSet.next()) {
                    String foodDesc = resultSet.getString("FoodDescription");
                    if (foodDesc != null) {
                        foodTypes.add(foodDesc);
                    }
                }

                resultSet.close();
            }

            preparedStatement.close();
            return foodTypes;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return Collections.emptyList();
    }
}
