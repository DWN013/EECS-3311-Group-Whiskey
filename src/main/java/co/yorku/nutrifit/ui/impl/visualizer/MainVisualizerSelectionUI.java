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

    public MainVisualizerSelectionUI() {
        super("Visualizers", new GridLayout(5, 2));

        JRadioButton barGraphRadioButton = new JRadioButton("Bar Graph");
        JRadioButton pieChartRadioButton = new JRadioButton("Pie Chart");

        barGraphRadioButton.setSelected(true);

        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(barGraphRadioButton);
        buttonGroup.add(pieChartRadioButton);

        addComponent(barGraphRadioButton);
        addComponent(pieChartRadioButton);

        addButton("Average Food Plate Visualizer", event -> {
            this.hideWindow();
            new NutrifitVisualizer("Average User Food Plate Visualizer", this, getVisualizerUI(barGraphRadioButton).buildAverageUserPlateUI()).showWindow();
        });

        addButton("Calorie Visualizer", event -> {
            this.hideWindow();
            new NutrifitVisualizer("Calorie Visualizer", this, getVisualizerUI(barGraphRadioButton).buildAverageUserPlateUI()).showWindow();

        });

        addButton("CFG Plate Visualizer", event -> {
            this.hideWindow();
            new NutrifitVisualizer("Canada Food Guide Plate Visualizer", this, getVisualizerUI(barGraphRadioButton).buildAverageUserPlateUI()).showWindow();

        });

        addButton("Exercise Visualizer", event -> {
            this.hideWindow();
            new NutrifitVisualizer("Exercise Visualizer", this, getVisualizerUI(barGraphRadioButton).buildAverageUserPlateUI()).showWindow();

        });

        addButton("Nutrient Visualizer", event -> {
            this.hideWindow();
            new NutrifitVisualizer("Nutrient Visualizer", this, getVisualizerUI(barGraphRadioButton).buildAverageUserPlateUI()).showWindow();
        });

        this.addBackButton(NutriFitMainUI.getInstance());
        this.build();
    }

    private VisualizerUI getVisualizerUI(JRadioButton barGraphButton) {
        return barGraphButton.isSelected() ? new BargraphUI() : new PieChartUI();
    }

}
