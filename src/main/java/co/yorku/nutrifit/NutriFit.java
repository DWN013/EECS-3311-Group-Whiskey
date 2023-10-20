package co.yorku.nutrifit;

import co.yorku.nutrifit.database.nutrient.INFDatabase;
import co.yorku.nutrifit.database.nutrient.NFDatabaseAdapter;
import co.yorku.nutrifit.database.nutrient.impl.NFDatabase;
import co.yorku.nutrifit.database.userdata.UserDatabaseAdapter;
import co.yorku.nutrifit.database.userdata.IUserDatabase;
import co.yorku.nutrifit.database.userdata.impl.UserDatabase;
import co.yorku.nutrifit.event.EventManager;
import co.yorku.nutrifit.ui.ProfileSelectionUI;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class NutriFit {

    // Singleton Instance
    private static NutriFit i;
    public static NutriFit getInstance() { return i; }

    // Some stuff
    public static Gson GSON = new GsonBuilder().create(); // TODO: put this somewhere else
    private EventManager eventManager = new EventManager();

    public NutriFit() {
        NutriFit.i = this;

        IUserDatabase userDatabaseAdapter = new UserDatabaseAdapter(new UserDatabase());
        userDatabaseAdapter.setupDatabase();

        INFDatabase infDatabase = new NFDatabaseAdapter(new NFDatabase());
        infDatabase.setupDatabase();

        new ProfileSelectionUI(userDatabaseAdapter, infDatabase);
    }

    public EventManager getEventManager() {
        return eventManager;
    }

    public static void main(String[] args) {
        new NutriFit();
    }

}
