package co.yorku.nutrifit.exercise.calculators;

import co.yorku.nutrifit.NutriFit;
import co.yorku.nutrifit.logs.LogIterator;
import co.yorku.nutrifit.logs.impl.Exercise;
import co.yorku.nutrifit.logs.impl.Meal;
import co.yorku.nutrifit.profile.IProfile;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class WeightLossCalculator {
    // Need to know how many calories I burn living (figure out how activate user is)
    // RMR == Resting Metabolic Rate
    public double getUserRMR(IProfile profile){
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
        // Sorts by oldest to newest dates
        ex_iterator.sortByDateAscending();
        diet_iterator.sortByDateAscending();

        // While there is still a log next in the linked list for both meal and exercise, go to the next log
        //correct this, people don't need to exercise every day but they do need to eat
        int totalExerciseTime = 0;
        int totalCalorieIntake = 0;
        while (ex_iterator.hasNext() || diet_iterator.hasNext()){
            Exercise exercise = (Exercise) ex_iterator.getNext();
            Meal diet = (Meal) diet_iterator.getNext();
            if (exercise != null) {
                // Add exercise duration to total exercise time
                totalExerciseTime += (exercise.getTimeSpentInSeconds())/60;
            }

            if (diet != null) {
                // Add meal calorie intake to total calorie intake
                totalCalorieIntake += diet.getTotalMealCalories();
            }
        }
        //End of aggregation of exercise and meal info
        //------------------------------------------------------------------------------------
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
