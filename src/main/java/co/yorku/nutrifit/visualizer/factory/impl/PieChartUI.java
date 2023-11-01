package co.yorku.nutrifit.visualizer.factory.impl;

import co.yorku.nutrifit.visualizer.factory.VisualizerUI;
import co.yorku.nutrifit.visualizer.impl.*;
import org.jfree.chart.JFreeChart;

public class PieChartUI implements VisualizerUI {

    @Override
    public JFreeChart buildAverageUserPlateUI() {
        return new AvgUserPlateVisualizer(null).buildPiechart();
    }

    @Override
    public JFreeChart buildCalorieUI() {
        return new CalorieVisualizer(null).buildPiechart();
    }

    @Override
    public JFreeChart buildCFGPlateUI() {
        return new CFGPlateVisualizer().buildPiechart();
    }
    @Override
    public JFreeChart buildNutrientUI() {
        return new NutrientVisualizer(null).buildPiechart();
    }

    @Override
    public JFreeChart buildDailyTotalEnergyExpenditure() {
        return new ExerciseVisualizer(null).buildPiechart();
    }
}
