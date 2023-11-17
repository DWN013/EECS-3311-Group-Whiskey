package co.yorku.nutrifit.visualizer.impl;

import co.yorku.nutrifit.visualizer.IVisualizer;
import co.yorku.nutrifit.visualizer.calculators.AvgUserFoodPlateCalculator;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.Dataset;
import org.jfree.data.general.DefaultPieDataset;

import java.text.DecimalFormat;
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
        return "Food Group";
    }

    @Override
    public String getBarGraphValueAxisLabel() {
        return "Food Group Percentage";
    }

    @Override
    public boolean isBargraphShownInPercentage() {
        return true;
    }

    @Override
    public boolean isChartExpandable() {
        return true;
    }

    @Override
    public DefaultCategoryDataset buildBargraphDataset(String expandInfo, Date fromDate, Date toDate) {

        Map<String, Double> avgUserFoodPlate = avgUserFoodPlateCalculator.getPlate(expandInfo, fromDate, toDate);

        for(Map.Entry<String, Double> stringIntegerEntry : avgUserFoodPlate.entrySet()){

            int asPercentage = (int) (stringIntegerEntry.getValue() * 100);

            ((DefaultCategoryDataset) getDataset()).setValue(stringIntegerEntry.getValue(), "Food Group Category", stringIntegerEntry.getKey() + " (" + getDecimalFormat().format(asPercentage) + "%)");
        }

        return ((DefaultCategoryDataset) getDataset());
    }

    @Override
    public DefaultPieDataset<String> buildPiechartDataset(String expandInfo, Date fromDate, Date toDate) {

        Map<String, Double> avgUserFoodPlate = avgUserFoodPlateCalculator.getPlate(expandInfo, fromDate, toDate);

        for(Map.Entry<String, Double> stringIntegerEntry : avgUserFoodPlate.entrySet()){

            int asPercentage = (int) (stringIntegerEntry.getValue() * 100);

            ((DefaultPieDataset<String>) getDataset()).setValue(stringIntegerEntry.getKey() + " (" + getDecimalFormat().format(asPercentage) + "%)", stringIntegerEntry.getValue() * 100);
        }

        return ((DefaultPieDataset<String>) getDataset());
    }

    @Override
    public void onDateRangeUpdate(String type, String expandData, Date newFromDate, Date newToDate) {

        if (!type.equals(this.getChartName())) return;

        if (getDataset() instanceof DefaultCategoryDataset) {
            ((DefaultCategoryDataset) getDataset()).clear();
            this.buildBargraphDataset(expandData, newFromDate, newToDate);
        } else if (getDataset() instanceof DefaultPieDataset) {
            ((DefaultPieDataset<String>) getDataset()).clear();
            this.buildPiechartDataset(expandData, newFromDate, newToDate);
        }
    }
}