package co.yorku.nutrifit.ui.impl.meal;

import co.yorku.nutrifit.ui.NutrifitWindow;
import co.yorku.nutrifit.ui.impl.main.NutriFitMainUI;

import java.awt.*;

public class MainMealMenu extends NutrifitWindow {

    private static MainMealMenu instance;

    public static MainMealMenu getInstance() {
        if (instance == null) {
            instance = new MainMealMenu();
        }
        return instance;
    }

    private MainMealMenu() {
        super("Meal Menu", new GridLayout());
        addButton("Log Meal", event -> {
            this.hideWindow();
            MealInputUI.getInstance().showWindow();
        });
        addButton("Show Meal Log", event -> {
            this.hideWindow();
            new MealDisplayUI().showToUser();
        });

        this.addBackButton(NutriFitMainUI.getInstance());
        this.build();
    }
}
