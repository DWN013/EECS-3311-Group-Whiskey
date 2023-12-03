package co.yorku.nutrifit.visualizer.factory;

import co.yorku.nutrifit.object.VisualizerData;
import co.yorku.nutrifit.object.daterange.DateRange;

import java.util.Date;
/*
    This is the VisualizerUI interface. (Factory Method)
    It holds methods related to building the charts
    and each return a VisualizerData object which contain
    an instance of the JFreeChart object and a IVisualizer Object

 */
public interface VisualizerUI {
    VisualizerData buildAverageUserPlateUI(DateRange dateRange);
    VisualizerData buildCalorieUI(DateRange dateRange);
    VisualizerData buildCFGPlateUI(DateRange dateRange);
    VisualizerData buildNutrientUI(DateRange dateRange);
    VisualizerData buildDailyTotalEnergyExpenditure(DateRange dateRange);
}
