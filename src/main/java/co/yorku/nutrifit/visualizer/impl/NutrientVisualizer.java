package co.yorku.nutrifit.visualizer.impl;

import co.yorku.nutrifit.visualizer.IVisualizer;
import co.yorku.nutrifit.visualizer.calulcators.NutrientCalculator;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.Dataset;
import org.jfree.data.general.DefaultPieDataset;

import java.util.Date;

public class NutrientVisualizer extends IVisualizer {

    private NutrientCalculator nutrientCalculator;

    public NutrientVisualizer(NutrientCalculator nutrientCalculator, Dataset dataset) {
        super(dataset);
        this.nutrientCalculator = nutrientCalculator;
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
    public DefaultCategoryDataset buildBargraphDataset() {
        // TODO: Add Data here
        ((DefaultCategoryDataset) getDataset()).setValue(100, "Calories Burned", "Today");
        ((DefaultCategoryDataset) getDataset()).setValue(200, "Calories Burned", "Yesterday");
        ((DefaultCategoryDataset) getDataset()).setValue(999, "Calories Burned", "Day Before Yesterday");

        return ((DefaultCategoryDataset) getDataset());
    }

    @Override
    public DefaultPieDataset<String> buildPiechartDataset() {

        ((DefaultPieDataset<String>) getDataset()).setValue("Today", 20.0);
        ((DefaultPieDataset<String>) getDataset()).setValue("Yesterday", 80.0);

        return ((DefaultPieDataset<String>) getDataset());
    }

    @Override
    public void onDateRangeUpdate(Date newFromDate, Date newToDate) {

        if (getDataset() instanceof DefaultCategoryDataset) {
            ((DefaultCategoryDataset) getDataset()).clear();
            ((DefaultCategoryDataset) getDataset()).setValue(111, "Calories Burned", "Today");
            ((DefaultCategoryDataset) getDataset()).setValue(222, "Calories Burned", "Yesterday");
            ((DefaultCategoryDataset) getDataset()).setValue(333, "Calories Burned", "Day Before Yesterday");
        } else if (getDataset() instanceof DefaultPieDataset) {
            ((DefaultPieDataset) getDataset()).clear();
            ((DefaultPieDataset<String>) getDataset()).setValue("Today", 20.0);
            ((DefaultPieDataset<String>) getDataset()).setValue("Yesterday", 20.0);
            ((DefaultPieDataset<String>) getDataset()).setValue("AAA", 60.0);
        }

    }
}
