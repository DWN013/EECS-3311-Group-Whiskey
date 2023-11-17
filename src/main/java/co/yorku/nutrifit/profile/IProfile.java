package co.yorku.nutrifit.profile;

/*
 * Interface Class for User Profiles. This will allow us to hold the instance of the loaded user profile 
 */

public interface IProfile {
	
	//IProfile Getter and Setter Methods 
	
    int getId();

    String getName();

    boolean isMale();

    float getHeight();

    int getAge();

    float getWeight();

    boolean isMetric();
    
    void setName(String name);
    
    void setGender(boolean gender);
    
    void setAge (int age);
    
    void setHeight(float height);
    
    void setWeight (float weight);
    
    void setUnit(boolean unit);

}
