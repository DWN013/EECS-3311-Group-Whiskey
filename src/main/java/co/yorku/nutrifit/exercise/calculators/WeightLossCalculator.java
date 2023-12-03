package co.yorku.nutrifit.exercise.calculators;

import co.yorku.nutrifit.NutriFit;
import co.yorku.nutrifit.logs.LogIterator;
import co.yorku.nutrifit.logs.impl.Meal;
import co.yorku.nutrifit.object.daterange.TwoWeekDateRange;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/*
    Need to know how many calories I burn living (figure out how activate user is)
    Weightloss calc uses BMR and Activity rating calc and multiplies them
    RMR == Resting Metabolic Rate
 */

public class WeightLossCalculator {
    private ActivityRatingCalculator actRating;
    private BMRCalculator bmr;

    public double getWeightLossForDate(Date selectedDate){

        TwoWeekDateRange twoWeekDateRange = new TwoWeekDateRange();

        LogIterator diet_iterator = NutriFit.getInstance().getUserDatabase().getUserMealLogs(
                NutriFit.getInstance().getLoadedProfile().getId(),
                twoWeekDateRange
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
        //Initialize variables/calculators
        this.actRating = new ActivityRatingCalculator();
        this.bmr = new BMRCalculator();
        //Gets information needed for calculation
        double act_rating = actRating.getUserActivityRating();
        int  bmrCalc = bmr.getBMR(NutriFit.getInstance().getLoadedProfile());
        double caloriesExpendedPerDay = (act_rating * bmrCalc);
        //Sets selected date time to 00:00:00
        selectedDate.setHours(0);
        selectedDate.setMinutes(0);
        selectedDate.setSeconds(0);
        // Calculate the time difference in milliseconds
        long timeDifference = selectedDate.getTime() - twoWeekDateRange.getTo().getTime();
        // Calculate the difference in days, casts to an int
        int daysApart = (int) (timeDifference / (1000 * 60 * 60 * 24));

        // Multiplies gain or loss by the amount of days we are getting a prediction for (assumes rate will remain the same)
        //Calorie intake - RMR
        double caloriesGainedOrLost = (totalAverageCalorieIntake - caloriesExpendedPerDay) * daysApart;
        //7700 kCal == 1kg
        int calsTokg = 7700;
        //Makes final calculation
        double kgGainedOrLost = (caloriesGainedOrLost/calsTokg);
        //Returns final result
        return kgGainedOrLost;
    }
}
