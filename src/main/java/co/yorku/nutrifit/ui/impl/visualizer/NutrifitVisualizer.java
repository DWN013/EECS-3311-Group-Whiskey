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

public class NutrifitVisualizer extends NutrifitWindow {

    private ChartPanel chartPanel;
    private IVisualizer iVisualizer;
    private Date fromDate;
    private Date toDate;

    private boolean expanded;

    public NutrifitVisualizer(String windowName, NutrifitWindow parent, VisualizerData data, Date fromDate, Date toDate) {
        super(windowName, new GridLayout(1, 5));

        this.iVisualizer = data.getVisualizer();
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.expanded = false;
        this.chartPanel = new ChartPanel(data.getChart());
        this.chartPanel.setPreferredSize(new Dimension(800, 600));
        this.chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        this.chartPanel.setBackground(Color.white);
        this.chartPanel.addChartMouseListener(new ChartMouseListener() {
            @Override
            public void chartMouseClicked(ChartMouseEvent chartMouseEvent) {


                if (chartMouseEvent.getEntity() == null) return;
                if (!NutrifitVisualizer.this.iVisualizer.isChartExpandable()) return;
                if (NutrifitVisualizer.this.expanded) return;

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM dd yyyy");
                String dateText = chartMouseEvent.getEntity().getToolTipText();

                try {
                    Date fromDate = simpleDateFormat.parse(dateText);
                    Date toDate = simpleDateFormat.parse(dateText);

                    fromDate.setHours(0);
                    fromDate.setMinutes(0);
                    fromDate.setSeconds(0);

                    toDate.setHours(23);
                    toDate.setMinutes(59);
                    toDate.setSeconds(59);

                    NutriFit.getInstance().getEventManager().notify(iVisualizer.getChartName(), fromDate + ":" + toDate, fromDate, toDate);
                    NutrifitVisualizer.this.expanded = true;
                } catch (Exception e) {

                    String expandData = chartMouseEvent.getEntity().getToolTipText();

                    if (chartMouseEvent.getEntity() instanceof PieSectionEntity) {
                        expandData = ((PieSectionEntity) chartMouseEvent.getEntity()).getSectionKey().toString().split("\\(")[0].trim();
                    } else if (chartMouseEvent.getEntity() instanceof CategoryItemEntity) {
                        expandData = ((CategoryItemEntity) chartMouseEvent.getEntity()).getColumnKey().toString().split("\\(")[0].trim();
                    } else {
                        return; // do nothing
                    }

                    // If we cannot parse the date from the clicked bar or pie entity, just send the tooltip to the specific visualizer, it will handle it with the default date range.
                    NutriFit.getInstance().getEventManager().notify(iVisualizer.getChartName(), expandData, NutrifitVisualizer.this.fromDate, NutrifitVisualizer.this.toDate);
                    NutrifitVisualizer.this.expanded = true;
                }

            }

            @Override
            public void chartMouseMoved(ChartMouseEvent chartMouseEvent) {
            }
        });

        this.addFromToButtons(this.fromDate, this.toDate);
        this.addBackButton(parent, event -> NutriFit.getInstance().getEventManager().unsubscribe(this.iVisualizer));

        this.setSize(900, 600);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        this.build();
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


    @Override
    public void build() {
        JPanel container = new JPanel();

        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.add(this.chartPanel);
        container.add(getMainJPanel());

        this.getContentPane().add(container);
        this.pack();
    }
}
