package co.yorku.nutrifit.visualizer.factory.impl;

import co.yorku.nutrifit.visualizer.IVisualizer;
import co.yorku.nutrifit.visualizer.calulcators.AvgUserFoodPlateCalculator;
import co.yorku.nutrifit.visualizer.calulcators.CalorieCalculator;
import co.yorku.nutrifit.visualizer.calulcators.DailyTotalEnergyExpenditureCalculator;
import co.yorku.nutrifit.visualizer.calulcators.NutrientCalculator;
import co.yorku.nutrifit.visualizer.factory.VisualizerUI;
import co.yorku.nutrifit.visualizer.impl.*;
import javafx.util.Pair;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

public class BargraphUI implements VisualizerUI {

    @Override
    public Pair<JFreeChart, IVisualizer> buildAverageUserPlateUI() {
        IVisualizer iVisualizer = new AvgUserPlateVisualizer(new AvgUserFoodPlateCalculator(), new DefaultCategoryDataset());
        return new Pair<>(iVisualizer.buildBarGraph(), iVisualizer);
    }

    @Override
    public Pair<JFreeChart, IVisualizer> buildCalorieUI() {
        IVisualizer iVisualizer =  new CalorieVisualizer(new CalorieCalculator(), new DefaultCategoryDataset());
        return new Pair<>(iVisualizer.buildBarGraph(), iVisualizer);
    }

    @Override
    public Pair<JFreeChart, IVisualizer> buildCFGPlateUI() {
        IVisualizer iVisualizer =  new CFGPlateVisualizer(new DefaultCategoryDataset());
        return new Pair<>(iVisualizer.buildBarGraph(), iVisualizer);
    }
    @Override
    public Pair<JFreeChart, IVisualizer> buildNutrientUI() {
        IVisualizer iVisualizer =  new NutrientVisualizer(new NutrientCalculator(), new DefaultCategoryDataset());
        return new Pair<>(iVisualizer.buildBarGraph(), iVisualizer);
    }

    @Override
    public Pair<JFreeChart, IVisualizer> buildDailyTotalEnergyExpenditure() {
        IVisualizer iVisualizer =  new ExerciseVisualizer(new DailyTotalEnergyExpenditureCalculator(), new DefaultCategoryDataset());
        return new Pair<>(iVisualizer.buildBarGraph(), iVisualizer);
    }
}
