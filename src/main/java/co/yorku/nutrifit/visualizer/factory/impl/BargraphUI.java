package co.yorku.nutrifit.visualizer.factory.impl;

import co.yorku.nutrifit.visualizer.factory.VisualizerUI;
import co.yorku.nutrifit.visualizer.impl.*;

public class BargraphUI implements VisualizerUI {

    @Override
    public void buildAverageUserPlateUI() {
        new AvgUserPlateVisualizer(null).buildBarGraph();
    }

    @Override
    public void buildCalorieUI() {
        new CalorieVisualizer(null).buildBarGraph();
    }

    @Override
    public void buildCFGPlateUI() {
        new CFGPlateVisualizer().buildBarGraph();
    }
    @Override
    public void buildNutrientUI() {
        new NutrientVisualizer(null).buildBarGraph();
    }

    @Override
    public void buildDailyTotalEnergyExpenditure() {
        new ExerciseVisualizer(null).buildBarGraph();
    }
}
