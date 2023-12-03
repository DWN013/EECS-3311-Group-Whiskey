package co.yorku.nutrifit.ui.impl.visualizer;

import co.yorku.nutrifit.NutriFit;
import co.yorku.nutrifit.object.VisualizerData;
import co.yorku.nutrifit.object.daterange.DateRange;
import co.yorku.nutrifit.object.daterange.FullDayDateRange;
import co.yorku.nutrifit.ui.NutrifitWindow;
import co.yorku.nutrifit.visualizer.IVisualizer;
import com.toedter.calendar.JDateChooser;
import org.jfree.chart.ChartMouseEvent;
import org.jfree.chart.ChartMouseListener;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.entity.CategoryItemEntity;
import org.jfree.chart.entity.ChartEntity;
import org.jfree.chart.entity.PieSectionEntity;
import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/*

    This is the NutrifitVisualizer class.
    This class is the visualizer UI for our visualizers

 */
public class NutrifitVisualizer extends NutrifitWindow implements ChartMouseListener {

    // chartPanel is used for the JFreeChart
    private ChartPanel chartPanel;
    // iVisualizer is our visualizer class
    private IVisualizer iVisualizer;

    // Below is the date range that we pass to our listener
    private DateRange dateRange;

    // Expanded boolean to tell class if the chart is expanded (ex. to view more specific data for a specific date)
    private boolean expanded;

    public NutrifitVisualizer(String windowName, NutrifitWindow parent, VisualizerData data, DateRange dateRange) {
        super(windowName, new GridLayout(1, 5)); // Call parent with parameters 

        this.iVisualizer = data.getVisualizer(); // Set visualizer object in class
        this.dateRange = dateRange;
        this.expanded = false; // By default, we are not expanded

        // Construct and set the ChartPanel and it's parameters 
        this.chartPanel = new ChartPanel(data.getChart());
        this.chartPanel.addChartMouseListener(this);
        this.setupChartPanel();

        // Add a Chart Mouse Listener (for when a user clicks a specific bar in the bar graph or pie slice in the pie chart)
        this.addFromToButtons(); // Add the from and to buttons by default

        // Add the back button
        // Also add a listener to unsubscribe the Visualizer that is listening to the class when we exit this specific visulization
        this.addBackButton(parent, event -> NutriFit.getInstance().getEventManager().unsubscribe(this.iVisualizer));

        this.setSize(900, 600); // Set the width
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Set the default close operation

        this.build(); // Build the chart
    }

    private void setupChartPanel() {
        this.chartPanel.setPreferredSize(new Dimension(800, 600));
        this.chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        this.chartPanel.setBackground(Color.white);
    }

    // By Default we will show the last 7 days
    public void addFromToButtons() {

        addLabel("Date From To:"); // Add a "Date From To" label
        JDateChooser fromDate = new JDateChooser(); // Create JDateChooser object so the user can input a fromDate
        fromDate.setDateFormatString("yyyy-MM-dd"); // Set the date format
        fromDate.setDate(NutrifitVisualizer.this.dateRange.getFrom()); // Set the default date
        this.addComponent(fromDate); // Add the component

        JDateChooser toDate = new JDateChooser(); // Create JDateChooser object so the user can input a toDate
        toDate.setDateFormatString("yyyy-MM-dd"); // Create JDateChooser object so the user can input a fromDate
        toDate.setDate(NutrifitVisualizer.this.dateRange.getTo()); // Set the default date
        this.addComponent(toDate);// Add the component
        addButton("Update Date Range", event -> { // Add a button
            NutrifitVisualizer.this.dateRange = new DateRange(fromDate.getDate(), toDate.getDate());
            NutrifitVisualizer.this.expanded = false; // Set the chart to not be expanded

            // Fire an event indicating that the user updated the date range (Observer Pattern)
            NutriFit.getInstance().getEventManager().notify(iVisualizer.getChartName(), null, NutrifitVisualizer.this.dateRange);
        });
    }


    // Override the build method because we want to format it different
    @Override
    public void build() {
        JPanel container = new JPanel(); // Create a JPanel

        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS)); // Set the Layout
        container.add(this.chartPanel); // Add the chart (or the Bar/Pie chart)
        container.add(getMainJPanel()); // Add the Main panel (which contains our buttons)

        // Add the JPanel to the content pane and pack it
        this.getContentPane().add(container);
        this.pack();
    }

    @Override
    public void chartMouseClicked(ChartMouseEvent chartMouseEvent) {

        if (!isValidChartClick(chartMouseEvent)) return;

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM dd yyyy"); // Create a simple date format for parsing dates
        String data = this.parseClickedSlice(chartMouseEvent.getEntity());

        try {
            // Parse the dateText into a date, if we thrown an exception (if we cannot parse a date) we will sanitize and pass in the name of the clicked bar or pie slice
            Date date = simpleDateFormat.parse(data);

            // Call the Event and notify it's subscribers (Observer Pattern)
            NutriFit.getInstance().getEventManager().notify(iVisualizer.getChartName(), data.split("\\(")[0].replace(":", "").trim(), new FullDayDateRange(date.getTime()));
            NutrifitVisualizer.this.expanded = true; // Set the chart to expanded
            return;
        } catch (Exception e) {} // Do nothing as it's not a date range

        // Call the Event and notify it's subscribers (Observer Pattern)
        NutriFit.getInstance().getEventManager().notify(iVisualizer.getChartName(), data, NutrifitVisualizer.this.dateRange);
        NutrifitVisualizer.this.expanded = true; // Set the chart to expanded
    }

    private String parseClickedSlice(ChartEntity chart) {
        if (chart instanceof PieSectionEntity) { // If the user clicked a Pie Slice (In a Pie Chart)
            return ((PieSectionEntity) chart).getSectionKey().toString().split("\\(")[0].trim(); // Get and Cleanup the name of the pie slice the user clicked
        } else if (chart instanceof CategoryItemEntity) { // If the user clicked a CategoryItemEntity (or Bar in a Bar Graph)
            return ((CategoryItemEntity) chart).getColumnKey().toString().split("\\(")[0].trim(); // Get and Cleanup the name of the bar the user clicked
        } else {
            return null;
        }
    }

    private boolean isValidChartClick(ChartMouseEvent event) {
        return event.getEntity() != null && this.iVisualizer.isChartExpandable() && !this.expanded;
    }

    @Override
    public void chartMouseMoved(ChartMouseEvent chartMouseEvent) {
    }
}
