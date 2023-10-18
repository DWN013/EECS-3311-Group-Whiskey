package co.yorku.nutrifit.profile;

import co.yorku.nutrifit.event.IEventListener;

public interface IProfile extends IEventListener {

    void setupProfile();

    int getId();

    String getName();

    boolean isMale();

    float getHeight();

    int getAge();

    float getWeight();

    boolean isMetric();

}
