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
    public String getChartName() {
        return "Nutrients";
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
        this.buildDataSet();
    }
}
