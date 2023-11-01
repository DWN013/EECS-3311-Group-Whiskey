package co.yorku.nutrifit.visualizer.impl;

import co.yorku.nutrifit.visualizer.IVisualizer;
import org.jfree.data.category.DefaultCategoryDataset;

import java.util.Date;

public class CFGPlateVisualizer extends IVisualizer {

    private DefaultCategoryDataset defaultCategoryDataset;

    public CFGPlateVisualizer() {
        super();
        this.defaultCategoryDataset = new DefaultCategoryDataset();
    }

    @Override
    public String getChartName() {
        return "CFG Plate";
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
