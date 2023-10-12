package co.yorku.nutrifit.database.userdata;

import co.yorku.nutrifit.profile.Profile;

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
    public Profile getProfile(int id) {
        return this.userDatabaseInterface.getProfile(id);
    }

    @Override
    public void close() {
        this.userDatabaseInterface.close();
    }
}
