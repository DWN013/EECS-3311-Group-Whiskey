package co.yorku.nutrifit.visualizer;

import co.yorku.nutrifit.NutriFit;
import co.yorku.nutrifit.event.IListener;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.Dataset;
import org.jfree.data.general.DefaultPieDataset;

public abstract class IVisualizer implements IListener {

    // DefaultCategoryDataset will be stored in the child class for the implementation
    // so that they can be updated when the user updates the date range in the UI (IListener)

    private Dataset dataset;

    public IVisualizer(Dataset dataset) {
        this.dataset = dataset;
        // Subscribe this class
        NutriFit.getInstance().getEventManager().subscribe(this);
    }

    public Dataset getDataset() {
        return dataset;
    }

    public abstract String getChartName();

    public abstract String getBarGraphCategoryAxisLabel();

    public abstract String getBarGraphValueAxisLabel();

    public abstract DefaultCategoryDataset buildBargraphDataset();

    public abstract DefaultPieDataset<String> buildPiechartDataset();

    public JFreeChart buildBarGraph() {
        return ChartFactory.createBarChart(
                this.getChartName(),
                this.getBarGraphCategoryAxisLabel(),
                this.getBarGraphValueAxisLabel(),
                this.buildBargraphDataset(),
                PlotOrientation.VERTICAL,
                true, true, false);
    }
    public JFreeChart buildPieChart() {
        return ChartFactory.createPieChart(
                this.getChartName(),
                this.buildPiechartDataset()
        );
    }
}
