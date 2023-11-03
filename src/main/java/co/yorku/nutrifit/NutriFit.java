package co.yorku.nutrifit;

import co.yorku.nutrifit.database.nutrient.INFDatabase;
import co.yorku.nutrifit.database.nutrient.NFDatabaseAdapter;
import co.yorku.nutrifit.database.nutrient.impl.NFDatabase;
import co.yorku.nutrifit.database.userdata.UserDatabaseAdapter;
import co.yorku.nutrifit.database.userdata.IUserDatabase;
import co.yorku.nutrifit.database.userdata.impl.UserDatabase;
import co.yorku.nutrifit.event.EventManager;
import co.yorku.nutrifit.profile.IProfile;
//import co.yorku.nutrifit.ui.ProfileSelectionUI;
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
    public Gson gson; // TODO: put this somewhere else
    private EventManager eventManager;

    private IProfile loadedProfile;
    private IUserDatabase iUserDatabase;
    private INFDatabase infDatabase;

    public NutriFit() {
        NutriFit.i = this;

        this.gson = new GsonBuilder().create();
        this.eventManager = new EventManager();

        this.iUserDatabase = new UserDatabaseAdapter(new UserDatabase());
        this.iUserDatabase.setupDatabase();

        this.infDatabase = new NFDatabaseAdapter(new NFDatabase());
        this.infDatabase.setupDatabase();

        LogInOrSignUpPage.getInstance().showWindow();
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

    public void setLoadedProfile(IProfile loadedProfile) {
        this.loadedProfile = loadedProfile;
    }


    public static void main(String[] args) {
        new NutriFit();
    }

}
