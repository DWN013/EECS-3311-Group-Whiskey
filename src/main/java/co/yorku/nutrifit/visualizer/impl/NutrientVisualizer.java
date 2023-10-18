package co.yorku.nutrifit.visualizer.impl;

import co.yorku.nutrifit.visualizer.IVisualizer;
import co.yorku.nutrifit.visualizer.calulcators.NutrientCalculator;

public class NutrientVisualizer implements IVisualizer {

    private NutrientCalculator nutrientCalculator;

    public NutrientVisualizer(NutrientCalculator nutrientCalculator) {
        this.nutrientCalculator = nutrientCalculator;
    }
}
