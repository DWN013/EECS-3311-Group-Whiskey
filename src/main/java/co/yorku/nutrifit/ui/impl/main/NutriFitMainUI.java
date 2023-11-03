package co.yorku.nutrifit.ui.impl.main;

import co.yorku.nutrifit.NutriFit;
import co.yorku.nutrifit.ui.NutrifitWindow;
import co.yorku.nutrifit.ui.impl.exercise.ExerciseDisplayUI;
import co.yorku.nutrifit.ui.impl.exercise.ExerciseInputUI;
import co.yorku.nutrifit.ui.impl.meal.MainMealMenu;
import co.yorku.nutrifit.ui.impl.meal.MealDisplayUI;
import co.yorku.nutrifit.ui.impl.meal.MealInputUI;
import co.yorku.nutrifit.ui.impl.visualizer.MainVisualizerSelectionUI;
import co.yorku.nutrifit.ui.impl.exercise.MainExerciseMenu;

import java.awt.*;

public class NutriFitMainUI extends NutrifitWindow {

    private static NutriFitMainUI instance;

    public static NutriFitMainUI getInstance() {
        if (instance == null) {
            instance = new NutriFitMainUI();
        }
        return instance;
    }

    private NutriFitMainUI() {
        super("Home Page", new GridLayout());

        addButton("Visualizers", event -> {
            this.hideWindow();
            MainVisualizerSelectionUI.getInstance().showWindow();
        });
        addButton("Exercise Menu", event -> {
            this.hideWindow();
            MainExerciseMenu.getInstance().showWindow();
        });
        addButton("Meal Menu", event -> {
            this.hideWindow();
            MainMealMenu.getInstance().showWindow();
        });


        addButton("Settings", event -> {
            this.hideWindow();
            SettingsUI.getInstance().showWindow();
        });

        addButton("Logout", event -> {
            NutriFit.getInstance().setLoadedProfile(null);
            this.hideWindow();
            LogInOrSignUpPage.getInstance().showWindow();
        });

       this.build();
    }

}
