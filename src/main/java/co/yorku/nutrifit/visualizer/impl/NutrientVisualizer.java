package co.yorku.nutrifit.visualizer.impl;

import co.yorku.nutrifit.visualizer.IVisualizer;
import co.yorku.nutrifit.visualizer.calculators.NutrientCalculator;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.Dataset;
import org.jfree.data.general.DefaultPieDataset;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

public class NutrientVisualizer extends IVisualizer {

    private NutrientCalculator nutrientCalculator;

    public NutrientVisualizer(NutrientCalculator nutrientCalculator, Dataset dataset) {
        super(dataset);
        this.nutrientCalculator = nutrientCalculator;
    }

    @Override
    public String getChartName() {
        return "Average Nutrients Per Day";
    }

    @Override
    public String getBarGraphCategoryAxisLabel() {
        return "Date";
    }

    @Override
    public String getBarGraphValueAxisLabel() {
        return "Nutrient Consumed";
    }

    @Override
    public boolean isChartExpandable() {
        return true;
    }

    @Override
    public DefaultCategoryDataset buildBargraphDataset(String expandInfo, Date fromDate, Date toDate) {

        LinkedHashMap<String, Map<String, Double>> data = this.nutrientCalculator.getNutrientInfoPerDate(fromDate, toDate);
        boolean expand = expandInfo != null;

        for (Map.Entry<String, Map<String, Double>> stringMapEntry : data.entrySet()) {

            String day = stringMapEntry.getKey();
            double dayTotal = 0.0;

            for (Map.Entry<String, Double> stringDoubleEntry : stringMapEntry.getValue().entrySet()) {

                String nutrientName = stringDoubleEntry.getKey();
                double average = stringDoubleEntry.getValue();

                if (!expand) {
                    dayTotal += average;
                } else {
                    ((DefaultCategoryDataset) getDataset()).setValue(average, nutrientName, stringMapEntry.getKey());
                }
            }
            if (!expand) {
                ((DefaultCategoryDataset) getDataset()).setValue(dayTotal, "Day", day);
            }
        }

        return ((DefaultCategoryDataset) getDataset());
    }

    @Override
    public DefaultPieDataset<String> buildPiechartDataset(String expandInfo, Date fromDate, Date toDate) {

        LinkedHashMap<String, Map<String, Double>> data = this.nutrientCalculator.getNutrientInfoPerDate(fromDate, toDate);
        boolean expand = expandInfo != null;

        for (Map.Entry<String, Map<String, Double>> stringMapEntry : data.entrySet()) {

            String day = stringMapEntry.getKey();
            double dayTotal = 0.0;

            for (Map.Entry<String, Double> stringDoubleEntry : stringMapEntry.getValue().entrySet()) {

                String nutrientName = stringDoubleEntry.getKey();
                double average = stringDoubleEntry.getValue();

                if (!expand) {
                    dayTotal += average;
                } else {
                    ((DefaultPieDataset<String>) getDataset()).setValue(nutrientName, average);
                }
            }
            if (!expand) {
                ((DefaultPieDataset<String>) getDataset()).setValue(day, dayTotal);
            }
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
