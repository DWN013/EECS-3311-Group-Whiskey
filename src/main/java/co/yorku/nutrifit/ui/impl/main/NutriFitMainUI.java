package co.yorku.nutrifit.ui.impl.main;

import co.yorku.nutrifit.ui.NutrifitWindow;
import co.yorku.nutrifit.ui.impl.exercise.ExerciseDisplayUI;
import co.yorku.nutrifit.ui.impl.exercise.ExerciseInputUI;
import co.yorku.nutrifit.ui.impl.meal.MealDisplayUI;
import co.yorku.nutrifit.ui.impl.meal.MealInputUI;

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

        });
        addButton("Log Exercise", event -> ExerciseInputUI.getInstance().showWindow());
        addButton("Show Exercise Log", event -> new ExerciseDisplayUI().showToUser());
        addButton("Log Meal", event -> {
            MealInputUI.getInstance().clearInputtedIngredients();
            MealInputUI.getInstance().showWindow();
        });
        addButton("Show Meal Log", event -> new MealDisplayUI().showToUser());

       this.build();
    }

}
