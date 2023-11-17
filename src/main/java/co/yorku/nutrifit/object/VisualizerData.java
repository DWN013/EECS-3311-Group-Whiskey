package co.yorku.nutrifit.object;

import co.yorku.nutrifit.visualizer.IVisualizer;
import org.jfree.chart.JFreeChart;


// Class VisualizerData for storing chart and visualizer information
public class VisualizerData {
    // Private instance variables representing chart and visualizer
    private JFreeChart jFreeChart;
    private IVisualizer iVisualizer;

    // Constructor to initialize VisualizerData with a chart and a visualizer
    public VisualizerData(JFreeChart jFreeChart, IVisualizer iVisualizer) {
        this.jFreeChart = jFreeChart;
        this.iVisualizer = iVisualizer;
    }

    // Getter method to retrieve the stored chart
    public JFreeChart getChart() {
        return jFreeChart;
    }

    // Getter method to retrieve the stored visualizer
    public IVisualizer getVisualizer() {
        return iVisualizer;
    }
}

