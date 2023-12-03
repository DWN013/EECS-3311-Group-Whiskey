package co.yorku.nutrifit.visualizer.factory.impl;

import co.yorku.nutrifit.object.VisualizerData;
import co.yorku.nutrifit.object.daterange.DateRange;
import co.yorku.nutrifit.visualizer.IVisualizer;
import co.yorku.nutrifit.visualizer.calculators.*;
import co.yorku.nutrifit.visualizer.factory.VisualizerUI;
import co.yorku.nutrifit.visualizer.impl.*;
import org.jfree.data.category.DefaultCategoryDataset;

import java.util.Date;

/*
    This is the BarGraphUI interface.
    It implements the VisualizerUI interface (Factory Method).
    This class builds all the related objects required for
    constructing a BarGraph.
 */
public class BargraphUI implements VisualizerUI {

    @Override
    public VisualizerData buildAverageUserPlateUI(DateRange dateRange) {
        IVisualizer iVisualizer = new AvgUserPlateVisualizer(new AvgUserFoodPlateCalculator(), new DefaultCategoryDataset()); // Construct a new IVisualizer Object with the required args
        // Build and return a VisualizerData object with the JFreeChart object and the IVisualizer object
        return new VisualizerData(iVisualizer.buildBarGraph(dateRange), iVisualizer);
    }

    @Override
    public VisualizerData buildCalorieUI(DateRange dateRange) {
        IVisualizer iVisualizer =  new CalorieVisualizer(new CalorieCalculator(), new DefaultCategoryDataset()); // Construct a new IVisualizer Object with the required args
        // Build and return a VisualizerData object with the JFreeChart object and the IVisualizer object
        return new VisualizerData(iVisualizer.buildBarGraph(dateRange), iVisualizer);
    }

    @Override
    public VisualizerData buildCFGPlateUI(DateRange dateRange) {
        IVisualizer iVisualizer =  new CFGPlateVisualizer(new DefaultCategoryDataset(), new CFGPlateCalculator()); // Construct a new IVisualizer Object with the required args
        // Build and return a VisualizerData object with the JFreeChart object and the IVisualizer object
        return new VisualizerData(iVisualizer.buildBarGraph(dateRange), iVisualizer);
    }
    @Override
    public VisualizerData buildNutrientUI(DateRange dateRange) {
        IVisualizer iVisualizer =  new NutrientVisualizer(new NutrientCalculator(), new DefaultCategoryDataset()); // Construct a new IVisualizer Object with the required args
        // Build and return a VisualizerData object with the JFreeChart object and the IVisualizer object
        return new VisualizerData(iVisualizer.buildBarGraph(dateRange), iVisualizer);
    }

    @Override
    public VisualizerData buildDailyTotalEnergyExpenditure(DateRange dateRange) {
        IVisualizer iVisualizer =  new DailyTotalEnergyExpenditureVisualizer(new DailyTotalEnergyExpenditureCalculator(), new DefaultCategoryDataset()); // Construct a new IVisualizer Object with the required args
        // Build and return a VisualizerData object with the JFreeChart object and the IVisualizer object
        return new VisualizerData(iVisualizer.buildBarGraph(dateRange), iVisualizer);
    }
}
