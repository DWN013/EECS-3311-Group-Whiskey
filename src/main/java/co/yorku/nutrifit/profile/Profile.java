package co.yorku.nutrifit.profile;

import co.yorku.nutrifit.event.EventListener;

public interface Profile extends EventListener {

    void setupProfile();

    int getId();

    String getName();

    String getSex();

    float getHeight();

    int getAge();

    float getWeight();

    boolean isMetric();

}
