package co.yorku.nutrifit.database.userdata;

import co.yorku.nutrifit.profile.Profile;

public interface UserDatabaseInterface {

    void setupDatabase();

    void setupProfile(Profile profile);

    Profile getProfile(int id);

    void close();
}
