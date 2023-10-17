package co.yorku.nutrifit.database.userdata;

import co.yorku.nutrifit.database.userdata.objects.ExerciseLog;
import co.yorku.nutrifit.database.userdata.objects.MealLog;
import co.yorku.nutrifit.profile.Profile;

import java.util.Date;
import java.util.List;

public class UserDatabaseAdapter implements UserDatabaseInterface {

    private UserDatabaseInterface userDatabaseInterface;

    public UserDatabaseAdapter(UserDatabaseInterface userDatabaseInterface) {
        this.userDatabaseInterface = userDatabaseInterface;
    }

    @Override
    public void setupDatabase() {
        this.userDatabaseInterface.setupDatabase();
    }

    @Override
    public int setupProfile(Profile profile) {
        return this.userDatabaseInterface.setupProfile(profile);
    }

    @Override
    public List<Profile> getAllProfiles() {
        return this.userDatabaseInterface.getAllProfiles();
    }

    @Override
    public Profile getProfile(int userId) {
        return this.userDatabaseInterface.getProfile(userId);
    }

    @Override
    public List<ExerciseLog> getUserExerciseLogs(int userId, Date fromDate, Date toDate) {
        return this.userDatabaseInterface.getUserExerciseLogs(userId, fromDate, toDate);
    }

    @Override
    public boolean addUserExerciseLog(int userId, ExerciseLog exerciseLog) {
        return this.userDatabaseInterface.addUserExerciseLog(userId, exerciseLog);
    }

    @Override
    public List<MealLog> getUserMealLogs(int userId) {
        return this.userDatabaseInterface.getUserMealLogs(userId);
    }

    @Override
    public boolean addUserMealLog(int userId, MealLog mealLog) {
        return this.userDatabaseInterface.addUserMealLog(userId, mealLog);
    }

    @Override
    public void close() {
        this.userDatabaseInterface.close();
    }
}
