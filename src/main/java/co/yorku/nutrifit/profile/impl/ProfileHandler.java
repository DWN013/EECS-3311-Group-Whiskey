package co.yorku.nutrifit.profile.impl;

import co.yorku.nutrifit.database.userdata.UserDatabaseInterface;
import co.yorku.nutrifit.profile.Profile;

import java.util.List;

public class ProfileHandler implements Profile {

    private UserDatabaseInterface userDatabaseInterface;

    private int id;
    private String name;
    private String sex;
    private float height;
    private int age;
    private float weight;
    private boolean isMetric;

    public ProfileHandler(UserDatabaseInterface userDatabaseInterface, int id, String name, String sex, float height, int age, float weight, boolean isMetric) {
        this.userDatabaseInterface = userDatabaseInterface;
        this.id = id;
        this.name = name;
        this.sex = sex;
        this.height = height;
        this.age = age;
        this.weight = weight;
        this.isMetric = isMetric;
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
    public float getHeight() {
        return this.height;
    }

    @Override
    public int getAge() {
        return this.age;
    }

    @Override
    public float getWeight() {
        return this.weight;
    }

    @Override
    public boolean isMetric() {
        return this.isMetric;
    }

    // Always keep at the bottom, it comes from the event handler (has 0 impact on function, my ocd)
    @Override
    public void onEvent(String eventType, String data) {

    }
}
