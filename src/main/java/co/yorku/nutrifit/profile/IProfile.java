package co.yorku.nutrifit.profile;

public interface IProfile {

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
