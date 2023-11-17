package co.yorku.nutrifit.ui.impl.visualizer;

import co.yorku.nutrifit.NutriFit;
import co.yorku.nutrifit.object.VisualizerData;
import co.yorku.nutrifit.ui.NutrifitWindow;
import co.yorku.nutrifit.visualizer.IVisualizer;
import com.toedter.calendar.JDateChooser;
import org.jfree.chart.ChartMouseEvent;
import org.jfree.chart.ChartMouseListener;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.entity.CategoryItemEntity;
import org.jfree.chart.entity.PieSectionEntity;
import org.jfree.chart.entity.PlotEntity;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/*

    This is the NutrifitVisualizer class.
    This class is the visualizer UI for our visualizers

 */
public class NutrifitVisualizer extends NutrifitWindow {

    // chartPanel is used for the JFreeChart
    private ChartPanel chartPanel;
    // iVisualizer is our visualizer class
    private IVisualizer iVisualizer;

    // Below is the date range that we pass to our listener
    private Date fromDate;
    private Date toDate;

    // Expanded boolean to tell class if the chart is expanded (ex. to view more specific data for a specific date)
    private boolean expanded;

    public NutrifitVisualizer(String windowName, NutrifitWindow parent, VisualizerData data, Date fromDate, Date toDate) {
        super(windowName, new GridLayout(1, 5)); // Call parent with params

        this.iVisualizer = data.getVisualizer(); // Set visualizer object in class
        this.fromDate = fromDate; // Set from date
        this.toDate = toDate; // Set to date
        this.expanded = false; // By default, we are not expanded

        // Construct and set the ChartPanel and it's params
        this.chartPanel = new ChartPanel(data.getChart());
        this.chartPanel.setPreferredSize(new Dimension(800, 600));
        this.chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        this.chartPanel.setBackground(Color.white);

        // Add a Chart Mouse Listener (for when a user clicks a specific bar in the bar graph or pie slice in the pie chart)
        this.chartPanel.addChartMouseListener(new ChartMouseListener() {
            @Override
            public void chartMouseClicked(ChartMouseEvent chartMouseEvent) {

                if (chartMouseEvent.getEntity() == null) return; // Check if the entity of the clicked chart is null
                if (!NutrifitVisualizer.this.iVisualizer.isChartExpandable()) return; // Check to see if our chart is expandable
                if (NutrifitVisualizer.this.expanded) return; // Check to see if our chart is currently expanded

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM dd yyyy"); // Create a simple date format for parsing dates
                String dateText = chartMouseEvent.getEntity().getToolTipText(); // Get the text of the bar or pie slice the user clicked

                try {
                    // Parse the dateText into a date, if we thrown an exception (if we cannot parse a date) we will sanitize and pass in the name of the clicked bar or pie slice
                    Date fromDate = simpleDateFormat.parse(dateText);
                    Date toDate = simpleDateFormat.parse(dateText);

                    // Set the fromDate to be the start of the day
                    fromDate.setHours(0);
                    fromDate.setMinutes(0);
                    fromDate.setSeconds(0);

                    // Set the toDate to be the end of the day
                    toDate.setHours(23);
                    toDate.setMinutes(59);
                    toDate.setSeconds(59);

                    // Call the Event and notify it's subscribers (Observer Pattern)
                    NutriFit.getInstance().getEventManager().notify(iVisualizer.getChartName(), fromDate + ":" + toDate, fromDate, toDate);
                    NutrifitVisualizer.this.expanded = true; // Set the chart to expanded
                } catch (Exception e) {

                    String expandData;

                    if (chartMouseEvent.getEntity() instanceof PieSectionEntity) { // If the user clicked a Pie Slice (In a Pie Chart)
                        expandData = ((PieSectionEntity) chartMouseEvent.getEntity()).getSectionKey().toString().split("\\(")[0].trim(); // Get and Cleanup the name of the pie slice the user clicked
                    } else if (chartMouseEvent.getEntity() instanceof CategoryItemEntity) { // If the user clicked a CategoryItemEntity (or Bar in a Bar Graph)
                        expandData = ((CategoryItemEntity) chartMouseEvent.getEntity()).getColumnKey().toString().split("\\(")[0].trim(); // Get and Cleanup the name of the bar the user clicked
                    } else {
                        return; // do nothing
                    }

                    // Call the Event and notify it's subscribers (Observer Pattern)
                    NutriFit.getInstance().getEventManager().notify(iVisualizer.getChartName(), expandData, NutrifitVisualizer.this.fromDate, NutrifitVisualizer.this.toDate);
                    NutrifitVisualizer.this.expanded = true; // Set the chart to expanded
                }

            }

            @Override
            public void chartMouseMoved(ChartMouseEvent chartMouseEvent) {
            }
        });

        this.addFromToButtons(this.fromDate, this.toDate); // Add the from and to buttons by default

        // Add the back button
        // Also add a listener to unsubscribe the Visualizer that is listening to the class when we exit this specific visulization
        this.addBackButton(parent, event -> NutriFit.getInstance().getEventManager().unsubscribe(this.iVisualizer));

        this.setSize(900, 600); // Set the width
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Set the default close operation

        this.build(); // Build the chart
    }

    // By Default we will show the last 7 days
    public void addFromToButtons(Date defaultFromDate, Date defaultToDate) {

        addLabel("Date From To:");
        JDateChooser fromDate = new JDateChooser();
        fromDate.setDateFormatString("yyyy-MM-dd");
        fromDate.setDate(defaultFromDate);
        this.addComponent(fromDate);

        JDateChooser toDate = new JDateChooser();
        toDate.setDateFormatString("yyyy-MM-dd");
        toDate.setDate(defaultToDate);
        this.addComponent(toDate);
        addButton("Update Date Range", event -> {
            NutrifitVisualizer.this.fromDate = fromDate.getDate();
            NutrifitVisualizer.this.toDate = toDate.getDate();
            NutrifitVisualizer.this.expanded = false;
            NutriFit.getInstance().getEventManager().notify(iVisualizer.getChartName(), null, NutrifitVisualizer.this.fromDate, NutrifitVisualizer.this.toDate);
        });
    }


    // Override the build method because we want to format it different
    @Override
    public void build() {
        JPanel container = new JPanel(); // Create a JPanel

        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS)); // Set the Layout
        container.add(this.chartPanel); // Add the chart (or the Bar/Pie chart)
        container.add(getMainJPanel()); // Add the Main panel (which contains our buttons)

        // Add the JPanel to the content pane and packit
        this.getContentPane().add(container);
        this.pack();
    }
}
