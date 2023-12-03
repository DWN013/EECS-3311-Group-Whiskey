package co.yorku.nutrifit.exercise.calculators;

import co.yorku.nutrifit.NutriFit;
import co.yorku.nutrifit.logs.LogIterator;
import co.yorku.nutrifit.logs.impl.Exercise;
import co.yorku.nutrifit.object.daterange.TwoWeekDateRange;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/*
This class is responsible for finding the "activity rating" of the user
based on how active they are over a period of 2 weeks and giving them a score.
In other words, the class calculates how active you are.
 */

public class ActivityRatingCalculator {

    public double getUserActivityRating(){
        //Gets the logs for 14 days worth of exercises or as many as possible up to 14 days
        LogIterator ex_iterator = NutriFit.getInstance().getUserDatabase().getUserExerciseLogs(
                    NutriFit.getInstance().getLoadedProfile().getId(),
                    new TwoWeekDateRange()
            );
            //Sort data from oldest to newest log
            ex_iterator.sortByDateAscending();
            int totalExerciseTime = 0;
            //Goes through each log till it reaches the end
            while (ex_iterator.hasNext()){
                Exercise exercise = (Exercise) ex_iterator.getNext();
                if (exercise != null) {
                    // Add exercise duration to total exercise time in minutes
                    totalExerciseTime += ((exercise.getTimeSpentInSeconds()) / 60);
                }
            }
            //Decision logic for how much exercise the individual does
            //Based on how much time they spend exercising ie. 181 mins == 1.55
            double averageExerciseTimePerWeek = ((double) totalExerciseTime)/2;
            if (averageExerciseTimePerWeek <= 0.0) {
                return 1.2;
            } else if (averageExerciseTimePerWeek > 0.0 && averageExerciseTimePerWeek <= 180.0) {
                return 1.375;
            } else if (averageExerciseTimePerWeek > 180.0 && averageExerciseTimePerWeek <= 300.0) {
                return 1.55;
            } else if (averageExerciseTimePerWeek > 300.0 && averageExerciseTimePerWeek <= 420.0) {
                return 1.725;
            } else {
                return 1.9;
            }
    }
}
