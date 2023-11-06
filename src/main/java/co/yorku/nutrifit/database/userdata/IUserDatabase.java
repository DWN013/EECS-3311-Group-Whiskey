package co.yorku.nutrifit.database.userdata;

import co.yorku.nutrifit.logs.LogIterator;
import co.yorku.nutrifit.logs.impl.Exercise;
import co.yorku.nutrifit.logs.impl.Meal;
import co.yorku.nutrifit.profile.IProfile;

import java.util.Date;
import java.util.List;

public interface IUserDatabase {

    boolean setupDatabase();

    int setupProfile(IProfile profile);

    boolean updateProfile(IProfile profile);

    List<IProfile> getAllProfiles();

    IProfile getProfile(int userId);

    LogIterator getUserExerciseLogs(int userId, Date fromDate, Date toDate);

    boolean addUserExerciseLog(int userId, Exercise exerciseLog);

    LogIterator getUserMealLogs(int userId, Date fromDate, Date toDate);

    boolean addUserMealLog(int userId, Meal mealLog);

    void close();
}
