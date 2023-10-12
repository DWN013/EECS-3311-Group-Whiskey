package co.yorku.nutrifit.visualizer.impl;

import co.yorku.nutrifit.visualizer.Visualizer;
import co.yorku.nutrifit.visualizer.calulcators.CalorieCalculator;

public class CalorieVisualizer implements Visualizer {

    private CalorieCalculator calorieCalculator;

    public CalorieVisualizer(CalorieCalculator calorieCalculator) {
        this.calorieCalculator = calorieCalculator;
    }
}
