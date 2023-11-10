package co.yorku.nutrifit.ui.impl.visualizer;

import co.yorku.nutrifit.NutriFit;
import co.yorku.nutrifit.ui.NutrifitWindow;
import co.yorku.nutrifit.visualizer.IVisualizer;
import com.google.common.base.Preconditions;
import com.toedter.calendar.JDateChooser;
import javafx.util.Pair;
import org.jfree.chart.ChartMouseEvent;
import org.jfree.chart.ChartMouseListener;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class NutrifitVisualizer extends NutrifitWindow {

    private ChartPanel chartPanel;
    private IVisualizer iVisualizer;

    public NutrifitVisualizer(String windowName, NutrifitWindow parent, Pair<JFreeChart, IVisualizer> data, Date defaultFromDate, Date defaultToDate) {
        super(windowName, new GridLayout(1, 5));

        this.chartPanel = new ChartPanel(data.getKey());
        this.chartPanel.setPreferredSize(new Dimension(800, 600));
        this.chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        this.chartPanel.setBackground(Color.white);

        this.chartPanel.addChartMouseListener(new ChartMouseListener() {
            @Override
            public void chartMouseClicked(ChartMouseEvent chartMouseEvent) {
                if (chartMouseEvent.getChart().getPlot() instanceof PiePlot) {

                    if (chartMouseEvent.getEntity() == null) return;

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

                        NutriFit.getInstance().getEventManager().notify(iVisualizer.getChartName() + "_EXPAND", fromDate, toDate);

                    } catch (Exception e) {
                        return;
                    }

                }
            }

            @Override
            public void chartMouseMoved(ChartMouseEvent chartMouseEvent) {
            }
        });

        this.iVisualizer = data.getValue();

        this.addFromToButtons(defaultFromDate, defaultToDate);
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
        addButton("Update Date Range", event -> NutriFit.getInstance().getEventManager().notify(iVisualizer.getChartName(), fromDate.getDate(), toDate.getDate()));
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
