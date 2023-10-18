package co.yorku.nutrifit.profile;

public interface IProfile {

    void setupProfile();

    int getId();

    String getName();

    boolean isMale();

    float getHeight();

    int getAge();

    float getWeight();

    boolean isMetric();

}
