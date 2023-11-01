package co.yorku.nutrifit.ui.impl.visualizer;

import co.yorku.nutrifit.ui.JFrameVisualizer;
import co.yorku.nutrifit.ui.NutrifitWindow;
import co.yorku.nutrifit.visualizer.IVisualizer;

public class AverageUserFoodPlateVisualizer extends JFrameVisualizer {

    public AverageUserFoodPlateVisualizer(IVisualizer iVisualizer, NutrifitWindow parent) {
        super("Average User Food Plate", iVisualizer);

        this.addBackButton(parent);
        this.build();
    }
}
