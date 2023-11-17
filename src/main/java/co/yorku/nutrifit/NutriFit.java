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

/*
 * NutriFit is the main class of the program
 * Holds instances of interfaces and the databases
 */

public class NutriFit {
	
    // Singleton Design Pattern Implementation
    private static NutriFit instance;

    public static NutriFit getInstance() {
        return instance;
    }

    //Library from google used to sterilize/desterilize objects to/from JSON
    public Gson gson;

    //private instances of classes/interfaces
    private EventManager eventManager;
    private IMeal iMeal;
    private IProfile loadedProfile;
    private IUserDatabase iUserDatabase;
    private INFDatabase infDatabase;

    private NutriFit() {
        NutriFit.instance = this;

        //initialize classes/interfaces
        this.gson = new GsonBuilder().create();
        this.eventManager = new EventManager();
        this.iMeal = new MealHandler();
        this.iUserDatabase = new UserDatabaseAdapter(new UserDatabase());
        this.infDatabase = new NFDatabaseAdapter(new NFDatabase());

        //To connect and set up the user database
        if (!this.iUserDatabase.connect() || !this.iUserDatabase.setupDatabase()) {
            System.out.println("Could not connect to the user database.");
            System.exit(0);
            return;
        }

        //to connect and setup the nutrient file database 
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
        //Closes connection to user database
        if (this.infDatabase != null) {
            this.infDatabase.closeConnection();
        }
        //closes connection to nutrient file database
        if (this.iUserDatabase != null) {
            this.iUserDatabase.closeConnection();
        }
        //Exit status 0
        System.exit(0);
    }

    /*
     * Getters and Setters
     */
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

    //Main Method of the Program
    public static void main(String[] args) {
        new NutriFit();
    }
}
