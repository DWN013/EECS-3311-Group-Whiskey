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
