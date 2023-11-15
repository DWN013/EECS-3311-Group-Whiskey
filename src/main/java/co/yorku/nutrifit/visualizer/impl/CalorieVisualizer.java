package co.yorku.nutrifit.visualizer.impl;

import co.yorku.nutrifit.visualizer.IVisualizer;
import co.yorku.nutrifit.visualizer.calculators.CalorieCalculator;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.Dataset;
import org.jfree.data.general.DefaultPieDataset;

import java.util.Date;
import java.util.Map;

public class CalorieVisualizer extends IVisualizer {

    private CalorieCalculator calorieCalculator;

    public CalorieVisualizer(CalorieCalculator calorieCalculator, Dataset dataset) {
        super(dataset);
        this.calorieCalculator = calorieCalculator;
    }

    @Override
    public String getChartName() {
        return "Calories Consumed";
    }

    @Override
    public String getBarGraphCategoryAxisLabel() {
        return "Date";
    }

    @Override
    public String getBarGraphValueAxisLabel() {
        return "Calories Consumed";
    }

    @Override
    public DefaultCategoryDataset buildBargraphDataset(String expandInfo, Date fromDate, Date toDate) {

        Map<String, Integer> calorieData = calorieCalculator.getCaloriesPerDay(fromDate, toDate);

        for (Map.Entry<String, Integer> stringIntegerEntry : calorieData.entrySet()) {
            ((DefaultCategoryDataset) getDataset()).setValue(stringIntegerEntry.getValue(), "Calories Consumed", stringIntegerEntry.getKey());
        }

        return ((DefaultCategoryDataset) getDataset());
    }

    @Override
    public DefaultPieDataset<String> buildPiechartDataset(String expandInfo, Date fromDate, Date toDate) {

        Map<String, Integer> calorieData = calorieCalculator.getCaloriesPerDay(fromDate, toDate);

        for (Map.Entry<String, Integer> stringIntegerEntry : calorieData.entrySet()) {
            ((DefaultPieDataset<String>) getDataset()).setValue(stringIntegerEntry.getKey(), stringIntegerEntry.getValue());
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
