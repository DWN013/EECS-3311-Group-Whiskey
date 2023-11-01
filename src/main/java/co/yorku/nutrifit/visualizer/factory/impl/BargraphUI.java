package co.yorku.nutrifit.visualizer.factory.impl;

import co.yorku.nutrifit.visualizer.factory.VisualizerUI;
import co.yorku.nutrifit.visualizer.impl.*;
import org.jfree.chart.JFreeChart;

public class BargraphUI implements VisualizerUI {

    @Override
    public JFreeChart buildAverageUserPlateUI() {
        return new AvgUserPlateVisualizer(null).buildBarGraph();
    }

    @Override
    public JFreeChart buildCalorieUI() {
        return new CalorieVisualizer(null).buildBarGraph();
    }

    @Override
    public JFreeChart buildCFGPlateUI() {
        return new CFGPlateVisualizer().buildBarGraph();
    }
    @Override
    public JFreeChart buildNutrientUI() {
        return new NutrientVisualizer(null).buildBarGraph();
    }

    @Override
    public JFreeChart buildDailyTotalEnergyExpenditure() {
        return new ExerciseVisualizer(null).buildBarGraph();
    }
}
