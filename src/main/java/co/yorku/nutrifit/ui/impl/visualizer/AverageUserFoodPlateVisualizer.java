package co.yorku.nutrifit.ui.impl.visualizer;

import co.yorku.nutrifit.ui.NutrifitVisualizer;
import co.yorku.nutrifit.ui.NutrifitWindow;
import co.yorku.nutrifit.visualizer.IVisualizer;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;

import javax.swing.*;
import java.awt.*;

public class AverageUserFoodPlateVisualizer extends NutrifitVisualizer {

    public AverageUserFoodPlateVisualizer(IVisualizer iVisualizer, NutrifitWindow parent) {
        super("Average User Food Plate", iVisualizer);

        JFreeChart jFreeChart = iVisualizer.buildBarGraph();
        ChartPanel chartPanel = new ChartPanel(jFreeChart);
        chartPanel.setPreferredSize(new Dimension(400, 300));
        chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        chartPanel.setBackground(Color.white);

        addComponent(chartPanel);

        this.addBackButton(parent);
        this.build();
    }
}
