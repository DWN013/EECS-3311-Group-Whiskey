package co.yorku.nutrifit.visualizer.impl;

import co.yorku.nutrifit.object.daterange.DateRange;
import co.yorku.nutrifit.visualizer.IVisualizer;
import co.yorku.nutrifit.visualizer.calculators.CFGPlateCalculator;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.Dataset;
import org.jfree.data.general.DefaultPieDataset;

import java.util.Date;
import java.util.Map;

/*
 * The CFGPlateVisualizer class is designed to visualize data related to the CFG (Canada Food Guide).
 * Uses JFreeChart to create bar graphs and pie charts to represent food group data.
 *
 * The class includes methods for building bar graph and pie chart datasets, as well as
 * handling date range updates. It offers customization options for chart labels and expandability.
 */

public class CFGPlateVisualizer extends IVisualizer {

    // Instance variable for CFGPlateCalculator
    private CFGPlateCalculator cfgPlateCalculator;

    public CFGPlateVisualizer(Dataset dataset, CFGPlateCalculator cfgPlateCalculator) {
        super(dataset);
        this.cfgPlateCalculator = cfgPlateCalculator;

    }

    @Override
    public String getChartName() {
        return "CFG Plate";
    }

    @Override
    // Get the label for the x axis on the bar graph
    public String getBarGraphCategoryAxisLabel() {
        return "Food Categories";
    }

    @Override
    // Get the label for the y axis on the bar graph
    public String getBarGraphValueAxisLabel() {
        return "Percentage";
    }

    @Override
    // Checks if the bar graph should show percentages
    public boolean isBargraphShownInPercentage() {
        return true;
    }

    @Override
    // Checks if the chart is expandable
    public boolean isChartExpandable() {
        return true;
    }

    @Override
    // Method to build the dataset for bar graph
    public DefaultCategoryDataset buildBargraphDataset(String expandInfo, DateRange dateRange) {
        // Get food group data from calculator
        Map<String, Double> data = cfgPlateCalculator.getFoodGroupData(expandInfo);
        // Populate bar graph dataset
        for (Map.Entry<String, Double> stringIntegerEntry : data.entrySet()) {
            ((DefaultCategoryDataset) getDataset()).setValue(stringIntegerEntry.getValue(), "Food Group", stringIntegerEntry.getKey() + " (" + getDecimalFormat().format(stringIntegerEntry.getValue() * 100.0) + "%)");
        }
        //Returns populated dataset
        return ((DefaultCategoryDataset) getDataset());
    }

    @Override
    // Logic works the same as the "buildBarGraphDataset" method above, however instead this method will create a Pie Chart
    public DefaultPieDataset<String> buildPiechartDataset(String expandInfo, DateRange dateRange) {

        Map<String, Double> data = cfgPlateCalculator.getFoodGroupData(expandInfo);

        for (Map.Entry<String, Double> stringIntegerEntry : data.entrySet()) {
            ((DefaultPieDataset<String>) getDataset()).setValue(stringIntegerEntry.getKey() + " (" + getDecimalFormat().format(stringIntegerEntry.getValue() * 100.0) + "%)", stringIntegerEntry.getValue());
        }

        return ((DefaultPieDataset<String>) getDataset());
    }
}
