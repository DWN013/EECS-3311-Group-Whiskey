package co.yorku.nutrifit.database.userdata;

import co.yorku.nutrifit.database.IDatabase;
import co.yorku.nutrifit.logs.LogIterator;
import co.yorku.nutrifit.logs.impl.Exercise;
import co.yorku.nutrifit.logs.impl.Meal;
import co.yorku.nutrifit.profile.IProfile;

import java.util.Date;
import java.util.List;
/*

    This is the IUserDatabase interface.
    It holds methods related to User functions.
    It extends the IDatabase interface for some generic methods
    like connecting to the database, setting up tables, etc.

 */
public interface IUserDatabase extends IDatabase {

    // This method is used to setup a profile in the database and returns the ID of the profile just created
    int setupProfile(IProfile profile);

    // This method is used to update a profile in the database and returns a true/false based on if the profile was updated successfully
    boolean updateProfile(IProfile profile);

    // This method returns all the profiles in our program
    List<IProfile> getAllProfiles();

    // This method returns a profile based on the userID provided
    IProfile getProfile(int userId);

    // This method returns a LogIterator of all the user exercise logs given
    // A userid and a date range
    LogIterator getUserExerciseLogs(int userId, Date fromDate, Date toDate);

    // This method returns a true/false based on if adding an exercise log to the database
    // for a specific userId was successful
    boolean addUserExerciseLog(int userId, Exercise exerciseLog);

    // This method returns a LogIterator of all the user meal logs given
    // A userid and a date range
    LogIterator getUserMealLogs(int userId, Date fromDate, Date toDate);

    // This method returns a true/false based on if adding a meal log to the database
    // for a specific userId was successful
    boolean addUserMealLog(int userId, Meal mealLog);
}
