package co.yorku.nutrifit.database.userdata.impl;

import co.yorku.nutrifit.database.userdata.UserDatabaseInterface;
import co.yorku.nutrifit.profile.Profile;
import co.yorku.nutrifit.profile.impl.ProfileHandler;

import java.io.File;
import java.io.IOException;
import java.sql.*;

public class UserDatabase implements UserDatabaseInterface {

    private String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS profiles ("
            + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
            + "name TEXT, "
            + "sex TEXT, "
            + "height TEXT, "
            + "age INTEGER)";

    private String INSERT_USER = "INSERT INTO profiles (name, sex, height, age) VALUES (?, ?, ?, ?)";

    private String GET_USER = "SELECT * FROM profiles WHERE id=?";

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
            databaseConnection = DriverManager.getConnection("jdbc:sqlite:" + dbFile.getAbsolutePath());
            System.out.println("Established connection with sqlite database.");
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }

    @Override
    public void setupDatabase() {
        try {
            PreparedStatement preparedStatement = databaseConnection.prepareStatement(CREATE_TABLE);
            preparedStatement.execute();
            preparedStatement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setupProfile(Profile profile) {
        try {
            PreparedStatement preparedStatement = databaseConnection.prepareStatement(INSERT_USER);
            preparedStatement.setString(1, profile.getName());
            preparedStatement.setString(2, profile.getSex());
            preparedStatement.setString(3, profile.getHeight());
            preparedStatement.setInt(4, profile.getAge());
            preparedStatement.execute();
            preparedStatement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Profile getProfile(int id) {

        Profile profile = null;

        try {
            PreparedStatement preparedStatement = databaseConnection.prepareStatement(GET_USER);
            preparedStatement.setInt(1, id);

            ResultSet data = preparedStatement.executeQuery();

            while (data != null && data.next()) {

                String name = data.getString("name");
                String sex = data.getString("sex");
                String height = data.getString("height");
                int age = data.getInt("age");

                profile = new ProfileHandler(this, name, sex, height, age);
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
