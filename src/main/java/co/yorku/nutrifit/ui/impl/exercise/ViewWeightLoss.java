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
        LogIterator ex_iterator = NutriFit.getInstance().getUserDatabase().getUserExerciseLogs(
                NutriFit.getInstance().getLoadedProfile().getId(),
                new Date(System.currentTimeMillis() - TimeUnit.DAYS.toMillis(14)),
                new Date(System.currentTimeMillis())
        );
        LogIterator diet_iterator = NutriFit.getInstance().getUserDatabase().getUserMealLogs(
                NutriFit.getInstance().getLoadedProfile().getId(),
                new Date(System.currentTimeMillis() - TimeUnit.DAYS.toMillis(14)),
                new Date(System.currentTimeMillis())
        );
        // Gets users RMR, only need the users profile ID as input.
        double userBMR = 0;
        // Sorts by oldest to newest dates
        ex_iterator.sortByDateAscending();
        diet_iterator.sortByDateAscending();

        // While there is still a log next in the linked list for both meal and exercise, go to the next log
        while (ex_iterator.hasNext() && diet_iterator.hasNext()){

            //Go to next set of logs
            Exercise exercise = (Exercise) ex_iterator.getNext();
            Meal diet = (Meal) diet_iterator.getNext();
        }

        this.addBackButton(MainExerciseMenu.getInstance());
        this.build();
    }
}
