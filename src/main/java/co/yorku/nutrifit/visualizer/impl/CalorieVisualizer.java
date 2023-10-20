package co.yorku.nutrifit.visualizer.impl;

import co.yorku.nutrifit.visualizer.IVisualizer;
import co.yorku.nutrifit.visualizer.calulcators.CalorieCalculator;
import org.jfree.data.category.DefaultCategoryDataset;

import java.util.Date;

public class CalorieVisualizer extends IVisualizer {

    private CalorieCalculator calorieCalculator;
    private DefaultCategoryDataset defaultCategoryDataset;

    public CalorieVisualizer(CalorieCalculator calorieCalculator) {
        super();
        this.calorieCalculator = calorieCalculator;
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
