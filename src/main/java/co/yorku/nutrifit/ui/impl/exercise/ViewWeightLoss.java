package co.yorku.nutrifit.ui.impl.exercise;

import co.yorku.nutrifit.NutriFit;
import co.yorku.nutrifit.logs.LogIterator;
import co.yorku.nutrifit.logs.impl.Exercise;
import co.yorku.nutrifit.logs.impl.Meal;
import co.yorku.nutrifit.ui.NutrifitWindow;
import co.yorku.nutrifit.ui.impl.main.NutriFitMainUI;
import co.yorku.nutrifit.visualizer.calulcators.DailyTotalEnergyExpenditureCalculator;

import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import java.awt.*;
import java.util.Date;

/*
Basically, what this is class is doing is getting the "activity rating" (aka. how much the user actually moves/exercises)
from class "DailyTotalEnergyExpenditureCalculator"
 */

public class ViewWeightLoss extends NutrifitWindow {
    public ViewWeightLoss() {
        super("Weight Loss", new GridLayout());


        this.addBackButton(MainExerciseMenu.getInstance());
        this.build();
    }
}
