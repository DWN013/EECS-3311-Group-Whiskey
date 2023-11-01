package co.yorku.nutrifit.ui.impl.visualizer;

import co.yorku.nutrifit.ui.NutrifitWindow;
import co.yorku.nutrifit.ui.impl.main.NutriFitMainUI;
import co.yorku.nutrifit.visualizer.factory.impl.BargraphUI;

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

        // Todo radio buttons for bar or pie chart, for now just barcharts (tmp)

        addButton("Average Food Plate Visualizer", event -> {

            this.hideWindow();
            AverageUserFoodPlateVisualizer averageUserFoodPlateVisualizer = new AverageUserFoodPlateVisualizer(new BargraphUI().buildAverageUserPlateUI(), this);
            averageUserFoodPlateVisualizer.showWindow();
        });

        addButton("Calorie Visualizer", event -> {

        });

        addButton("CFG Plate Visualizer", event -> {

        });

        addButton("Exercise Visualizer", event -> {

        });

        addButton("Nutrient Visualizer", event -> {

        });

        this.addBackButton(NutriFitMainUI.getInstance());
        this.build();
    }

}
