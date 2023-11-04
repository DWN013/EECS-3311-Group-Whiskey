package co.yorku.nutrifit.ui.impl.visualizer;

import co.yorku.nutrifit.NutriFit;
import co.yorku.nutrifit.ui.NutrifitWindow;
import co.yorku.nutrifit.visualizer.IVisualizer;
import com.google.common.base.Preconditions;
import com.toedter.calendar.JDateChooser;
import javafx.util.Pair;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class NutrifitVisualizer extends NutrifitWindow {

    private ChartPanel chartPanel;

    public NutrifitVisualizer(String windowName, NutrifitWindow parent, Pair<JFreeChart, IVisualizer> data) {
        super(windowName, new GridLayout(1, 5));

        this.chartPanel = new ChartPanel(data.getKey());
        this.chartPanel.setPreferredSize(new Dimension(800, 600));
        this.chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        this.chartPanel.setBackground(Color.white);

        this.addFromToButtons();
        this.addBackButton(parent, event -> NutriFit.getInstance().getEventManager().unsubscribe(data.getValue()));

        this.setSize(900, 600);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        this.build();
    }

    // By Default we will show the last 7 days
    public void addFromToButtons() {

        addLabel("Date From To:");
        JDateChooser fromDate = new JDateChooser();
        fromDate.setDateFormatString("yyyy-MM-dd");
        fromDate.setDate(new Date(System.currentTimeMillis() - TimeUnit.DAYS.toMillis(7)));
        this.addComponent(fromDate);

        JDateChooser toDate = new JDateChooser();
        toDate.setDateFormatString("yyyy-MM-dd");
        toDate.setDate(new Date(System.currentTimeMillis()));
        this.addComponent(toDate);
        addButton("Update Date Range", event -> NutriFit.getInstance().getEventManager().notify(fromDate.getDate(), toDate.getDate()));
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
