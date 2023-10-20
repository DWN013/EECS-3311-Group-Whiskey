package co.yorku.nutrifit.visualizer.impl;

import co.yorku.nutrifit.visualizer.IVisualizer;
import co.yorku.nutrifit.visualizer.calulcators.NutrientCalculator;
import org.jfree.data.category.DefaultCategoryDataset;

import java.util.Date;

public class NutrientVisualizer extends IVisualizer {

    private NutrientCalculator nutrientCalculator;
    private DefaultCategoryDataset defaultCategoryDataset;

    public NutrientVisualizer(NutrientCalculator nutrientCalculator) {
        super();
        this.nutrientCalculator = nutrientCalculator;
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
