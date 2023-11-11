package co.yorku.nutrifit.visualizer.impl;

import co.yorku.nutrifit.visualizer.IVisualizer;
import co.yorku.nutrifit.visualizer.calulcators.AvgUserFoodPlateCalculator;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.Dataset;
import org.jfree.data.general.DefaultPieDataset;

import java.util.Date;
import java.util.Map;

public class AvgUserPlateVisualizer extends IVisualizer {

    private AvgUserFoodPlateCalculator avgUserFoodPlateCalculator;

    public AvgUserPlateVisualizer(AvgUserFoodPlateCalculator avgUserFoodPlateCalculator, Dataset dataset) {
        super(dataset);
        this.avgUserFoodPlateCalculator = avgUserFoodPlateCalculator;
    }

    @Override
    public String getChartName() {
        return "Average User Plate";
    }

    @Override
    public String getBarGraphCategoryAxisLabel() {
        return "Date";
    }

    @Override
    public String getBarGraphValueAxisLabel() {
        return "Food Group";
    }

    @Override
    public DefaultCategoryDataset buildBargraphDataset(Date fromDate, Date toDate) {
        // TODO: Add Data here
        ((DefaultCategoryDataset) getDataset()).setValue(100, "Calories Burned", "Today");
        ((DefaultCategoryDataset) getDataset()).setValue(200, "Calories Burned", "Yesterday");
        ((DefaultCategoryDataset) getDataset()).setValue(999, "Calories Burned", "Day Before Yesterday");

        return ((DefaultCategoryDataset) getDataset());
    }

    @Override
    public DefaultPieDataset<String> buildPiechartDataset(boolean expand, Date fromDate, Date toDate) {

        Map<String, Integer> avgUserFoodPlate = avgUserFoodPlateCalculator.getPlate(fromDate, toDate);

        ((DefaultPieDataset<String>) getDataset()).setValue("Today", 20.0);
        ((DefaultPieDataset<String>) getDataset()).setValue("Yesterday", 80.0);

        return ((DefaultPieDataset<String>) getDataset());
    }

    @Override
    public void onDateRangeUpdate(String type, Date newFromDate, Date newToDate) {

        if (!type.equals(this.getChartName())) return;

        if (getDataset() instanceof DefaultCategoryDataset) {
            ((DefaultCategoryDataset) getDataset()).clear();
            this.buildBargraphDataset(newFromDate, newToDate);
        } else if (getDataset() instanceof DefaultPieDataset) {
            ((DefaultPieDataset<String>) getDataset()).clear();
            this.buildPiechartDataset(false, newFromDate, newToDate);
        }

    }
}