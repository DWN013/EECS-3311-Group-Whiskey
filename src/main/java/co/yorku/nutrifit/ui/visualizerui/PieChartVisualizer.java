package co.yorku.nutrifit.ui.visualizerui;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.util.TableOrder;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;

public class PieChartVisualizer extends JFrame {

    public PieChartVisualizer() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.addValue(3.946, "Unemployed", "Men");
        dataset.addValue(96.054, "Employed", "Men");
        dataset.addValue(3.837, "Unemployed", "Women");
        dataset.addValue(96.163, "Employed", "Women");

        JFreeChart pieChart = ChartFactory.createMultiplePieChart("Unemployment: Men vs Women", dataset,
                TableOrder.BY_COLUMN, true, true, false);

        ChartPanel chartPanel = new ChartPanel(pieChart);
        chartPanel.setPreferredSize(new Dimension(400, 300));
        chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        chartPanel.setBackground(Color.white);

        getContentPane().add(chartPanel);
    }

}
