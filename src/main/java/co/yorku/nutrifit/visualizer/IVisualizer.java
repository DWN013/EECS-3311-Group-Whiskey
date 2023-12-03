package co.yorku.nutrifit.visualizer;

import co.yorku.nutrifit.NutriFit;
import co.yorku.nutrifit.event.IListener;
import co.yorku.nutrifit.object.daterange.DateRange;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryLabelPositions;
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
    public abstract DefaultCategoryDataset buildBargraphDataset(String expandInfo, DateRange dateRange);

    // Abstract method implemented by children classes that will return a DefaultPieDataSet for PieCharts
    public abstract DefaultPieDataset<String> buildPiechartDataset(String expandInfo, DateRange dateRange);

    // This method constructs the JFreeChart object for a bargraph and uses abstract methods defined in this class (Template Method)
    public JFreeChart buildBarGraph(DateRange dateRange) {
        JFreeChart jFreeChart = ChartFactory.createBarChart(
                this.getChartName(),
                this.getBarGraphCategoryAxisLabel(),
                this.getBarGraphValueAxisLabel(),
                this.buildBargraphDataset(null, dateRange),
                PlotOrientation.VERTICAL,
                true, true, false);

        jFreeChart.getCategoryPlot().getDomainAxis().setCategoryLabelPositions(CategoryLabelPositions.DOWN_45);

        if (this.isBargraphShownInPercentage()) {
            CategoryPlot plot = (CategoryPlot) jFreeChart.getPlot();
            NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
            DecimalFormat pctFormat = new DecimalFormat("####%");
            rangeAxis.setNumberFormatOverride(pctFormat);
        }

        return jFreeChart;
    }

    // This method constructs the JFreeChart object for a piechart and uses abstract methods defined in this class (Template Method)
    public JFreeChart buildPieChart(DateRange dateRange) {
        return ChartFactory.createPieChart(
                this.getChartName(),
                this.buildPiechartDataset(null, dateRange)
        );
    }


    //If the user has clicked on the "Update Date Range" button in the UI, will take new date values and create the graphs again
    @Override
    public void onDateRangeUpdate(String type, String expandData, DateRange dateRange) {

        //If the user has multiple visualizers open, and they want to update one visualizer, this will ensure that the program is not going to update the wrong visualizer
        if (!type.equals(this.getChartName())) return;

        //If current dataset type for this visualizer is a bar graph
        if (getDataset() instanceof DefaultCategoryDataset) {
            ((DefaultCategoryDataset) getDataset()).clear();
            this.buildBargraphDataset(expandData, dateRange); //build new bar graph data
        }
        //Otherwise, this will update the pie chart
        else if (getDataset() instanceof DefaultPieDataset) {
            ((DefaultPieDataset<String>) getDataset()).clear();
            this.buildPiechartDataset(expandData, dateRange); //build new pie chart data
        }
    }
}
