package co.yorku.nutrifit.visualizer.impl;

import co.yorku.nutrifit.visualizer.IVisualizer;
import co.yorku.nutrifit.visualizer.calculators.CFGPlateCalculator;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.Dataset;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.*;
import java.awt.*;
import java.util.Date;
import java.util.Map;

public class CFGPlateVisualizer extends IVisualizer {

    private CFGPlateCalculator cfgPlateCalculator;

    public CFGPlateVisualizer(Dataset dataset, CFGPlateCalculator cfgPlateCalculator) {
        super(dataset);
        this.cfgPlateCalculator = cfgPlateCalculator;

    }

    @Override
    public String getChartName() {
        return "CFG Plate";
    }

    @Override
    public String getBarGraphCategoryAxisLabel() {
        return "Food Categories";
    }

    @Override
    public String getBarGraphValueAxisLabel() {
        return "Percentage";
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

        Map<String, Double> data = cfgPlateCalculator.getFoodGroupData(expandInfo);

        for (Map.Entry<String, Double> stringIntegerEntry : data.entrySet()) {
            ((DefaultCategoryDataset) getDataset()).setValue(stringIntegerEntry.getValue(), "Food Group", stringIntegerEntry.getKey() + " (" + getDecimalFormat().format(stringIntegerEntry.getValue() * 100.0) + "%)");
        }

        return ((DefaultCategoryDataset) getDataset());
    }

    @Override
    public DefaultPieDataset<String> buildPiechartDataset(String expandInfo, Date fromDate, Date toDate) {

        Map<String, Double> data = cfgPlateCalculator.getFoodGroupData(expandInfo);

        for (Map.Entry<String, Double> stringIntegerEntry : data.entrySet()) {
            ((DefaultPieDataset<String>) getDataset()).setValue(stringIntegerEntry.getKey() + " (" + getDecimalFormat().format(stringIntegerEntry.getValue() * 100.0) + "%)", stringIntegerEntry.getValue());
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
