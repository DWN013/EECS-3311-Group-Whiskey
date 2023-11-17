package co.yorku.nutrifit.visualizer.impl;

import co.yorku.nutrifit.visualizer.IVisualizer;
import co.yorku.nutrifit.visualizer.calculators.CalorieCalculator;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.Dataset;
import org.jfree.data.general.DefaultPieDataset;

import java.util.Date;
import java.util.Map;

/*
CalorieVisualizer is designed to visualize calorie consumption data. Utilizes JFreeChart library to create
bar graphs and pie charts. The class implements a method to update the visualizer when the user modifies the date range.
 */

public class CalorieVisualizer extends IVisualizer {
    // Instance variable for calorieCalculator
    private CalorieCalculator calorieCalculator;

    public CalorieVisualizer(CalorieCalculator calorieCalculator, Dataset dataset) {
        super(dataset);
        this.calorieCalculator = calorieCalculator;
    }

    @Override
    public String getChartName() {
        return "Calories Consumed";
    }

    @Override
    // Get the label for the x axis on the bar graph
    public String getBarGraphCategoryAxisLabel() {
        return "Date";
    }

    @Override
    // Get the label for the y axis on the bar graph
    public String getBarGraphValueAxisLabel() {
        return "Calories Consumed";
    }

    @Override
    // Method to build the dataset for bar graph
    public DefaultCategoryDataset buildBargraphDataset(String expandInfo, Date fromDate, Date toDate) {
        // Get food group data from calculator
        Map<String, Integer> calorieData = calorieCalculator.getCaloriesPerDay(fromDate, toDate);
        // Populate bar graph dataset
        for (Map.Entry<String, Integer> stringIntegerEntry : calorieData.entrySet()) {
            ((DefaultCategoryDataset) getDataset()).setValue(stringIntegerEntry.getValue(), "Calories Consumed", stringIntegerEntry.getKey());
        }
        //Returns populated dataset
        return ((DefaultCategoryDataset) getDataset());
    }

    @Override
    //Same as above but for pie chart
    public DefaultPieDataset<String> buildPiechartDataset(String expandInfo, Date fromDate, Date toDate) {

        Map<String, Integer> calorieData = calorieCalculator.getCaloriesPerDay(fromDate, toDate);

        for (Map.Entry<String, Integer> stringIntegerEntry : calorieData.entrySet()) {
            ((DefaultPieDataset<String>) getDataset()).setValue(stringIntegerEntry.getKey(), stringIntegerEntry.getValue());
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
