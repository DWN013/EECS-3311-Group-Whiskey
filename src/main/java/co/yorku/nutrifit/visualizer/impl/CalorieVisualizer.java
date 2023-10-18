package co.yorku.nutrifit.visualizer.impl;

import co.yorku.nutrifit.visualizer.IVisualizer;
import co.yorku.nutrifit.visualizer.calulcators.CalorieCalculator;

public class CalorieVisualizer implements IVisualizer {

    private CalorieCalculator calorieCalculator;

    public CalorieVisualizer(CalorieCalculator calorieCalculator) {
        this.calorieCalculator = calorieCalculator;
    }
}
