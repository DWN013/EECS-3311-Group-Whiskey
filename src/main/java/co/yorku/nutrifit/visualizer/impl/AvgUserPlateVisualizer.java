package co.yorku.nutrifit.visualizer.impl;

import co.yorku.nutrifit.visualizer.IVisualizer;
import co.yorku.nutrifit.visualizer.calulcators.AvgUserFoodPlateCalculator;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.Dataset;
import org.jfree.data.general.DefaultPieDataset;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.Map;

public class AvgUserPlateVisualizer extends IVisualizer {

    private AvgUserFoodPlateCalculator avgUserFoodPlateCalculator;
    private DecimalFormat decimalFormat;

    public AvgUserPlateVisualizer(AvgUserFoodPlateCalculator avgUserFoodPlateCalculator, Dataset dataset) {
        super(dataset);
        this.avgUserFoodPlateCalculator = avgUserFoodPlateCalculator;
        this.decimalFormat = new DecimalFormat();
    }

    @Override
    public boolean isBargraphShownInPercentage() {
        return true;
    }

    @Override
    public String getChartName() {
        return "Average User Plate";
    }

    @Override
    public String getBarGraphCategoryAxisLabel() {
        return "Food Group";
    }

    @Override
    public String getBarGraphValueAxisLabel() {
        return "Food Group Percentage";
    }

    @Override
    public DefaultCategoryDataset buildBargraphDataset(Date fromDate, Date toDate) {

        Map<String, Double> avgUserFoodPlate = avgUserFoodPlateCalculator.getPlate(fromDate, toDate);

        for(Map.Entry<String, Double> stringIntegerEntry : avgUserFoodPlate.entrySet()){

            int asPercentage = (int) (stringIntegerEntry.getValue() * 100);

            ((DefaultCategoryDataset) getDataset()).setValue(stringIntegerEntry.getValue(), "Food Group Category", stringIntegerEntry.getKey() + " (" + decimalFormat.format(asPercentage) + "%)");
        }

        return ((DefaultCategoryDataset) getDataset());
    }

    @Override
    public DefaultPieDataset<String> buildPiechartDataset(boolean expand, Date fromDate, Date toDate) {

        Map<String, Double> avgUserFoodPlate = avgUserFoodPlateCalculator.getPlate(fromDate, toDate);

        for(Map.Entry<String, Double> stringIntegerEntry : avgUserFoodPlate.entrySet()){

            int asPercentage = (int) (stringIntegerEntry.getValue() * 100);

            ((DefaultPieDataset<String>) getDataset()).setValue(stringIntegerEntry.getKey() + " (" + decimalFormat.format(asPercentage) + "%)", stringIntegerEntry.getValue() * 100);
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