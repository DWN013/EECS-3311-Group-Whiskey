package co.yorku.nutrifit.visualizer.impl;

import co.yorku.nutrifit.visualizer.IVisualizer;
import co.yorku.nutrifit.visualizer.calculators.CFGPlateCalculator;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.Dataset;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.*;
import java.awt.*;
import java.util.Date;
import java.util.Map;

/*
 * The CFGPlateVisualizer class is designed to visualize data related to the user's plate and
 * the CFG (Canada Food Guide). Uses JFreeChart to create bar graphs and pie charts to represent food group data.
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
    public DefaultCategoryDataset buildBargraphDataset(String expandInfo, Date fromDate, Date toDate) {
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
    //Same as above but for pie chart
    public DefaultPieDataset<String> buildPiechartDataset(String expandInfo, Date fromDate, Date toDate) {

        Map<String, Double> data = cfgPlateCalculator.getFoodGroupData(expandInfo);

        for (Map.Entry<String, Double> stringIntegerEntry : data.entrySet()) {
            ((DefaultPieDataset<String>) getDataset()).setValue(stringIntegerEntry.getKey() + " (" + getDecimalFormat().format(stringIntegerEntry.getValue() * 100.0) + "%)", stringIntegerEntry.getValue());
        }

        return ((DefaultPieDataset<String>) getDataset());
    }

    @Override
    // Method called when the date range is updated by user
    public void onDateRangeUpdate(String type, String expandData, Date newFromDate, Date newToDate) {

        if (!type.equals(this.getChartName())) return;

        if (getDataset() instanceof DefaultCategoryDataset) {
            ((DefaultCategoryDataset) getDataset()).clear();
            this.buildBargraphDataset(expandData, newFromDate, newToDate);
        } else if (getDataset() instanceof DefaultPieDataset) {
            ((DefaultPieDataset<String>) getDataset()).clear();
            this.buildPiechartDataset(expandData, newFromDate, newToDate);
        }
    }
}
