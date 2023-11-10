package co.yorku.nutrifit.database.nutrient.impl;

import co.yorku.nutrifit.database.nutrient.INFDatabase;
import co.yorku.nutrifit.object.FoodInfo;
import co.yorku.nutrifit.object.NutrientData;
import co.yorku.nutrifit.object.FoodNutrientInfo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class NFDatabase implements INFDatabase {

    private String GET_FOOD_DATA_FROM_NAME = "SELECT * FROM food_names WHERE FoodDescription=?";
    private String GET_FOOD_DATA_FROM_FOODID = "SELECT * FROM food_names WHERE foodID=?";
    private String GET_NUTRIENT_INFO_FROM_FOODID = "SELECT * FROM nutrient_amounts WHERE foodID=?";

    private String GET_NUTRIENT_DATA_FROM_NUTRIENTID = "SELECT * FROM nutrient_names WHERE nutrientID=?";

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
    public FoodInfo getFoodInfo(String food) {

        try {

            FoodInfo foodInfo = null;

            PreparedStatement preparedStatement = this.databaseConnection.prepareStatement(this.GET_FOOD_DATA_FROM_NAME);
            preparedStatement.setString(1, food);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet != null && resultSet.next()) {
                foodInfo = buildMealInfo(resultSet);
                resultSet.close();
            }

            preparedStatement.close();

            return foodInfo;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public FoodInfo getFoodInfo(int foodID) {
        try {

            FoodInfo foodInfo = null;

            PreparedStatement preparedStatement = this.databaseConnection.prepareStatement(this.GET_FOOD_DATA_FROM_FOODID);
            preparedStatement.setInt(1, foodID);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet != null && resultSet.next()) {
                foodInfo = buildMealInfo(resultSet);
                resultSet.close();
            }

            preparedStatement.close();

            return foodInfo;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public FoodNutrientInfo getNutrientInfo(int foodID) {
        try {

            PreparedStatement preparedStatement = this.databaseConnection.prepareStatement(this.GET_NUTRIENT_INFO_FROM_FOODID);
            preparedStatement.setInt(1, foodID);

            ResultSet resultSet = preparedStatement.executeQuery();

            int calories = -1;
            int energy = -1;
            Map<Integer, Double> nutrientIDAndValue = Maps.newHashMap();

            if (resultSet != null) {

                while (resultSet.next()) {
                    int nutrientID = resultSet.getInt("nutrientID");
                    double nutrientValue = resultSet.getDouble("nutrientValue");

                    if (nutrientID == 208) {
                        calories = (int) Math.round(nutrientValue);
                    } else if (nutrientID == 268) {
                        energy = (int) Math.round(nutrientValue);
                    } else {
                        nutrientIDAndValue.put(nutrientID, nutrientValue);
                    }
                }

                resultSet.close();
            }

            preparedStatement.close();

            return new FoodNutrientInfo(foodID, calories, energy, nutrientIDAndValue);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public NutrientData getNutrientData(int nutrientID) {
        try {

            NutrientData nutrientData = null;
            PreparedStatement preparedStatement = this.databaseConnection.prepareStatement(this.GET_NUTRIENT_DATA_FROM_NUTRIENTID);
            preparedStatement.setInt(1, nutrientID);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet != null && resultSet.next()) {

                int nutrientCode = resultSet.getInt("nutrientCode");
                String symbol = resultSet.getString("symbol");
                String unit = resultSet.getString("unit");
                String name = resultSet.getString("name");
                String tag = resultSet.getString("tag");

                nutrientData = new NutrientData(nutrientID, nutrientCode, symbol, unit, name, tag);

                resultSet.close();
            }

            preparedStatement.close();

            return nutrientData;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<String> getFoodTypesSimilar(String checkFoodType) {

        try {

            List<String> foodTypes = Lists.newArrayList();

            PreparedStatement preparedStatement = this.databaseConnection.prepareStatement(this.GET_SIMILAR_FOOD_TYPES);
            preparedStatement.setString(1, "%" + checkFoodType + "%");

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


    private FoodInfo buildMealInfo(ResultSet resultSet) throws SQLException {
        String foodDesc = resultSet.getString("FoodDescription");
        int foodID = resultSet.getInt("foodID");
        int foodCode = resultSet.getInt("foodCode");
        int foodGroupID = resultSet.getInt("foodGroupID");
        int foodSourceID = resultSet.getInt("foodSourceID");

        return new FoodInfo(foodDesc, foodID, foodCode, foodGroupID, foodSourceID);
    }
}
