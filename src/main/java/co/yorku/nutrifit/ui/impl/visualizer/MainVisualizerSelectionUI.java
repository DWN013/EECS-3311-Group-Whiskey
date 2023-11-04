package co.yorku.nutrifit.ui.impl.visualizer;

import co.yorku.nutrifit.ui.NutrifitWindow;
import co.yorku.nutrifit.ui.impl.main.NutriFitMainUI;
import co.yorku.nutrifit.visualizer.factory.VisualizerUI;
import co.yorku.nutrifit.visualizer.factory.impl.BargraphUI;
import co.yorku.nutrifit.visualizer.factory.impl.PieChartUI;

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

    private VisualizerUI visualizerUI;

    public MainVisualizerSelectionUI() {
        super("Visualizers", new GridLayout(5, 2));
        this.visualizerUI = new BargraphUI();

        JRadioButton barGraphRadioButton = addRadioButton("Bar Graph", true, event -> this.visualizerUI = new BargraphUI());
        JRadioButton pieChartRadioButton = addRadioButton("Pie Chart", false, event -> this.visualizerUI = new PieChartUI());


        addButton("Average Food Plate Visualizer", event -> {
            this.hideWindow();
            new NutrifitVisualizer("Average User Food Plate Visualizer", this, this.visualizerUI.buildAverageUserPlateUI()).showWindow();
        });

        addButton("Calorie Visualizer", event -> {
            this.hideWindow();
            new NutrifitVisualizer("Calorie Visualizer", this, this.visualizerUI.buildCalorieUI()).showWindow();

        });

        addButton("CFG Plate Visualizer", event -> {
            this.hideWindow();
            new NutrifitVisualizer("Canada Food Guide Plate Visualizer", this, this.visualizerUI.buildCFGPlateUI()).showWindow();

        });

        addButton("Exercise Visualizer", event -> {
            this.hideWindow();
            new NutrifitVisualizer("Exercise Visualizer", this, this.visualizerUI.buildDailyTotalEnergyExpenditure()).showWindow();

        });

        addButton("Nutrient Visualizer", event -> {
            this.hideWindow();
            new NutrifitVisualizer("Nutrient Visualizer", this, this.visualizerUI.buildNutrientUI()).showWindow();
        });

        this.createButtonGroup(barGraphRadioButton, pieChartRadioButton);
        this.addBackButton(NutriFitMainUI.getInstance());
        this.build();
    }


}
