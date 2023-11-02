package co.yorku.nutrifit.ui.impl.exercise;

import co.yorku.nutrifit.ui.NutrifitWindow;
import co.yorku.nutrifit.ui.impl.main.NutriFitMainUI;
import co.yorku.nutrifit.ui.impl.visualizer.MainVisualizerSelectionUI;

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
        addButton("Visualizers", event -> {
            this.hideWindow();
            MainVisualizerSelectionUI.getInstance().showWindow();
        });

        this.build();
    }
}
