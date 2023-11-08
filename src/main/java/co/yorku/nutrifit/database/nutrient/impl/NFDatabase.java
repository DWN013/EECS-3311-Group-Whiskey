package co.yorku.nutrifit.database.nutrient.impl;

import co.yorku.nutrifit.database.nutrient.INFDatabase;
import co.yorku.nutrifit.object.NutrientInfo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class NFDatabase implements INFDatabase {
    private Connection databaseConnection;

    public NFDatabase() {
        File dbFile = new File("nfdatabase.db");
        if (!dbFile.exists()) {
            try {
                dbFile.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        try {
            Class.forName("org.sqlite.JDBC");
            this.databaseConnection = DriverManager.getConnection("jdbc:sqlite:" + dbFile.getAbsolutePath());
            System.out.println("Established connection with sqlite database. [NFDatabase]");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    private String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS nutrient_amounts ("
//            + "foodID INTEGER NOT NULL, "
//            + "nutrientID INTEGER NOT NULL, "
//            + "nutrientValue DOUBLE NOT NULL, "
//            + "nutrientSourceID INTEGER NOT NULL,"
//            + "FOREIGN KEY (foodID) REFERENCES food_names(foodID),"
//            + "FOREIGN KEY (nutrientID) REFERENCES nutrient_names(nutrientID));";

    @Override
    public boolean setupDatabase() {

        // Temp code we'll delete later, used to get CSV data stuff into nfdatabase.db :)
//        try {
//
//            File file = new File("data.csv");
//            FileReader fileReader = new FileReader(file);
//            BufferedReader br = new BufferedReader(fileReader);
//            String line = "";
//            String[] tempArr;
//
//            databaseConnection.prepareStatement(CREATE_TABLE).execute();
//
//
//            PreparedStatement preparedStatement = databaseConnection.prepareStatement("INSERT INTO nutrient_amounts (foodID, nutrientID, nutrientValue, nutrientSourceID) VALUES(?, ?, ?, ?)");
//
//           int i = 0;
//            while((line = br.readLine()) != null) {
//
//                try {
//                    String[] data = line.split(",(?=(?:[^\\\"]*\\\"[^\\\"]*\\\")*[^\\\"]*$)");
//                    if (data[0].equals("FoodID")) continue;
//
//                    int foodID = Integer.parseInt(data[0]);
//                    int nutrientID = Integer.parseInt(data[1]);
//                    double nutrientValue = Double.parseDouble(data[2]);
//                    int nutrientSourceID = Integer.parseInt(data[5]);
//
//
//                    preparedStatement.setInt(1, foodID);
//                    preparedStatement.setInt(2, nutrientID);
//                    preparedStatement.setDouble(3, nutrientValue);
//                    preparedStatement.setInt(4, nutrientSourceID);
//                    preparedStatement.addBatch();
//
//                    i++;
//
//                    if (i % 1000 == 0) {
//                        PreparedStatement temp = preparedStatement;
//                        preparedStatement = databaseConnection.prepareStatement("INSERT INTO nutrient_amounts (foodID, nutrientID, nutrientValue, nutrientSourceID) VALUES(?, ?, ?, ?)");
//                        System.out.println("Executing batch on async thread");
//                        new Thread(() -> {
//                            try {
//                                temp.executeBatch();
//                            } catch (SQLException e) {
//                                throw new RuntimeException(e);
//                            }
//                        }).run();
//                    }
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//            br.close();
//            System.out.println("Executing batch remainder...");
//            preparedStatement.executeBatch();
//            System.out.println("done!");
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        return true; // tmp
    }

    @Override
    public NutrientInfo getNutrientInfo(String ingredient) {
        // TODO: connect to DB and stuff
        return new NutrientInfo(ingredient, 100, 25, 20, 5);
    }

    @Override
    public void close() {
        if (this.databaseConnection != null) {
            try {
                this.databaseConnection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
