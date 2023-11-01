package co.yorku.nutrifit.ui.impl.visualizer;

import co.yorku.nutrifit.ui.NutrifitWindow;
import co.yorku.nutrifit.ui.impl.exercise.ExerciseDisplayUI;
import co.yorku.nutrifit.ui.impl.exercise.ExerciseInputUI;
import co.yorku.nutrifit.ui.impl.meal.MealDisplayUI;
import co.yorku.nutrifit.ui.impl.meal.MealInputUI;

import javax.swing.*;
import java.awt.*;

public class MainVisualizerSelectionUI extends NutrifitWindow {

    private static MainVisualizerSelectionUI instance;

    public static MainVisualizerSelectionUI getInstance() {
        if (instance == null) {
            instance = new MainVisualizerSelectionUI();
        }
        return instance;
    }

    public MainVisualizerSelectionUI() {
        super("Visualizers", new GridLayout(9, 2));

    }

}
