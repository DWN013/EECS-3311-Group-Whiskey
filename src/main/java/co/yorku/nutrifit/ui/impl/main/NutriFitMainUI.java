package co.yorku.nutrifit.ui.impl.main;

import co.yorku.nutrifit.NutriFit;
import co.yorku.nutrifit.ui.NutrifitWindow;
import co.yorku.nutrifit.ui.impl.meal.MainMealMenu;
import co.yorku.nutrifit.ui.impl.visualizer.MainVisualizerSelectionUI;
import co.yorku.nutrifit.ui.impl.exercise.MainExerciseMenu;

import java.awt.*;

/*
 * Home Page UI - Page shown after the user logs in or signs up
 * This page allows users to decide what they want to do in the app
 * 	whether that is visualize data, see/log their exercise or meals and the user can edit their profile in settings
 */

public class NutriFitMainUI extends NutrifitWindow {

    private static NutriFitMainUI instance; //hold instance of NutriFitMainUI page

    //Implementation of Singleton Design Pattern - keep instance of the main page once created
    public static NutriFitMainUI getInstance() {
        if (instance == null) {
            instance = new NutriFitMainUI();
        }
        return instance;
    }

    private NutriFitMainUI() {
        super("Home Page", new GridLayout());

        //Button to allow users to see Visualizers menu
        addButton("Visualizers", event -> {
            this.hideWindow();
            MainVisualizerSelectionUI.getInstance().showWindow();
        });
        
      //Button to allow users to see Exercise menu
        addButton("Exercise Menu", event -> {
            this.hideWindow();
            MainExerciseMenu.getInstance().showWindow();
        });
        
      //Button to allow users to see Meal menu
        addButton("Meal Menu", event -> {
            this.hideWindow();
            MainMealMenu.getInstance().showWindow();
        });

      //Button to allow users to see Settings
        addButton("Settings", event -> {
            this.hideWindow();
            SettingsUI.getInstance().showWindow();
        });
        
        // Button for users to log out of their profile and go to main page (log in / sign up page)
        addButton("Logout", event -> {
            NutriFit.getInstance().setLoadedProfile(null);
            this.hideWindow();
            LogInOrSignUpPage.getInstance().showWindow();
        });

       this.build();
    }

}
