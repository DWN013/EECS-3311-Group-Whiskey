package co.yorku.nutrifit.ui.impl.main;

import co.yorku.nutrifit.NutriFit;
import co.yorku.nutrifit.profile.IProfile;
import co.yorku.nutrifit.ui.NutrifitWindow;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

/*
 * Log In UI page - allow user to log into existing profile
 */

public class LoginPage extends NutrifitWindow {

    public LoginPage(NutrifitWindow parent) {
        super("Login", new GridLayout(1, 3));

        // Get All existing profiles form the Database and store to dynamic Array list
        List<IProfile> allProfiles = NutriFit.getInstance().getUserDatabase().getAllProfiles();
        
        //Show all profiles to user in a Combo Box
        JComboBox<String> profileList = addComboBox(allProfiles.stream().map(IProfile::getName).collect(Collectors.toList()));
        
        //If user selects profile and hits "Load Selected Profile" button
        addButton("Load Selected Profile", event -> {
            IProfile selectedIndex = allProfiles.get(profileList.getSelectedIndex()); //get user selected profile
            NutriFit.getInstance().setLoadedProfile(selectedIndex); //set profile in main NutriFit class
            hideWindow(); // Hide this window
            NutriFitMainUI.getInstance().showWindow(); // Show the Main UI
        });

        //Add back button for user to go back to main log in / sign up page
        this.addBackButton(parent);

        this.build();
    }

}
