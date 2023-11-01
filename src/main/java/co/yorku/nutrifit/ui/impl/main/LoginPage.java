package co.yorku.nutrifit.ui.impl.main;

import co.yorku.nutrifit.NutriFit;
import co.yorku.nutrifit.profile.IProfile;
import co.yorku.nutrifit.profile.impl.ProfileHandler;
import co.yorku.nutrifit.ui.NutrifitWindow;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class LoginPage extends NutrifitWindow {

    public LoginPage(NutrifitWindow parent) {
        super("Login", new GridLayout(1, 3));

        List<IProfile> allProfiles = NutriFit.getInstance().getUserDatabase().getAllProfiles();
        JComboBox<String> profileList = addComboBox(allProfiles.stream().map(IProfile::getName).collect(Collectors.toList()));
        addButton("Load Selected Profile", event -> {
            IProfile selectedIndex = allProfiles.get(profileList.getSelectedIndex());
            NutriFit.getInstance().setLoadedProfile(selectedIndex);
            hideWindow(); // Hide this window
            NutriFitMainUI.getInstance().showWindow(); // Show the Main UI
        });

        this.addBackButton(parent);

        this.build();
    }

}
