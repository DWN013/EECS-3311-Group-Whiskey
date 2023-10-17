package co.yorku.nutrifit.database.userdata;

import co.yorku.nutrifit.database.userdata.objects.ExerciseLog;
import co.yorku.nutrifit.database.userdata.objects.MealLog;
import co.yorku.nutrifit.profile.Profile;

import java.util.Date;
import java.util.List;

public interface UserDatabaseInterface {

    void setupDatabase();

    int setupProfile(Profile profile);

    List<Profile> getAllProfiles();

    Profile getProfile(int userId);

    List<ExerciseLog> getUserExerciseLogs(int userId, Date fromDate, Date toDate);

    boolean addUserExerciseLog(int userId, ExerciseLog exerciseLog);

    List<MealLog> getUserMealLogs(int userId, Date fromDate, Date toDate);

    boolean addUserMealLog(int userId, MealLog mealLog);

    void close();
}
