package co.yorku.nutrifit.database.userdata.impl;

import co.yorku.nutrifit.database.userdata.UserDatabaseInterface;
import co.yorku.nutrifit.profile.Profile;
import co.yorku.nutrifit.profile.impl.ProfileHandler;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDatabase implements UserDatabaseInterface {

    private String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS profiles ("
            + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
            + "name TEXT, "
            + "sex BOOL, "
            + "height FLOAT, "
            + "age INTEGER,"
            + "weight FLOAT,"
            + "isMetric BOOL);";

    private String INSERT_USER = "INSERT INTO profiles (name, sex, height, age, weight, isMetric) " +
            "VALUES (?, ?, ?, ?, ?, ?);";

    private String GET_USER = "SELECT * FROM profiles WHERE id=?;";

    private String GET_LAST_INSERTED_USER = "SELECT * FROM profiles;";

    private Connection databaseConnection;

    public UserDatabase() {

        File dbFile = new File("userdatabase.db");
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
            System.out.println("Established connection with sqlite database.");
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }

    @Override
    public void setupDatabase() {
        try {
            PreparedStatement preparedStatement = this.databaseConnection.prepareStatement(CREATE_TABLE);
            preparedStatement.execute();
            preparedStatement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int setupProfile(Profile profile) {
        int id = -1;
        try {
            PreparedStatement preparedStatement = this.databaseConnection.prepareStatement(INSERT_USER);
            preparedStatement.setString(1, profile.getName());
            preparedStatement.setBoolean(2, profile.isMale());
            preparedStatement.setFloat(3, profile.getHeight());
            preparedStatement.setInt(4, profile.getAge());
            preparedStatement.setFloat(5, profile.getWeight());
            preparedStatement.setBoolean(6, profile.isMetric());
            preparedStatement.execute();
            preparedStatement.close();

            preparedStatement = this.databaseConnection.prepareStatement(GET_LAST_INSERTED_USER);

            ResultSet data = preparedStatement.executeQuery();
            while (data != null && data.next()) {
                id = Math.max(id, data.getInt("id"));
            }
            preparedStatement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return id;
    }

    @Override
    public List<Profile> getAllProfiles() {

        List<Profile> profiles = new ArrayList<>();

        try {
            PreparedStatement preparedStatement = this.databaseConnection.prepareStatement(GET_LAST_INSERTED_USER);

            ResultSet data = preparedStatement.executeQuery();
            while (data != null && data.next()) {
                int id = data.getInt("id");
                String name = data.getString("name");
                boolean isMale = data.getBoolean("sex");
                float height = data.getFloat("height");
                int age = data.getInt("age");
                float weight = data.getFloat("weight");
                boolean isMetric = data.getBoolean("isMetric");

                profiles.add(new ProfileHandler(this, id, name, isMale, height, age, weight, isMetric));
            }
            preparedStatement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return profiles;
    }

    @Override
    public Profile getProfile(int id) {

        Profile profile = null;

        try {
            PreparedStatement preparedStatement = this.databaseConnection.prepareStatement(GET_USER);
            preparedStatement.setInt(1, id);

            ResultSet data = preparedStatement.executeQuery();

            while (data != null && data.next()) {

                int userId = data.getInt("id");
                String name = data.getString("name");
                boolean isMale = data.getBoolean("sex");
                float height = data.getFloat("height");
                int age = data.getInt("age");
                float weight = data.getFloat("weight");
                boolean isMetric = data.getBoolean("isMetric");

                profile = new ProfileHandler(this, userId, name, isMale, height, age, weight, isMetric);
                break;
            }

            preparedStatement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return profile;
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
