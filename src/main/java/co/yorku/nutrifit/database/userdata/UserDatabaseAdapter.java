package co.yorku.nutrifit.database.userdata;

import co.yorku.nutrifit.logs.LogIterator;
import co.yorku.nutrifit.logs.impl.Exercise;
import co.yorku.nutrifit.logs.impl.Meal;
import co.yorku.nutrifit.object.daterange.DateRange;
import co.yorku.nutrifit.profile.IProfile;

import java.util.Date;
import java.util.List;
/*

    This UserDatabaseAdapter wraps an implementation for the IUserDatabase interface
    so that we can pass whatever implementation of IUserDatabase into it.
    This also uses the "Adapter" design pattern

 */
public class UserDatabaseAdapter implements IUserDatabase {

    // Variable that holds the IUserDatabase instance
    private IUserDatabase userDatabaseInterface;

    // Constructor for the UserDatabaseAdapter
    public UserDatabaseAdapter(IUserDatabase userDatabaseInterface) {
        this.userDatabaseInterface = userDatabaseInterface;
    }

    /*

        Below are methods used to interact with the IUserDdatabase
        Specific comments about these methods can be found
        in the respective interface classes

     */

    @Override
    public boolean connect() {
        return this.userDatabaseInterface.connect();
    }

    @Override
    public boolean setupDatabase() {
        return this.userDatabaseInterface.setupDatabase();
    }

    @Override
    public void closeConnection() {
        this.userDatabaseInterface.closeConnection();
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
    public LogIterator getUserExerciseLogs(int userId, DateRange dateRange) {
        return this.userDatabaseInterface.getUserExerciseLogs(userId, dateRange);
    }

    @Override
    public boolean addUserExerciseLog(int userId, Exercise exerciseLog) {
        return this.userDatabaseInterface.addUserExerciseLog(userId, exerciseLog);
    }

    @Override
    public LogIterator getUserMealLogs(int userId, DateRange dateRange) {
        return this.userDatabaseInterface.getUserMealLogs(userId, dateRange);
    }

    @Override
    public boolean addUserMealLog(int userId, Meal mealLog) {
        return this.userDatabaseInterface.addUserMealLog(userId, mealLog);
    }
}
