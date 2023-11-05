package co.yorku.nutrifit.visualizer.factory;

import co.yorku.nutrifit.visualizer.IVisualizer;
import javafx.util.Pair;
import org.jfree.chart.JFreeChart;

public interface VisualizerUI {
    Pair<JFreeChart, IVisualizer> buildAverageUserPlateUI();
    Pair<JFreeChart, IVisualizer> buildCalorieUI();
    Pair<JFreeChart, IVisualizer> buildCFGPlateUI();
    Pair<JFreeChart, IVisualizer> buildNutrientUI();
    Pair<JFreeChart, IVisualizer> buildDailyTotalEnergyExpenditure();
}
