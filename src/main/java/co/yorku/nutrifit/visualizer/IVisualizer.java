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
/*
    This is the IVisualizer interface.
    It holds methods related to Visualizer methods
    It extends the IListener interface for listening to events.
    This class uses the "Template" method
 */
public abstract class IVisualizer implements IListener {

    // Variables that are used
    private Dataset dataset;
    private DecimalFormat decimalFormat; // DecimalFormat is used to format decimals into number readable numbers (ex. 0.1 to "10%")

    // Constructor that takes in a abstract dataset
    public IVisualizer(Dataset dataset) {
        this.dataset = dataset;
        this.decimalFormat = new DecimalFormat();
        // Subscribe this class to the listener
        NutriFit.getInstance().getEventManager().subscribe(this);
    }

    // Method to get DataSet
    public Dataset getDataset() {
        return dataset;
    }

    // Method to get Decimal Format
    public DecimalFormat getDecimalFormat() {
        return decimalFormat;
    }

    // Method that can be overwritten by children class (Template Method) to tell this class if bargraph will be shown in percentage
    public boolean isBargraphShownInPercentage() {
        return false;
    }

    // Method that can be overwritten by children class (Template Method) to tell the system if this visualizer can be clicked and expanded to view specific data
    // For what is being visualized
    public boolean isChartExpandable() { return false; }

    // Abstract method implemented by children classes that will return the chart name
    public abstract String getChartName();

    // Abstract method implemented by children classes that will return the bar graph category axis label
    public abstract String getBarGraphCategoryAxisLabel();

    // Abstract method implemented by children classes that will return the bar graph value axis label
    public abstract String getBarGraphValueAxisLabel();

    // Abstract method implemented by children classes that will return a DefaultCategorySet for Bar Graphs
    public abstract DefaultCategoryDataset buildBargraphDataset(String expandInfo, Date fromDate, Date toDate);

    // Abstract method implemented by children classes that will return a DefaultPieDataSet for PieCharts
    public abstract DefaultPieDataset<String> buildPiechartDataset(String expandInfo, Date fromDate, Date toDate);

    // This method constructs the JFreeChart object for a bargraph and uses abstract methods defined in this class (Template Method)
    public JFreeChart buildBarGraph(Date fromDate, Date toDate) {
        JFreeChart jFreeChart = ChartFactory.createBarChart(
                this.getChartName(),
                this.getBarGraphCategoryAxisLabel(),
                this.getBarGraphValueAxisLabel(),
                this.buildBargraphDataset(null, fromDate, toDate),
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

    // This method constructs the JFreeChart object for a piechart and uses abstract methods defined in this class (Template Method)
    public JFreeChart buildPieChart(Date fromDate, Date toDate) {
        return ChartFactory.createPieChart(
                this.getChartName(),
                this.buildPiechartDataset(null, fromDate, toDate)
        );
    }
}
