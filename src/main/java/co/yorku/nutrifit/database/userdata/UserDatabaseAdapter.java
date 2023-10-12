package co.yorku.nutrifit.database.userdata;

import co.yorku.nutrifit.profile.Profile;

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
    public void setupProfile(Profile profile) {
        this.userDatabaseInterface.setupProfile(profile);
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
