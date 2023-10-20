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
    public DefaultCategoryDataset getJFreeChartDataObject() {
        // TODO: Add Data here
        return defaultCategoryDataset;
    }

    @Override
    public void onDateRangeUpdate(Date newFromDate, Date newToDate) {
        this.defaultCategoryDataset.clear();
        // TODO: calculate new data and stuff
    }
}
