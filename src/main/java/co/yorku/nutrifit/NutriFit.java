package co.yorku.nutrifit;

import co.yorku.nutrifit.database.nutrient.INFDatabase;
import co.yorku.nutrifit.database.nutrient.NFDatabaseAdapter;
import co.yorku.nutrifit.database.nutrient.impl.NFDatabase;
import co.yorku.nutrifit.database.userdata.IUserDatabase;
import co.yorku.nutrifit.database.userdata.UserDatabaseAdapter;
import co.yorku.nutrifit.database.userdata.impl.UserDatabase;
import co.yorku.nutrifit.event.EventManager;
import co.yorku.nutrifit.meal.IMeal;
import co.yorku.nutrifit.meal.impl.MealHandler;
import co.yorku.nutrifit.profile.IProfile;
import co.yorku.nutrifit.ui.impl.main.LogInOrSignUpPage;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class NutriFit {
    // Singleton Instance of NutriFit
    private static NutriFit instance;

    public static NutriFit getInstance() {
        return instance;
    }

    public Gson gson;

    private EventManager eventManager;

    private IMeal iMeal;

    private IProfile loadedProfile;

    private IUserDatabase iUserDatabase;

    private INFDatabase infDatabase;

    private NutriFit() {
        NutriFit.instance = this;

        this.gson = new GsonBuilder().create();
        this.eventManager = new EventManager();
        this.iMeal = new MealHandler();

        this.iUserDatabase = new UserDatabaseAdapter(new UserDatabase());
        if (!this.iUserDatabase.connect() || !this.iUserDatabase.setupDatabase()) {
            System.out.println("Could not connect to the user database.");
            System.exit(0);
            return;
        }

        this.infDatabase = new NFDatabaseAdapter(new NFDatabase());
        if (!this.infDatabase.connect() || !this.infDatabase.setupDatabase()) {
            System.out.println("Could not connect to the user NFDatabase.");
            System.exit(0);
            return;
        }
        //Calls login/signup page
        LogInOrSignUpPage.getInstance().showWindow();
    }

    //Actions when NutriFit is closed
    public void close() {
        this.setLoadedProfile(null);
        //Closes connection to database
        if (infDatabase != null) {
            this.infDatabase.closeConnection();
        }
        if (iUserDatabase != null) {
            this.iUserDatabase.closeConnection();
        }
        //Exit status 0
        System.exit(0);
    }

    //
    public Gson getGson() {
        return gson;
    }

    public EventManager getEventManager() {
        return eventManager;
    }

    public IMeal getMeal() {
        return iMeal;
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
