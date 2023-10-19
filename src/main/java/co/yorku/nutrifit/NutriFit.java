package co.yorku.nutrifit;

import co.yorku.nutrifit.database.nutrient.INFDatabase;
import co.yorku.nutrifit.database.nutrient.NFDatabaseAdapter;
import co.yorku.nutrifit.database.nutrient.impl.NFDatabase;
import co.yorku.nutrifit.database.userdata.UserDatabaseAdapter;
import co.yorku.nutrifit.database.userdata.IUserDatabase;
import co.yorku.nutrifit.database.userdata.impl.UserDatabase;
import co.yorku.nutrifit.ui.ProfileSelectionUI;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class NutriFit {

    public static Gson GSON = new GsonBuilder().create(); // TODO: put this somewhere else
    public static void main(String[] args) {

        IUserDatabase userDatabaseAdapter = new UserDatabaseAdapter(new UserDatabase());
        userDatabaseAdapter.setupDatabase();

        INFDatabase infDatabase = new NFDatabaseAdapter(new NFDatabase());
        infDatabase.setupDatabase();

        new ProfileSelectionUI(userDatabaseAdapter, infDatabase);

    }

}
