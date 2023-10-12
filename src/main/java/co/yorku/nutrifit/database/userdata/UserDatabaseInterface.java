package co.yorku.nutrifit.database.userdata;

import co.yorku.nutrifit.profile.Profile;

import java.util.List;

public interface UserDatabaseInterface {

    void setupDatabase();

    int setupProfile(Profile profile);

    List<Profile> getAllProfiles();

    Profile getProfile(int id);

    void close();
}
