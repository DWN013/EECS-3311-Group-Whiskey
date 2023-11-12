package co.yorku.nutrifit.visualizer;

import co.yorku.nutrifit.NutriFit;
import co.yorku.nutrifit.event.IListener;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.Dataset;
import org.jfree.data.general.DefaultPieDataset;

import java.text.DecimalFormat;
import java.util.Date;

public abstract class IVisualizer implements IListener {

    private Dataset dataset;

    public IVisualizer(Dataset dataset) {
        this.dataset = dataset;
        // Subscribe this class
        NutriFit.getInstance().getEventManager().subscribe(this);
    }

    public Dataset getDataset() {
        return dataset;
    }

    public boolean isBargraphShownInPercentage() {
        return false;
    }

    public abstract String getChartName();

    public abstract String getBarGraphCategoryAxisLabel();

    public abstract String getBarGraphValueAxisLabel();

    public abstract DefaultCategoryDataset buildBargraphDataset(Date fromDate, Date toDate);

    public abstract DefaultPieDataset<String> buildPiechartDataset(boolean expand, Date fromDate, Date toDate);

    public JFreeChart buildBarGraph(Date fromDate, Date toDate) {
        JFreeChart jFreeChart = ChartFactory.createBarChart(
                this.getChartName(),
                this.getBarGraphCategoryAxisLabel(),
                this.getBarGraphValueAxisLabel(),
                this.buildBargraphDataset(fromDate, toDate),
                PlotOrientation.VERTICAL,
                true, true, false);

        if (this.isBargraphShownInPercentage()) {
            CategoryPlot plot = (CategoryPlot) jFreeChart.getPlot();
            NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
            DecimalFormat pctFormat = new DecimalFormat("####%");
            rangeAxis.setNumberFormatOverride(pctFormat);
        }

        return jFreeChart;
    }
    public JFreeChart buildPieChart(Date fromDate, Date toDate) {
        return ChartFactory.createPieChart(
                this.getChartName(),
                this.buildPiechartDataset(false, fromDate, toDate)
        );
    }
}
