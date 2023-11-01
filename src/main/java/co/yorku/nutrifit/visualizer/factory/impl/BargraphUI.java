package co.yorku.nutrifit.visualizer.factory.impl;

import co.yorku.nutrifit.visualizer.IVisualizer;
import co.yorku.nutrifit.visualizer.calulcators.AvgUserFoodPlateCalculator;
import co.yorku.nutrifit.visualizer.calulcators.CalorieCalculator;
import co.yorku.nutrifit.visualizer.calulcators.DailyTotalEnergyExpenditureCalculator;
import co.yorku.nutrifit.visualizer.calulcators.NutrientCalculator;
import co.yorku.nutrifit.visualizer.factory.VisualizerUI;
import co.yorku.nutrifit.visualizer.impl.*;
import org.jfree.chart.JFreeChart;

public class BargraphUI implements VisualizerUI {

    @Override
    public IVisualizer buildAverageUserPlateUI() {
        return new AvgUserPlateVisualizer(new AvgUserFoodPlateCalculator());
    }

    @Override
    public IVisualizer buildCalorieUI() {
        return new CalorieVisualizer(new CalorieCalculator());
    }

    @Override
    public IVisualizer buildCFGPlateUI() {
        return new CFGPlateVisualizer();
    }
    @Override
    public IVisualizer buildNutrientUI() {
        return new NutrientVisualizer(new NutrientCalculator());
    }

    @Override
    public IVisualizer buildDailyTotalEnergyExpenditure() {
        return new ExerciseVisualizer(new DailyTotalEnergyExpenditureCalculator());
    }
}
