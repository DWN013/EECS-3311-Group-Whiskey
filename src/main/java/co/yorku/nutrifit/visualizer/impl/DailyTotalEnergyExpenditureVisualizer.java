package co.yorku.nutrifit.visualizer.impl;

import co.yorku.nutrifit.visualizer.IVisualizer;
import co.yorku.nutrifit.visualizer.calulcators.DailyTotalEnergyExpenditureCalculator;
import org.jfree.data.category.DefaultCategoryDataset;

import java.util.Date;

public class DailyTotalEnergyExpenditureVisualizer extends IVisualizer {

    private DailyTotalEnergyExpenditureCalculator dailyTotalEnergyExpenditureCalculator;
    private DefaultCategoryDataset defaultCategoryDataset;

    public DailyTotalEnergyExpenditureVisualizer(DailyTotalEnergyExpenditureCalculator dailyTotalEnergyExpenditureCalculator) {
        super();
        this.dailyTotalEnergyExpenditureCalculator = dailyTotalEnergyExpenditureCalculator;
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
