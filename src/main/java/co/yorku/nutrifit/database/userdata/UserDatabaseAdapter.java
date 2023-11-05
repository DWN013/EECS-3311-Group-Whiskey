package co.yorku.nutrifit.database.userdata;

import co.yorku.nutrifit.logs.LogIterator;
import co.yorku.nutrifit.logs.impl.Exercise;
import co.yorku.nutrifit.logs.impl.Meal;
import co.yorku.nutrifit.profile.IProfile;

import java.util.Date;
import java.util.List;

public class UserDatabaseAdapter implements IUserDatabase {

    private IUserDatabase userDatabaseInterface;

    public UserDatabaseAdapter(IUserDatabase userDatabaseInterface) {
        this.userDatabaseInterface = userDatabaseInterface;
    }

    @Override
    public void setupDatabase() {
        this.userDatabaseInterface.setupDatabase();
    }

    @Override
    public int setupProfile(IProfile profile) {
        return this.userDatabaseInterface.setupProfile(profile);
    }

    @Override
    public boolean updateProfile(IProfile profile) {
        return this.userDatabaseInterface.updateProfile(profile);
    }

    @Override
    public List<IProfile> getAllProfiles() {
        return this.userDatabaseInterface.getAllProfiles();
    }

    @Override
    public IProfile getProfile(int userId) {
        return this.userDatabaseInterface.getProfile(userId);
    }

    @Override
    public LogIterator getUserExerciseLogs(int userId, Date fromDate, Date toDate) {
        return this.userDatabaseInterface.getUserExerciseLogs(userId, fromDate, toDate);
    }

    @Override
    public boolean addUserExerciseLog(int userId, Exercise exerciseLog) {
        return this.userDatabaseInterface.addUserExerciseLog(userId, exerciseLog);
    }

    @Override
    public LogIterator getUserMealLogs(int userId, Date fromDate, Date toDate) {
        return this.userDatabaseInterface.getUserMealLogs(userId, fromDate, toDate);
    }

    @Override
    public boolean addUserMealLog(int userId, Meal mealLog) {
        return this.userDatabaseInterface.addUserMealLog(userId, mealLog);
    }

    @Override
    public void close() {
        this.userDatabaseInterface.close();
    }
}
