package co.yorku.nutrifit.ui.impl.exercise;

import co.yorku.nutrifit.ui.NutrifitWindow;
import co.yorku.nutrifit.ui.impl.main.NutriFitMainUI;

import java.awt.*;

public class ViewWeightLoss extends NutrifitWindow {
    private static ViewWeightLoss instance;

    public static ViewWeightLoss getInstance() {
        if (instance == null) {
            instance = new ViewWeightLoss();
        }
        return instance;
    }

    public ViewWeightLoss() {
        super("Weight Loss", new GridLayout());

        this.addBackButton(NutriFitMainUI.getInstance());
        this.build();
    }
}
