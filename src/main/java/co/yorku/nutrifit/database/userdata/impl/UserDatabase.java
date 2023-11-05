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
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UserDatabase implements IUserDatabase {

    // User
    private String CREATE_USER_TABLE = "CREATE TABLE IF NOT EXISTS profiles ("
            + "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
            + "name TEXT NOT NULL, "
            + "isMale BOOL NOT NULL, "
            + "height FLOAT NOT NULL, "
            + "age INTEGER NOT NULL,"
            + "weight FLOAT NOT NULL,"
            + "isMetric BOOL NOT NULL);";

    private String INSERT_USER = "INSERT INTO profiles (name, isMale, height, age, weight, isMetric) " +
            "VALUES (?, ?, ?, ?, ?, ?);";

    private String UPDATE_USER = "UPDATE profiles SET name=?, isMale=?, height=?, age=?, weight=?, isMetric=? WHERE id=?;";

    private String GET_USER = "SELECT * FROM profiles WHERE id=?;";

    private String GET_LAST_INSERTED_USER = "SELECT * FROM profiles;";


    // Exercise Logging
    private String CREATE_EXERCISE_TABLE = "CREATE TABLE IF NOT EXISTS user_exercise_logs ("
            + "userId INTEGER NOT NULL, "
            + "date DATETIME NOT NULL, "
            + "timeSpentInSeconds INTEGER NOT NULL, "
            + "activityType VARCAR(64) NOT NULL, "
            + "intensity VARCAR(12) NOT NULL,"
            + "totalCaloriesBurned INTEGER NOT NULL);";

    private String INSERT_USER_EXERCISE = "INSERT INTO user_exercise_logs " +
            "(userId, date, timeSpentInSeconds, activityType, intensity, totalCaloriesBurned) " +
            "VALUES (?, ?, ?, ?, ?, ?);";

    private String GET_USER_EXERCISE_LOGS = "SELECT * FROM user_exercise_logs WHERE userId=? AND date BETWEEN ? and ?;";

    // Meal Logging

    private String CREATE_MEALS_TABLE = "CREATE TABLE IF NOT EXISTS user_meal_logs ("
            + "userId INTEGER NOT NULL, "
            + "date DATETIME NOT NULL, "
            + "mealType VARCHAR(12) NOT NULL, "
            + "ingredients TEXT NOT NULL,"
            + "totalMealCalories INTEGER NOT NULL);";

    private String INSERT_USER_MEAL = "INSERT INTO user_meal_logs (userId, date, mealType, ingredients, totalMealCalories) " +
            "VALUES (?, ?, ?, ?, ?);";

    private String GET_USER_MEAL_LOGS = "SELECT * FROM user_meal_logs WHERE userId=? AND date BETWEEN ? and ?;";
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
            PreparedStatement preparedStatement = this.databaseConnection.prepareStatement(this.CREATE_USER_TABLE);
            preparedStatement.execute();
            preparedStatement.close();

            preparedStatement = this.databaseConnection.prepareStatement(this.CREATE_EXERCISE_TABLE);
            preparedStatement.execute();
            preparedStatement.close();

            preparedStatement = this.databaseConnection.prepareStatement(this.CREATE_MEALS_TABLE);
            preparedStatement.execute();
            preparedStatement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int setupProfile(IProfile profile) {
        int id = -1;
        try {
            PreparedStatement preparedStatement = this.databaseConnection.prepareStatement(this.INSERT_USER);
            preparedStatement.setString(1, profile.getName());
            preparedStatement.setBoolean(2, profile.isMale());
            preparedStatement.setFloat(3, profile.getHeight());
            preparedStatement.setInt(4, profile.getAge());
            preparedStatement.setFloat(5, profile.getWeight());
            preparedStatement.setBoolean(6, profile.isMetric());
            preparedStatement.execute();
            preparedStatement.close();

            preparedStatement = this.databaseConnection.prepareStatement(this.GET_LAST_INSERTED_USER);

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
    public boolean updateProfile(IProfile profile) {

        try {

            PreparedStatement preparedStatement = this.databaseConnection.prepareStatement(this.UPDATE_USER);
            preparedStatement.setString(1, profile.getName());
            preparedStatement.setBoolean(2, profile.isMale());
            preparedStatement.setFloat(3, profile.getHeight());
            preparedStatement.setInt(4, profile.getAge());
            preparedStatement.setFloat(5, profile.getWeight());
            preparedStatement.setBoolean(6, profile.isMetric());
            preparedStatement.setInt(7, profile.getId());
            preparedStatement.execute();
            preparedStatement.close();

            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public List<IProfile> getAllProfiles() {

        List<IProfile> profiles = new ArrayList<>();

        try {
            PreparedStatement preparedStatement = this.databaseConnection.prepareStatement(this.GET_LAST_INSERTED_USER);

            ResultSet data = preparedStatement.executeQuery();
            while (data != null && data.next()) {
                int id = data.getInt("id");
                String name = data.getString("name");
                boolean isMale = data.getBoolean("isMale");
                float height = data.getFloat("height");
                int age = data.getInt("age");
                float weight = data.getFloat("weight");
                boolean isMetric = data.getBoolean("isMetric");

                profiles.add(new ProfileHandler(id, name, isMale, height, age, weight, isMetric));
            }
            preparedStatement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return profiles;
    }

    @Override
    public IProfile getProfile(int id) {

        IProfile profile = null;

        try {
            PreparedStatement preparedStatement = this.databaseConnection.prepareStatement(this.GET_USER);
            preparedStatement.setInt(1, id);

            ResultSet data = preparedStatement.executeQuery();

            while (data != null && data.next()) {

                int userId = data.getInt("id");
                String name = data.getString("name");
                boolean isMale = data.getBoolean("isMale");
                float height = data.getFloat("height");
                int age = data.getInt("age");
                float weight = data.getFloat("weight");
                boolean isMetric = data.getBoolean("isMetric");

                profile = new ProfileHandler(userId, name, isMale, height, age, weight, isMetric);
                break;
            }

            preparedStatement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return profile;
    }

    @Override
    public LogIterator getUserExerciseLogs(int userId, java.util.Date fromDate, java.util.Date toDate) {

        List<ILog> logs = new ArrayList<>();

        try {

            PreparedStatement preparedStatement = this.databaseConnection.prepareStatement(this.GET_USER_EXERCISE_LOGS);

            preparedStatement.setInt(1, userId);
            preparedStatement.setDate(2, new Date(fromDate.getTime()));
            preparedStatement.setDate(3, new Date(toDate.getTime()));

            ResultSet results = preparedStatement.executeQuery();

            if (results != null) {
                while (results.next()) {

                    java.util.Date date = new java.util.Date(results.getDate("date").getTime());
                    int timeSpentInSeconds = results.getInt("timeSpentInSeconds");
                    ActivityType activityType = ActivityType.valueOf(results.getString("activityType"));
                    Intensity intensity = Intensity.valueOf(results.getString("intensity"));
                    int totalCaloriesBurned = results.getInt("totalCaloriesBurned");

                    logs.add(new Exercise(date, timeSpentInSeconds, activityType, intensity, totalCaloriesBurned));
                }

                results.close();
            }

            preparedStatement.close();
        } catch (Exception e) {
            e.printStackTrace();
            return null; // SOme error occured
        }
        return new LogIterator(logs);
    }


    @Override
    public boolean addUserExerciseLog(int userId, Exercise exerciseLog) {

        try {
            PreparedStatement preparedStatement = this.databaseConnection.prepareStatement(this.INSERT_USER_EXERCISE);
            preparedStatement.setInt(1, userId);
            preparedStatement.setDate(2, new Date(exerciseLog.getDate().getTime()));
            preparedStatement.setInt(3, exerciseLog.getTimeSpentInSeconds());
            preparedStatement.setString(4, exerciseLog.getActivityType().toString());
            preparedStatement.setString(5, exerciseLog.getIntensity().toString());
            preparedStatement.setInt(6, exerciseLog.getTotalCaloriesBurned());
            preparedStatement.execute();
            preparedStatement.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    @Override
    public LogIterator getUserMealLogs(int userId, java.util.Date fromDate, java.util.Date toDate) {

        List<ILog> logs = new ArrayList<>();

        try {

            PreparedStatement preparedStatement = this.databaseConnection.prepareStatement(this.GET_USER_MEAL_LOGS);

            preparedStatement.setInt(1, userId);
            preparedStatement.setDate(2, new Date(fromDate.getTime()));
            preparedStatement.setDate(3, new Date(toDate.getTime()));

            ResultSet results = preparedStatement.executeQuery();

            if (results != null) {
                while (results.next()) {

                    java.util.Date date = new java.util.Date(results.getDate("date").getTime());
                    MealType mealType = MealType.valueOf(results.getString("mealType"));
                    Map<String, Integer> ingredients = NutriFit.getInstance().getGson().fromJson(results.getString("ingredients"), new TypeToken<Map<String, Integer>>() {}.getType());
                    int totalMealCalories = results.getInt("totalMealCalories");

                    logs.add(new Meal(date, mealType, ingredients, totalMealCalories));
                }

                results.close();
            }

            preparedStatement.close();
        } catch (Exception e) {
            e.printStackTrace();
            return null; // SOme error occured
        }
        return new LogIterator(logs);
    }

    @Override
    public boolean addUserMealLog(int userId, Meal mealLog) {

        try {
            PreparedStatement preparedStatement = this.databaseConnection.prepareStatement(this.INSERT_USER_MEAL);
            preparedStatement.setInt(1, userId);
            preparedStatement.setDate(2, new Date(mealLog.getDate().getTime()));
            preparedStatement.setString(3, mealLog.getMealType().toString());
            preparedStatement.setString(4, NutriFit.getInstance().getGson().toJson(mealLog.getIngredientsAndQuantities()));
            preparedStatement.setInt(5, mealLog.getTotalMealCalories());
            preparedStatement.execute();
            preparedStatement.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
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
