package co.yorku.nutrifit.visualizer;

import co.yorku.nutrifit.NutriFit;
import co.yorku.nutrifit.event.IListener;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

public abstract class IVisualizer implements IListener {

    // DefaultCategoryDataset will be stored in the child class for the implementation
    // so that they can be updated when the user updates the date range in the UI (IListener)

    public IVisualizer() {
        // Subscribe this class
        NutriFit.getInstance().getEventManager().subscribe(this);
    }

    public abstract DefaultCategoryDataset getJFreeChartDataObject();

    public JFreeChart buildBarGraph() {
        DefaultCategoryDataset data = this.getJFreeChartDataObject(); // create bar graph with this data (Template Method)

        return null;
    }
    public JFreeChart buildPiechart() {
        DefaultCategoryDataset data = this.getJFreeChartDataObject(); // create pie chart with this data (Template Method)

        return null;
    }
}
