package co.yorku.nutrifit.profile;

import co.yorku.nutrifit.event.EventListener;

public interface Profile extends EventListener {

    void setupProfile();

    String getName();

    String getSex();

    String getHeight();

    int getAge();

}
