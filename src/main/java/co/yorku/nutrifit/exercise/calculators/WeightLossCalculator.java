package co.yorku.nutrifit.exercise.calculators;

import co.yorku.nutrifit.NutriFit;
import co.yorku.nutrifit.logs.LogIterator;
import co.yorku.nutrifit.logs.impl.Meal;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class WeightLossCalculator {
    private ActivityRatingCalculator actRating;
    private BMRCalculator bmr;
    // Need to know how many calories I burn living (figure out how activate user is)
    // Weightloss calc uses BMR and Activity rating calc and multiplies em,
    // RMR == Resting Metabolic Rate

    public double getWeightLossForDate(Date selectedDate){
        Date today = new Date(System.currentTimeMillis());
        Date twoWeeksAgo = new Date(System.currentTimeMillis() - TimeUnit.DAYS.toMillis(14));
        //---------------------------------
        //Sets time from morning of very oldest day to midnight of current day so that we do not exclude logs by accident
        twoWeeksAgo.setHours(0);
        twoWeeksAgo.setMinutes(0);
        twoWeeksAgo.setSeconds(0);
        today.setHours(23);
        today.setMinutes(59);
        today.setSeconds(59);
        //---------------------------------
        LogIterator diet_iterator = NutriFit.getInstance().getUserDatabase().getUserMealLogs(
                NutriFit.getInstance().getLoadedProfile().getId(),
                twoWeeksAgo, today
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
        //Initialize variables
        this.actRating = new ActivityRatingCalculator();
        this.bmr = new BMRCalculator();

        double act_rating = actRating.getUserActivityRating();
        int  bmrCalc = bmr.getBMR(NutriFit.getInstance().getLoadedProfile());
        double caloriesExpendedPerDay = (act_rating * bmrCalc);

        selectedDate.setHours(0);
        selectedDate.setMinutes(0);
        selectedDate.setSeconds(0);
        // Calculate the time difference in milliseconds
        long timeDifference = selectedDate.getTime() - today.getTime();
        // Calculate the difference in days, casts to an int
        int daysApart = (int) (timeDifference / (1000 * 60 * 60 * 24));

        // Multiplies gain or loss by the amount of days we are getting a prediction for (assumes rate will remain the same)
        //Calorie intake - RMR
        double caloriesGainedOrLost = (totalAverageCalorieIntake - caloriesExpendedPerDay) * daysApart;
        int calsTokg = 7700;
        //Makes final calculation
        double kgGainedOrLost = (caloriesGainedOrLost/calsTokg);

        return kgGainedOrLost;
    }
}
