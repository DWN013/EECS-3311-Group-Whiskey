package co.yorku.nutrifit.database.nutrient.impl;

import co.yorku.nutrifit.database.nutrient.INFDatabase;
import co.yorku.nutrifit.object.NutrientInfo;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
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

    @Override
    public boolean setupDatabase() {

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
