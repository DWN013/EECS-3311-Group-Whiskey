package co.yorku.nutrifit;

import co.yorku.nutrifit.database.nutrient.INFDatabase;
import co.yorku.nutrifit.database.nutrient.NFDatabaseAdapter;
import co.yorku.nutrifit.database.nutrient.impl.NFDatabase;
import co.yorku.nutrifit.database.userdata.UserDatabaseAdapter;
import co.yorku.nutrifit.database.userdata.IUserDatabase;
import co.yorku.nutrifit.database.userdata.impl.UserDatabase;
import co.yorku.nutrifit.event.EventManager;
import co.yorku.nutrifit.profile.IProfile;
import co.yorku.nutrifit.ui.impl.main.LogInOrSignUpPage;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class NutriFit {

    // Singleton Instance
    private static NutriFit i;

    public static NutriFit getInstance() {
        return i;
    }

    // Some stuff
    public Gson gson;
    private EventManager eventManager;
    private IProfile loadedProfile;
    private IUserDatabase iUserDatabase;
    private INFDatabase infDatabase;

    private NutriFit() {
        NutriFit.i = this;

        this.gson = new GsonBuilder().create();
        this.eventManager = new EventManager();

        this.iUserDatabase = new UserDatabaseAdapter(new UserDatabase());
        if (!this.iUserDatabase.setupDatabase()) {
            System.out.println("Could not connect to the user database.");
            System.exit(0);
            return;
        }

        this.infDatabase = new NFDatabaseAdapter(new NFDatabase());
        if (!this.infDatabase.setupDatabase()) {
            System.out.println("Could not connect to the user NFDatabase.");
            System.exit(0);
            return;
        }

        LogInOrSignUpPage.getInstance().showWindow();
    }


    public void close() {
        this.infDatabase.close();
        this.iUserDatabase.close();
        this.setLoadedProfile(null);
        System.exit(0);
    }

    public Gson getGson() {
        return gson;
    }

    public EventManager getEventManager() {
        return eventManager;
    }

    public IUserDatabase getUserDatabase() {
        return iUserDatabase;
    }

    public INFDatabase getNutrientDatabase() {
        return infDatabase;
    }

    public IProfile getLoadedProfile() {
        return loadedProfile;
    }

    public boolean isProfileLoaded() {
        return this.getLoadedProfile() != null;
    }

    public void setLoadedProfile(IProfile loadedProfile) {
        this.loadedProfile = loadedProfile;
    }


    public static void main(String[] args) {
        new NutriFit();
    }

}
