package co.yorku.nutrifit.ui.impl.exercise;

import co.yorku.nutrifit.ui.NutrifitWindow;
import co.yorku.nutrifit.ui.impl.main.NutriFitMainUI;
import co.yorku.nutrifit.ui.impl.visualizer.MainVisualizerSelectionUI;
import co.yorku.nutrifit.ui.impl.exercise.ExerciseDisplayUI;
import co.yorku.nutrifit.ui.impl.exercise.ExerciseInputUI;

import java.awt.*;

public class MainExerciseMenu extends NutrifitWindow {
    private static MainExerciseMenu instance;

    public static MainExerciseMenu getInstance() {
        if (instance == null) {
            instance = new MainExerciseMenu();
        }
        return instance;
    }

    public MainExerciseMenu() {
        super("Exercise Menu", new GridLayout());
        addButton("Log Exercise", event -> {
            this.hideWindow();
            ExerciseInputUI.getInstance().showWindow();
        });
        addButton("Show Exercise Log", event -> {
            this.hideWindow();
            new ExerciseDisplayUI().showToUser();
        });
        addButton("View Weight Loss", event -> {
            this.hideWindow();
            new ViewWeightLoss().getInstance().showWindow();
        });

        this.addBackButton(NutriFitMainUI.getInstance());
        this.build();
    }
}
