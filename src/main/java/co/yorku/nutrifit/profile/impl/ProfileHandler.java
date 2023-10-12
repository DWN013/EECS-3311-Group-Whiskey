package co.yorku.nutrifit.profile.impl;

import co.yorku.nutrifit.database.userdata.UserDatabaseInterface;
import co.yorku.nutrifit.profile.Profile;

import java.util.List;

public class ProfileHandler implements Profile {

    private UserDatabaseInterface userDatabaseInterface;

    private int id;
    private String name;
    private String sex;
    private String height;
    private int age;

    public ProfileHandler(UserDatabaseInterface userDatabaseInterface, int id, String name, String sex, String height, int age) {
        this.userDatabaseInterface = userDatabaseInterface;
        this.id = id;
        this.name = name;
        this.sex = sex;
        this.height = height;
        this.age = age;
    }

    @Override
    public void setupProfile() {
        this.userDatabaseInterface.setupProfile(this);
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getSex() {
        return this.sex;
    }

    @Override
    public String getHeight() {
        return this.height;
    }

    @Override
    public int getAge() {
        return this.age;
    }

    // Always keep at the bottom, it comes from the event handler (has 0 impact on function, my ocd)
    @Override
    public void onEvent(String eventType, String data) {

    }
}
