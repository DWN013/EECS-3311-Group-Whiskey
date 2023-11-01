package co.yorku.nutrifit.ui.impl.main;

import co.yorku.nutrifit.NutriFit;
import co.yorku.nutrifit.ui.NutrifitWindow;
import co.yorku.nutrifit.ui.impl.exercise.ExerciseDisplayUI;
import co.yorku.nutrifit.ui.impl.exercise.ExerciseInputUI;
import co.yorku.nutrifit.ui.impl.meal.MealDisplayUI;
import co.yorku.nutrifit.ui.impl.meal.MealInputUI;
import co.yorku.nutrifit.ui.impl.visualizer.MainVisualizerSelectionUI;

import javax.swing.*;
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
        addButton("Log Exercise", event -> {
            this.hideWindow();
            ExerciseInputUI.getInstance().showWindow();
        });
        addButton("Show Exercise Log", event -> {
            this.hideWindow();
            new ExerciseDisplayUI().showToUser();
        });
        addButton("Log Meal", event -> {
            this.hideWindow();
            MealInputUI.getInstance().clearInputtedIngredients();
            MealInputUI.getInstance().showWindow();
        });
        addButton("Show Meal Log", event -> {
            this.hideWindow();
            new MealDisplayUI().showToUser();
        });

        addButton("Logout", event -> {
            NutriFit.getInstance().setLoadedProfile(null);
            this.hideWindow();
            ProfileSelectionUI.getInstance().showWindow();
        });

       this.build();
    }

}