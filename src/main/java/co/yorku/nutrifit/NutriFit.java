package co.yorku.nutrifit;

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

//        Profile profile = new ProfileHandler(userDatabaseAdapter, "karan", "male", "100 feet", 21);
//        profile.setupProfile();
//
//        profile = new ProfileHandler(userDatabaseAdapter, "alex", "male", "200 feet", 1000);
//        profile.setupProfile();


        new ProfileSelectionUI(userDatabaseAdapter);

    }

}
