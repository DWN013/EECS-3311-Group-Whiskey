package co.yorku.nutrifit.visualizer.impl;

import co.yorku.nutrifit.visualizer.Visualizer;
import co.yorku.nutrifit.visualizer.calulcators.NutrientCalculator;

public class NutrientVisualizer implements Visualizer {

    private NutrientCalculator nutrientCalculator;

    public NutrientVisualizer(NutrientCalculator nutrientCalculator) {
        this.nutrientCalculator = nutrientCalculator;
    }
}
