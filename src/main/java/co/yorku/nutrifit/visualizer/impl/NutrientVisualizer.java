package co.yorku.nutrifit.visualizer.impl;

import co.yorku.nutrifit.visualizer.IVisualizer;
import co.yorku.nutrifit.visualizer.calulcators.NutrientCalculator;
import org.jfree.chart.entity.PieSectionEntity;
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
    public DefaultCategoryDataset buildBargraphDataset(Date fromDate, Date toDate) {

        LinkedHashMap<String, Map<String, Double>> data = this.nutrientCalculator.getNutrientInfoPerDate(fromDate, toDate);

        for (Map.Entry<String, Map<String, Double>> stringMapEntry : data.entrySet()) {
            for (Map.Entry<String, Double> stringDoubleEntry : stringMapEntry.getValue().entrySet()) {
                ((DefaultCategoryDataset) getDataset()).setValue(stringDoubleEntry.getValue(), stringDoubleEntry.getKey(), stringMapEntry.getKey());
            }
        }

        return ((DefaultCategoryDataset) getDataset());
    }

    @Override
    public DefaultPieDataset<String> buildPiechartDataset(boolean expand, Date fromDate, Date toDate) {

        LinkedHashMap<String, Map<String, Double>> data = this.nutrientCalculator.getNutrientInfoPerDate(fromDate, toDate);

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
    public void onDateRangeUpdate(String type, Date newFromDate, Date newToDate) {

        if (!type.equals(this.getChartName()) && (!type.startsWith(this.getChartName()) && !type.endsWith("_EXPAND"))) return;

        if (getDataset() instanceof DefaultCategoryDataset) {
            ((DefaultCategoryDataset) getDataset()).clear();
            this.buildBargraphDataset(newFromDate, newToDate);
        } else if (getDataset() instanceof DefaultPieDataset) {
            ((DefaultPieDataset<String>) getDataset()).clear();
            this.buildPiechartDataset(type.endsWith("_EXPAND"), newFromDate, newToDate);
        }

    }
}
