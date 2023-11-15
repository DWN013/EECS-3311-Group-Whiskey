package co.yorku.nutrifit.visualizer.factory.impl;

import co.yorku.nutrifit.object.VisualizerData;
import co.yorku.nutrifit.visualizer.IVisualizer;
import co.yorku.nutrifit.visualizer.calulcators.AvgUserFoodPlateCalculator;
import co.yorku.nutrifit.visualizer.calulcators.CalorieCalculator;
import co.yorku.nutrifit.visualizer.calulcators.DailyTotalEnergyExpenditureCalculator;
import co.yorku.nutrifit.visualizer.calulcators.NutrientCalculator;
import co.yorku.nutrifit.visualizer.factory.VisualizerUI;
import co.yorku.nutrifit.visualizer.impl.*;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.*;
import java.util.Date;

public class PieChartUI implements VisualizerUI {

    @Override
    public VisualizerData buildAverageUserPlateUI(Date fromDate, Date toDate) {
        IVisualizer iVisualizer = new AvgUserPlateVisualizer(new AvgUserFoodPlateCalculator(), new DefaultPieDataset<>());
        return new VisualizerData(iVisualizer.buildPieChart(fromDate, toDate), iVisualizer);
    }

    @Override
    public VisualizerData buildCalorieUI(Date fromDate, Date toDate) {
        IVisualizer iVisualizer =  new CalorieVisualizer(new CalorieCalculator(), new DefaultPieDataset<>());
        return new VisualizerData(iVisualizer.buildPieChart(fromDate, toDate), iVisualizer);
    }

    @Override
    public VisualizerData buildCFGPlateUI(Date fromDate, Date toDate) {
        IVisualizer iVisualizer =  new CFGPlateVisualizer(new DefaultPieDataset<>());
        return new VisualizerData(iVisualizer.buildPieChart(fromDate, toDate), iVisualizer);

    }
    @Override
    public VisualizerData buildNutrientUI(Date fromDate, Date toDate) {
        IVisualizer iVisualizer =  new NutrientVisualizer(new NutrientCalculator(), new DefaultPieDataset<>());
        return new VisualizerData(iVisualizer.buildPieChart(fromDate, toDate), iVisualizer);
    }

    @Override
    public VisualizerData buildDailyTotalEnergyExpenditure(Date fromDate, Date toDate) {
        IVisualizer iVisualizer =  new ExerciseVisualizer(new DailyTotalEnergyExpenditureCalculator(), new DefaultPieDataset<>());
        return new VisualizerData(iVisualizer.buildPieChart(fromDate, toDate), iVisualizer);
    }
}
