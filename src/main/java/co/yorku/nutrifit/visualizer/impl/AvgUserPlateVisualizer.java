package co.yorku.nutrifit.visualizer.impl;

import co.yorku.nutrifit.visualizer.Visualizer;
import co.yorku.nutrifit.visualizer.calulcators.AvgUserFoodPlateCalculator;

public class AvgUserPlateVisualizer implements Visualizer {

    private AvgUserFoodPlateCalculator avgUserFoodPlateCalculator;

    public AvgUserPlateVisualizer(AvgUserFoodPlateCalculator avgUserFoodPlateCalculator) {
        this.avgUserFoodPlateCalculator = avgUserFoodPlateCalculator;
    }
}
