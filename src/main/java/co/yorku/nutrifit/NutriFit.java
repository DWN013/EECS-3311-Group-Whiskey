package co.yorku.nutrifit;

import co.yorku.nutrifit.database.userdata.UserDatabaseAdapter;
import co.yorku.nutrifit.database.userdata.UserDatabaseInterface;
import co.yorku.nutrifit.database.userdata.impl.UserDatabase;
import co.yorku.nutrifit.profile.Profile;
import co.yorku.nutrifit.profile.impl.ProfileHandler;
import co.yorku.nutrifit.ui.NutriFitMainUI;

public class NutriFit {

    public static void main(String[] args) {

        UserDatabaseInterface userDatabaseAdapter = new UserDatabaseAdapter(new UserDatabase());
        userDatabaseAdapter.setupDatabase();

//        Profile profile = new ProfileHandler(userDatabaseAdapter, "karan", "male", "100 feet", 21);
//        profile.setupProfile();
//
//        profile = new ProfileHandler(userDatabaseAdapter, "alex", "male", "200 feet", 1000);
//        profile.setupProfile();

        Profile test = userDatabaseAdapter.getProfile(3);
        System.out.println(test.getName());

        test = userDatabaseAdapter.getProfile(4);
        System.out.println(test.getName());

        NutriFitMainUI nutriFitMainUI = new NutriFitMainUI();

        nutriFitMainUI.setSize(900, 600);
        nutriFitMainUI.pack();
        nutriFitMainUI.setVisible(true);

    }

}
