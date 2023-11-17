package co.yorku.nutrifit.visualizer.factory.impl;

import co.yorku.nutrifit.object.VisualizerData;
import co.yorku.nutrifit.visualizer.IVisualizer;
import co.yorku.nutrifit.visualizer.calculators.*;
import co.yorku.nutrifit.visualizer.factory.VisualizerUI;
import co.yorku.nutrifit.visualizer.impl.*;
import org.jfree.data.general.DefaultPieDataset;

import java.util.Date;
/*

    This is the PieChartUI interface.
    It implements the VisualizerUI interface (Factory Method).
    This class builds all the related objects required for
    constructing a PieChart.

 */
public class PieChartUI implements VisualizerUI {

    @Override
    public VisualizerData buildAverageUserPlateUI(Date fromDate, Date toDate) {
        IVisualizer iVisualizer = new AvgUserPlateVisualizer(new AvgUserFoodPlateCalculator(), new DefaultPieDataset<>()); // Construct a new IVisualizer Object with the required args
        // Build and return a VisualizerData object with the JFreeChart object and the IVisualizer object
        return new VisualizerData(iVisualizer.buildPieChart(fromDate, toDate), iVisualizer); // Create a VisualizerData
    }

    @Override
    public VisualizerData buildCalorieUI(Date fromDate, Date toDate) {
        IVisualizer iVisualizer =  new CalorieVisualizer(new CalorieCalculator(), new DefaultPieDataset<>()); // Construct a new IVisualizer Object with the required args
        // Build and return a VisualizerData object with the JFreeChart object and the IVisualizer object
        return new VisualizerData(iVisualizer.buildPieChart(fromDate, toDate), iVisualizer);
    }

    @Override
    public VisualizerData buildCFGPlateUI(Date fromDate, Date toDate) {
        IVisualizer iVisualizer =  new CFGPlateVisualizer(new DefaultPieDataset<>(), new CFGPlateCalculator()); // Construct a new IVisualizer Object with the required args
        // Build and return a VisualizerData object with the JFreeChart object and the IVisualizer object
        return new VisualizerData(iVisualizer.buildPieChart(fromDate, toDate), iVisualizer);

    }
    @Override
    public VisualizerData buildNutrientUI(Date fromDate, Date toDate) {
        IVisualizer iVisualizer =  new NutrientVisualizer(new NutrientCalculator(), new DefaultPieDataset<>()); // Construct a new IVisualizer Object with the required args
        // Build and return a VisualizerData object with the JFreeChart object and the IVisualizer object
        return new VisualizerData(iVisualizer.buildPieChart(fromDate, toDate), iVisualizer);
    }

    @Override
    public VisualizerData buildDailyTotalEnergyExpenditure(Date fromDate, Date toDate) {
        IVisualizer iVisualizer =  new DailyTotalEnergyExpenditureVisualizer(new DailyTotalEnergyExpenditureCalculator(), new DefaultPieDataset<>()); // Construct a new IVisualizer Object with the required args
        // Build and return a VisualizerData object with the JFreeChart object and the IVisualizer object
        return new VisualizerData(iVisualizer.buildPieChart(fromDate, toDate), iVisualizer);
    }
}
