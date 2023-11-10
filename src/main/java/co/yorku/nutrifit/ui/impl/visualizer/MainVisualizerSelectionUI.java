package co.yorku.nutrifit.ui.impl.visualizer;

import co.yorku.nutrifit.ui.NutrifitWindow;
import co.yorku.nutrifit.ui.impl.main.NutriFitMainUI;
import co.yorku.nutrifit.visualizer.factory.VisualizerUI;
import co.yorku.nutrifit.visualizer.factory.impl.BargraphUI;
import co.yorku.nutrifit.visualizer.factory.impl.PieChartUI;

import javax.swing.*;
import java.awt.*;
import java.util.Date;
import java.util.concurrent.TimeUnit;

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

        Date defaultFromDate = new Date(System.currentTimeMillis() - TimeUnit.DAYS.toMillis(14));
        Date defaultToDate = new Date(System.currentTimeMillis());

        defaultFromDate.setHours(0);
        defaultFromDate.setMinutes(0);
        defaultFromDate.setSeconds(0);

        defaultToDate.setHours(23);
        defaultToDate.setMinutes(59);
        defaultToDate.setSeconds(59);

        addButton("Average Food Plate Visualizer", event -> {
            this.hideWindow();
            new NutrifitVisualizer("Average User Food Plate Visualizer", this, this.visualizerUI.buildAverageUserPlateUI(defaultFromDate, defaultToDate), defaultFromDate, defaultToDate).showWindow();
        });

        addButton("Calorie Visualizer", event -> {
            this.hideWindow();
            new NutrifitVisualizer("Calorie Visualizer", this, this.visualizerUI.buildCalorieUI(defaultFromDate, defaultToDate), defaultFromDate, defaultToDate).showWindow();

        });

        addButton("CFG Plate Visualizer", event -> {
            this.hideWindow();
            new NutrifitVisualizer("Canada Food Guide Plate Visualizer", this, this.visualizerUI.buildCFGPlateUI(defaultFromDate, defaultToDate), defaultFromDate, defaultToDate).showWindow();

        });

        addButton("Exercise Visualizer", event -> {
            this.hideWindow();
            new NutrifitVisualizer("Exercise Visualizer", this, this.visualizerUI.buildDailyTotalEnergyExpenditure(defaultFromDate, defaultToDate), defaultFromDate, defaultToDate).showWindow();

        });

        addButton("Nutrient Visualizer", event -> {
            this.hideWindow();
            new NutrifitVisualizer("Nutrient Visualizer", this, this.visualizerUI.buildNutrientUI(defaultFromDate, defaultToDate), defaultFromDate, defaultToDate).showWindow();
        });

        this.createButtonGroup(barGraphRadioButton, pieChartRadioButton);
        this.addBackButton(NutriFitMainUI.getInstance());
        this.build();
    }


}
