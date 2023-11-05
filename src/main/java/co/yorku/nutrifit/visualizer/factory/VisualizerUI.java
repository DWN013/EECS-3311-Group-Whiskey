package co.yorku.nutrifit.visualizer.factory;

import co.yorku.nutrifit.visualizer.IVisualizer;
import org.jfree.chart.JFreeChart;

public interface VisualizerUI {
    IVisualizer buildAverageUserPlateUI();
    IVisualizer buildCalorieUI();
    IVisualizer buildCFGPlateUI();
    IVisualizer buildNutrientUI();
    IVisualizer buildDailyTotalEnergyExpenditure();
}
