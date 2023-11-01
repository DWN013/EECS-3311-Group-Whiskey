package co.yorku.nutrifit.visualizer.impl;

import co.yorku.nutrifit.NutriFit;
import co.yorku.nutrifit.visualizer.IVisualizer;
import co.yorku.nutrifit.visualizer.calulcators.AvgUserFoodPlateCalculator;
import org.jfree.data.category.DefaultCategoryDataset;

import java.util.Date;

public class AvgUserPlateVisualizer extends IVisualizer {

    private AvgUserFoodPlateCalculator avgUserFoodPlateCalculator;
    private DefaultCategoryDataset defaultCategoryDataset;

    public AvgUserPlateVisualizer(AvgUserFoodPlateCalculator avgUserFoodPlateCalculator) {
        super();
        this.avgUserFoodPlateCalculator = avgUserFoodPlateCalculator;
        this.defaultCategoryDataset = new DefaultCategoryDataset();
    }

    @Override
    public String getChartName() {
        return "Average User Plate";
    }

    @Override
    public String getBarGraphCategoryAxisLabel() {
        return "Something";
    }

    @Override
    public String getBarGraphValueAxisLabel() {
        return "Something Else";
    }

    @Override
    public DefaultCategoryDataset buildDataSet() {
        // TODO: Add Data here
        defaultCategoryDataset.setValue(100, "Calories Burned", "Today");
        defaultCategoryDataset.setValue(200, "Calories Burned", "Yesterday");
        defaultCategoryDataset.setValue(999, "Calories Burned", "Day Before Yesterday");

        return defaultCategoryDataset;
    }

    @Override
    public void onDateRangeUpdate(Date newFromDate, Date newToDate) {
        this.defaultCategoryDataset.clear();
        //this.buildDataSet();

        defaultCategoryDataset.setValue(1234, "Calories Burned", "Test Today");
        defaultCategoryDataset.setValue(99999, "Calories Burned", "Test Yesterday");
        defaultCategoryDataset.setValue(420, "Calories Burned", "Test Day Before Yesterday");
    }
}
