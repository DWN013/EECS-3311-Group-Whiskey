package co.yorku.nutrifit.database.userdata;

import co.yorku.nutrifit.object.Exercise;
import co.yorku.nutrifit.object.Meal;
import co.yorku.nutrifit.profile.IProfile;

import java.util.Date;
import java.util.List;

public interface IUserDatabase {

    void setupDatabase();

    int setupProfile(IProfile profile);

    List<IProfile> getAllProfiles();

    IProfile getProfile(int userId);

    List<Exercise> getUserExerciseLogs(int userId, Date fromDate, Date toDate);

    boolean addUserExerciseLog(int userId, Exercise exerciseLog);

    List<Meal> getUserMealLogs(int userId, Date fromDate, Date toDate);

    boolean addUserMealLog(int userId, Meal mealLog);

    void close();
}
