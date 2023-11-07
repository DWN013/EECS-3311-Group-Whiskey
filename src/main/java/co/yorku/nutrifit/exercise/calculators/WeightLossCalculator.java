package co.yorku.nutrifit.exercise.calculators;

import co.yorku.nutrifit.NutriFit;
import co.yorku.nutrifit.logs.LogIterator;
import co.yorku.nutrifit.logs.impl.Exercise;
import co.yorku.nutrifit.logs.impl.Meal;
import co.yorku.nutrifit.profile.IProfile;
import co.yorku.nutrifit.visualizer.calulcators.DailyTotalEnergyExpenditureCalculator;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class WeightLossCalculator {
    private DailyTotalEnergyExpenditureCalculator dailyTotalExpend;
    // Need to know how many calories I burn living (figure out how activate user is)
    // Weightloss calc uses BMR and Activity rating calc and multiplies em,
    // RMR == Resting Metabolic Rate
    public void getUserRMR(IProfile profile){
        LogIterator diet_iterator = NutriFit.getInstance().getUserDatabase().getUserMealLogs(
                NutriFit.getInstance().getLoadedProfile().getId(),
                new Date(System.currentTimeMillis() - TimeUnit.DAYS.toMillis(14)),
                new Date(System.currentTimeMillis())
        );
        // Sorts by oldest to newest dates
        diet_iterator.sortByDateAscending();

        // While there is still a log next in the linked list for both meal and exercise, go to the next log
        //correct this, people don't need to exercise every day but they do need to eat
        int totalCalorieIntake = 0;
        while (diet_iterator.hasNext()){
            Meal diet = (Meal) diet_iterator.getNext();
            if (diet != null) {
                // Add meal calorie intake to total calorie intake
                totalCalorieIntake += diet.getTotalMealCalories();
            }
        }
        //Average amount of calories eaten per day.
        double totalAverageCalorieIntake = ((double) totalCalorieIntake)/14;
        double caloriesExpendedPerDay = dailyTotalExpend.getDTEE();

    }
}
