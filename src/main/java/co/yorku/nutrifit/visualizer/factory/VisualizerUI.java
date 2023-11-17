package co.yorku.nutrifit.visualizer.factory;

import co.yorku.nutrifit.object.VisualizerData;

import java.util.Date;
/*
    This is the VisualizerUI interface. (Factory Method)
    It holds methods related to building the charts
    and each return a VisualizerData object which contain
    an instance of the JFreeChart object and a IVisualizer Object

 */
public interface VisualizerUI {
    VisualizerData buildAverageUserPlateUI(Date fromDate, Date toDate);
    VisualizerData buildCalorieUI(Date fromDate, Date toDate);
    VisualizerData buildCFGPlateUI(Date fromDate, Date toDate);
    VisualizerData buildNutrientUI(Date fromDate, Date toDate);
    VisualizerData buildDailyTotalEnergyExpenditure(Date fromDate, Date toDate);
}
