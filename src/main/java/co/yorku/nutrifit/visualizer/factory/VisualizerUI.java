package co.yorku.nutrifit.visualizer.factory;

import co.yorku.nutrifit.object.VisualizerData;

import java.util.Date;

public interface VisualizerUI {
    VisualizerData buildAverageUserPlateUI(Date fromDate, Date toDate);
    VisualizerData buildCalorieUI(Date fromDate, Date toDate);
    VisualizerData buildCFGPlateUI(Date fromDate, Date toDate);
    VisualizerData buildNutrientUI(Date fromDate, Date toDate);
    VisualizerData buildDailyTotalEnergyExpenditure(Date fromDate, Date toDate);
}
