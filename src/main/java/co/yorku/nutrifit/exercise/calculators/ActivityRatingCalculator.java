package co.yorku.nutrifit.exercise.calculators;

import co.yorku.nutrifit.NutriFit;
import co.yorku.nutrifit.exercise.IExercise;
import co.yorku.nutrifit.logs.LogIterator;
import co.yorku.nutrifit.logs.impl.Exercise;
import co.yorku.nutrifit.logs.impl.Meal;
import co.yorku.nutrifit.profile.IProfile;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class ActivityRatingCalculator {

    private IExercise exercise;
    // This class calculates how active am I
    public double getUserActivityRating(){
        //Gets the logs for 14 days worth of exercises
        LogIterator ex_iterator = NutriFit.getInstance().getUserDatabase().getUserExerciseLogs(
                    NutriFit.getInstance().getLoadedProfile().getId(),
                    new Date(System.currentTimeMillis() - TimeUnit.DAYS.toMillis(14)),
                    new Date(System.currentTimeMillis())
            );
            //Sort data from oldest to newest log
            ex_iterator.sortByDateAscending();
            int totalExerciseTime = 0;

                while (ex_iterator.hasNext()){
                Exercise exercise = (Exercise) ex_iterator.getNext();
                if (exercise != null) {
                    // Add exercise duration to total exercise time in minutes
                    totalExerciseTime += ((exercise.getTimeSpentInSeconds()) / 60);
                }
            }
            double averageExerciseTimePerWeek = ((double) totalExerciseTime)/14;
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
