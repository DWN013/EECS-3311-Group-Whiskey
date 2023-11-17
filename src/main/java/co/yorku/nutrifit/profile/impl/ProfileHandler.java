package co.yorku.nutrifit.profile.impl;

import co.yorku.nutrifit.profile.IProfile;

/*
 * Implementation of the IProfile class. Will allow us to create new profiles and instantiate the getter, setter methods
 */

public class ProfileHandler implements IProfile {

	//Values being stored in a Profile
    private int id;
    private String name;
    private boolean isMale;
    private float height;
    private int age;
    private float weight;
    private boolean isMetric;

    public ProfileHandler(int id, String name, boolean isMale, float height, int age, float weight, boolean isMetric) {
    	//Take inputed values for the profile and set these values for the profile instance
        this.id = id;
        this.name = name;
        this.isMale = isMale;
        this.height = height;
        this.age = age;
        this.weight = weight;
        this.isMetric = isMetric;
    }

    /*
     * Getter and Setter Methods for the Data
     */
    
    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public boolean isMale() {
        return this.isMale;
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

	@Override
	public void setName(String name) {
		this.name = name;		
	}

	@Override
	public void setGender(boolean gender) {
		this.isMale = gender;
	}

	@Override
	public void setHeight(float height) {
		this.height = height;
	}

	@Override
	public void setWeight(float weight) {
		this.weight = weight;
	}

	@Override
	public void setUnit(boolean unit) {
		this.isMetric = unit;
	}

	@Override
	public void setAge(int age) {
		this.age = age;		
	}

}
