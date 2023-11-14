package co.yorku.nutrifit.visualizer.impl;

import co.yorku.nutrifit.visualizer.IVisualizer;
import co.yorku.nutrifit.visualizer.calulcators.DailyTotalEnergyExpenditureCalculator;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.Dataset;
import org.jfree.data.general.DefaultPieDataset;

import java.util.Date;
import java.util.Map;

public class ExerciseVisualizer extends IVisualizer {

    private DailyTotalEnergyExpenditureCalculator dailyTotalEnergyExpenditureCalculator;

    public ExerciseVisualizer(DailyTotalEnergyExpenditureCalculator dailyTotalEnergyExpenditureCalculator, Dataset dataset) {
        super(dataset);
        this.dailyTotalEnergyExpenditureCalculator = dailyTotalEnergyExpenditureCalculator;
    }

    @Override
    public String getChartName() {
        return "Exercise";
    }

    @Override
    public String getBarGraphCategoryAxisLabel() {
        return "Date";
    }

    @Override
    public String getBarGraphValueAxisLabel() {
        return "Total Daily Energy Expenditure Value";
    }

    @Override
    public DefaultCategoryDataset buildBargraphDataset(Date fromDate, Date toDate) {
        // TODO: Add Data here
    	
    	Map<String, Integer> tdeeValues = dailyTotalEnergyExpenditureCalculator.getTDEE(fromDate, toDate);
    	
    	for (Map.Entry<String, Integer> stringIntegerEntry : tdeeValues.entrySet()) {
            ((DefaultCategoryDataset) getDataset()).setValue(stringIntegerEntry.getValue(), "Total Daily Energy Expenditure", stringIntegerEntry.getKey());
        }

        return ((DefaultCategoryDataset) getDataset());
    }

    @Override
    public DefaultPieDataset<String> buildPiechartDataset(boolean expand, Date fromDate, Date toDate) {

    	Map<String, Integer> tdeeValues = dailyTotalEnergyExpenditureCalculator.getTDEE(fromDate, toDate);

        for (Map.Entry<String, Integer> stringIntegerEntry : tdeeValues.entrySet()) {
            ((DefaultPieDataset<String>) getDataset()).setValue(stringIntegerEntry.getKey(), stringIntegerEntry.getValue());
        }

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
