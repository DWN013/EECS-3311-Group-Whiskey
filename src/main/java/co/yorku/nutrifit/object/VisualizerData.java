package co.yorku.nutrifit.object;

import co.yorku.nutrifit.visualizer.IVisualizer;
import org.jfree.chart.JFreeChart;


public class VisualizerData {
    private JFreeChart jFreeChart;
    private IVisualizer iVisualizer;

    public VisualizerData(JFreeChart jFreeChart, IVisualizer iVisualizer) {
        this.jFreeChart = jFreeChart;
        this.iVisualizer = iVisualizer;
    }

    public JFreeChart getChart() {
        return jFreeChart;
    }

    public IVisualizer getVisualizer() {
        return iVisualizer;
    }
}
