package co.yorku.nutrifit.visualizer.impl;

import co.yorku.nutrifit.visualizer.IVisualizer;
import co.yorku.nutrifit.visualizer.calulcators.ExerciseCalculator;
import org.jfree.data.category.DefaultCategoryDataset;

import java.util.Date;

public class ExerciseVisualizer extends IVisualizer {

    private ExerciseCalculator exerciseCalculator;
    private DefaultCategoryDataset defaultCategoryDataset;

    public ExerciseVisualizer(ExerciseCalculator exerciseCalculator) {
        super();
        this.exerciseCalculator = exerciseCalculator;
        this.defaultCategoryDataset = new DefaultCategoryDataset();
    }

    @Override
    protected DefaultCategoryDataset getDataset() {
        // TODO: Add Data here
        return defaultCategoryDataset;
    }

    @Override
    public void onDateRangeUpdate(Date newFromDate, Date newToDate) {
        this.defaultCategoryDataset.clear();
        // TODO: calculate new data and stuff
    }
}
