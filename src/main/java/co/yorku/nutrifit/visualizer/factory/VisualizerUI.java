package co.yorku.nutrifit.visualizer.factory;

import co.yorku.nutrifit.visualizer.IVisualizer;
import javafx.util.Pair;
import org.jfree.chart.JFreeChart;

import java.util.Date;

public interface VisualizerUI {
    Pair<JFreeChart, IVisualizer> buildAverageUserPlateUI(Date fromDate, Date toDate);
    Pair<JFreeChart, IVisualizer> buildCalorieUI(Date fromDate, Date toDate);
    Pair<JFreeChart, IVisualizer> buildCFGPlateUI(Date fromDate, Date toDate);
    Pair<JFreeChart, IVisualizer> buildNutrientUI(Date fromDate, Date toDate);
    Pair<JFreeChart, IVisualizer> buildDailyTotalEnergyExpenditure(Date fromDate, Date toDate);
}
