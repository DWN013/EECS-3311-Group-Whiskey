package co.yorku.nutrifit.ui.impl.exercise;

import co.yorku.nutrifit.NutriFit;
import co.yorku.nutrifit.logs.LogIterator;
import co.yorku.nutrifit.logs.impl.Exercise;
import co.yorku.nutrifit.ui.NutrifitWindow;
import co.yorku.nutrifit.ui.impl.main.NutriFitMainUI;

import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import java.awt.*;
import java.util.Date;

public class ViewWeightLoss extends NutrifitWindow {
    public ViewWeightLoss() {
        super("Weight Loss", new GridLayout());
        LogIterator iterator = NutriFit.getInstance().getUserDatabase().getUserExerciseLogs(
                NutriFit.getInstance().getLoadedProfile().getId(),
                new Date(System.currentTimeMillis() - TimeUnit.DAYS.toMillis(14)),
                new Date(System.currentTimeMillis())
        );
        //Sorts by oldest to newest dates
        iterator.sortByDateAscending();

        while (iterator.hasNext()){
            Exercise exercise = (Exercise) iterator.getNext();
            
        }

        this.addBackButton(MainExerciseMenu.getInstance());
        this.build();
    }
}
