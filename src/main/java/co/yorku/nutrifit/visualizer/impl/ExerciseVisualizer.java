package co.yorku.nutrifit.visualizer.impl;

import co.yorku.nutrifit.visualizer.Visualizer;
import co.yorku.nutrifit.visualizer.calulcators.ExerciseCalculator;

public class ExerciseVisualizer implements Visualizer {

    private ExerciseCalculator exerciseCalculator;

    public ExerciseVisualizer(ExerciseCalculator exerciseCalculator) {
        this.exerciseCalculator = exerciseCalculator;
    }
}
