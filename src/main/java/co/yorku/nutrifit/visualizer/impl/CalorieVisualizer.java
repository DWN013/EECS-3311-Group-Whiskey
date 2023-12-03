package co.yorku.nutrifit.visualizer.impl;

import co.yorku.nutrifit.object.daterange.DateRange;
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
    public DefaultCategoryDataset buildBargraphDataset(String expandInfo, DateRange dateRange) {
        // Get food group data from calculator
        Map<String, Integer> calorieData = calorieCalculator.getCaloriesPerDay(dateRange);
        // Populate bar graph dataset
        for (Map.Entry<String, Integer> stringIntegerEntry : calorieData.entrySet()) {
            ((DefaultCategoryDataset) getDataset()).setValue(stringIntegerEntry.getValue(), "Calories Consumed", stringIntegerEntry.getKey());
        }
        //Returns populated dataset
        return ((DefaultCategoryDataset) getDataset());
    }

    @Override
    // Logic works the same as the "buildBarGraphDataset" method above, however instead this method will create a Pie Chart
    public DefaultPieDataset<String> buildPiechartDataset(String expandInfo, DateRange dateRange) {

        Map<String, Integer> calorieData = calorieCalculator.getCaloriesPerDay(dateRange);

        for (Map.Entry<String, Integer> stringIntegerEntry : calorieData.entrySet()) {
            ((DefaultPieDataset<String>) getDataset()).setValue(stringIntegerEntry.getKey(), stringIntegerEntry.getValue());
        }

        return ((DefaultPieDataset<String>) getDataset());
    }

}
