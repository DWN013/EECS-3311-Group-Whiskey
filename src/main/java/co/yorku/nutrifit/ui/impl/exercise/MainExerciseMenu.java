package co.yorku.nutrifit.ui.impl.exercise;

import co.yorku.nutrifit.ui.NutrifitWindow;
import co.yorku.nutrifit.ui.impl.main.NutriFitMainUI;

import java.awt.*;

/*
 * Exercise menu UI
 * This is main Exercise page that will allow user to do anything exercise related
 */

public class MainExerciseMenu extends NutrifitWindow {

    private static MainExerciseMenu instance; //store instance of page when created

    //Implementation of Singleton Design Pattern - allows us to keep instance of this UI page
    public static MainExerciseMenu getInstance() {
        if (instance == null) {
            instance = new MainExerciseMenu();
        }
        return instance;
    }

    private MainExerciseMenu() {
        super("Exercise Menu", new GridLayout());
        
        //Log Exercise button - allow user to log exercise
        addButton("Log Exercise", event -> {
            this.hideWindow();
            ExerciseInputUI.getInstance().showWindow();
        });
        
        //Show exercise button - allow user to see thier exercise logs
        addButton("Show Exercise Log", event -> {
            this.hideWindow();
            new ExerciseDisplayUI().showToUser();
        });
        
        //View projected weight loss button - allow user to see how much weight they will lose under current regime 
        addButton("View Projected Weight Loss", event -> {
            this.hideWindow();
            new ViewWeightLoss().showWindow();
        });

        //Create a back button that will go back to main menu
        this.addBackButton(NutriFitMainUI.getInstance());
        this.build();
    }
}
