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
import org.jfree.data.general.DefaultPieDataset;

import java.util.Date;

public class PieChartUI implements VisualizerUI {

    @Override
    public Pair<JFreeChart, IVisualizer> buildAverageUserPlateUI(Date fromDate, Date toDate) {
        IVisualizer iVisualizer = new AvgUserPlateVisualizer(new AvgUserFoodPlateCalculator(), new DefaultPieDataset<>());
        return new Pair<>(iVisualizer.buildPieChart(fromDate, toDate), iVisualizer);
    }

    @Override
    public Pair<JFreeChart, IVisualizer> buildCalorieUI(Date fromDate, Date toDate) {
        IVisualizer iVisualizer =  new CalorieVisualizer(new CalorieCalculator(), new DefaultPieDataset<>());
        return new Pair<>(iVisualizer.buildPieChart(fromDate, toDate), iVisualizer);
    }

    @Override
    public Pair<JFreeChart, IVisualizer> buildCFGPlateUI(Date fromDate, Date toDate) {
        IVisualizer iVisualizer =  new CFGPlateVisualizer(new DefaultPieDataset<>());
        return new Pair<>(iVisualizer.buildPieChart(fromDate, toDate), iVisualizer);
    }
    @Override
    public Pair<JFreeChart, IVisualizer> buildNutrientUI(Date fromDate, Date toDate) {
        IVisualizer iVisualizer =  new NutrientVisualizer(new NutrientCalculator(), new DefaultPieDataset<>());
        return new Pair<>(iVisualizer.buildPieChart(fromDate, toDate), iVisualizer);
    }

    @Override
    public Pair<JFreeChart, IVisualizer> buildDailyTotalEnergyExpenditure(Date fromDate, Date toDate) {
        IVisualizer iVisualizer =  new ExerciseVisualizer(new DailyTotalEnergyExpenditureCalculator(), new DefaultPieDataset<>());
        return new Pair<>(iVisualizer.buildPieChart(fromDate, toDate), iVisualizer);
    }
}
