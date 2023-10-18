package co.yorku.nutrifit.visualizer.impl;

import co.yorku.nutrifit.visualizer.IVisualizer;
import co.yorku.nutrifit.visualizer.calulcators.ExerciseCalculator;

public class ExerciseVisualizer implements IVisualizer {

    private ExerciseCalculator exerciseCalculator;

    public ExerciseVisualizer(ExerciseCalculator exerciseCalculator) {
        this.exerciseCalculator = exerciseCalculator;
    }
}
