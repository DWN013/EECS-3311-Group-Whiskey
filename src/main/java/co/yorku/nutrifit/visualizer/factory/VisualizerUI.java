package co.yorku.nutrifit.visualizer.factory;

import org.jfree.chart.JFreeChart;

public interface VisualizerUI {
    JFreeChart buildAverageUserPlateUI();
    JFreeChart buildCalorieUI();
    JFreeChart buildCFGPlateUI();
    JFreeChart buildNutrientUI();
    JFreeChart buildDailyTotalEnergyExpenditure();
}
