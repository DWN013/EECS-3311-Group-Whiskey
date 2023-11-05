package co.yorku.nutrifit.visualizer.impl;

import co.yorku.nutrifit.visualizer.IVisualizer;
import co.yorku.nutrifit.visualizer.calulcators.DailyTotalEnergyExpenditureCalculator;
import org.jfree.data.category.DefaultCategoryDataset;

import java.util.Date;

public class ExerciseVisualizer extends IVisualizer {

    private DailyTotalEnergyExpenditureCalculator dailyTotalEnergyExpenditureCalculator;
    private DefaultCategoryDataset defaultCategoryDataset;

    public ExerciseVisualizer(DailyTotalEnergyExpenditureCalculator dailyTotalEnergyExpenditureCalculator) {
        super();
        this.dailyTotalEnergyExpenditureCalculator = dailyTotalEnergyExpenditureCalculator;
        this.defaultCategoryDataset = new DefaultCategoryDataset();
    }

    @Override
    public String getChartName() {
        return "Exercise";
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
