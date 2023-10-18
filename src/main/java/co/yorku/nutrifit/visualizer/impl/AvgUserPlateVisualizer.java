package co.yorku.nutrifit.visualizer.impl;

import co.yorku.nutrifit.visualizer.IVisualizer;
import co.yorku.nutrifit.visualizer.calulcators.AvgUserFoodPlateCalculator;

public class AvgUserPlateVisualizer implements IVisualizer {

    private AvgUserFoodPlateCalculator avgUserFoodPlateCalculator;

    public AvgUserPlateVisualizer(AvgUserFoodPlateCalculator avgUserFoodPlateCalculator) {
        this.avgUserFoodPlateCalculator = avgUserFoodPlateCalculator;
    }
}
