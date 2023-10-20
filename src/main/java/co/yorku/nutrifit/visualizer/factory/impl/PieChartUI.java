package co.yorku.nutrifit.visualizer.factory.impl;

import co.yorku.nutrifit.visualizer.factory.VisualizerUI;
import co.yorku.nutrifit.visualizer.impl.*;

public class PieChartUI implements VisualizerUI {

    @Override
    public void buildAverageUserPlateUI() {
        new AvgUserPlateVisualizer(null).buildPiechart();
    }

    @Override
    public void buildCalorieUI() {
        new CalorieVisualizer(null).buildPiechart();
    }

    @Override
    public void buildCFGPlateUI() {
        new CFGPlateVisualizer().buildPiechart();
    }

    @Override
    public void buildExerciseUI() {
        new ExerciseVisualizer(null).buildPiechart();
    }

    @Override
    public void buildNutrientUI() {
        new NutrientVisualizer(null).buildPiechart();
    }
}
