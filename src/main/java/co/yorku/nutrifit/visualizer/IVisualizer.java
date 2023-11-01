package co.yorku.nutrifit.visualizer;

import co.yorku.nutrifit.NutriFit;
import co.yorku.nutrifit.event.IListener;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.util.TableOrder;
import org.jfree.data.category.DefaultCategoryDataset;

public abstract class IVisualizer implements IListener {

    // DefaultCategoryDataset will be stored in the child class for the implementation
    // so that they can be updated when the user updates the date range in the UI (IListener)

    public IVisualizer() {
        // Subscribe this class
        NutriFit.getInstance().getEventManager().subscribe(this);
    }

    public abstract String getChartName();

    public abstract String getBarGraphCategoryAxisLabel();

    public abstract String getBarGraphValueAxisLabel();

    public abstract DefaultCategoryDataset buildDataSet();

    public JFreeChart buildBarGraph() {
        return ChartFactory.createBarChart(
                this.getChartName(),
                this.getBarGraphCategoryAxisLabel(),
                this.getBarGraphValueAxisLabel(),
                this.buildDataSet(),
                PlotOrientation.VERTICAL,
                true, true, false);
    }
    public JFreeChart buildPieChart() {
        return ChartFactory.createMultiplePieChart(
                this.getChartName(),
                this.buildDataSet(),
                TableOrder.BY_COLUMN,
                true, true, false);
    }
}
