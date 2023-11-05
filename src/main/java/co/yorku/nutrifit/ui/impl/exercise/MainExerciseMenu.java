package co.yorku.nutrifit.ui.impl.exercise;

import co.yorku.nutrifit.ui.NutrifitWindow;
import co.yorku.nutrifit.ui.impl.main.NutriFitMainUI;

import java.awt.*;

public class MainExerciseMenu extends NutrifitWindow {
    private static MainExerciseMenu instance;

    public static MainExerciseMenu getInstance() {
        if (instance == null) {
            instance = new MainExerciseMenu();
        }
        return instance;
    }

    private MainExerciseMenu() {
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

            //AverageUserFoodPlateVisualizer averageUserFoodPlateVisualizer = new AverageUserFoodPlateVisualizer(new BargraphUI().buildAverageUserPlateUI(), this);
            new ViewWeightLoss().showWindow();
        });

        this.addBackButton(NutriFitMainUI.getInstance());
        this.build();
    }
}
