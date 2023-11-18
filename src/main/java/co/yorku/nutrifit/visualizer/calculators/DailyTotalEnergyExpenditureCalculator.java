package co.yorku.nutrifit.visualizer.calculators;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;

import com.google.common.collect.Maps;

import co.yorku.nutrifit.NutriFit;
import co.yorku.nutrifit.logs.LogIterator;
import co.yorku.nutrifit.logs.impl.Exercise;
import co.yorku.nutrifit.exercise.calculators.BMRCalculator;
import co.yorku.nutrifit.exercise.calculators.ActivityRatingCalculator;

/*
    This is the DailyTotalEnergyExpenditureCalculator.
    It's purpose is to calculate daily total expenditure over a time period.
 */
public class DailyTotalEnergyExpenditureCalculator {

    // This method returns a LinkedHashMap of type String, Integer which contain
    // a Day and the total calories burned for that day
    public LinkedHashMap<String, Integer> getTDEE(Date dateFrom, Date dateTo) {
        // Query the database for all the logs for the specific time period
        LogIterator logIterator = NutriFit.getInstance().getUserDatabase().getUserExerciseLogs(
                NutriFit.getInstance().getLoadedProfile().getId(),
                dateFrom,
                dateTo
        );
        logIterator.sortByDateAscending(); // Sort by ascending

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM dd yyyy"); // Create a SimpleDateFormat object to format Date objects
        LinkedHashMap<String, Integer> caloriesBurnedPerDay = Maps.newLinkedHashMap(); // Create the map that will be returned

        int bmr = new BMRCalculator().getBMR(NutriFit.getInstance().getLoadedProfile()); // Calculate the BMR
        double activityR = new ActivityRatingCalculator().getUserActivityRating(); // Calculate the User Activity Rating

		   /*
		   		The below block of if-else statements calculates the TDEE (Total Daily Energy Expenditure)

		   		TDEE is calculated by adding up the BMR (basic metabolic rate), TEF (thermic effect of food), neat (non exercise activity thermogenesis)
		   		as well as calories burned during exercise. TEF is calculated as approximately 10% of the BMR value.
		   		NEAT is calculated by getting users activity rating and seeing how active they are usually then assigning
		   		a certain amount of calories (250-500) based on that value. BMR is calculated by using
		   		the users profile data and the Mifflin-St. Jeor equation. TEF and NEAT are calculated below
		   		and added up to make Total Daily Energy Expenditure
		    */

        int neat = 0;
        if (activityR < 1.5) {
            neat = 250;
        } else if (activityR == 1.55) {
            neat = 350;
        } else {
            neat = 500;
        }
        int tef = (int) Math.round(bmr * 0.1);
        int tdee = bmr + tef + neat;


        while (logIterator.hasNext()) { // Loop through every exercise log
            Exercise exercise = (Exercise) logIterator.getNext(); // Get the exercise log
            Date date = exercise.getDate(); // Get the date from the exercise object
            String key = simpleDateFormat.format(date); // Format the date

			// Increment the total calories burned for the day
            caloriesBurnedPerDay.put(key, caloriesBurnedPerDay.getOrDefault(key, tdee) + exercise.getTotalCaloriesBurned());
        }

		// Return the data
        return caloriesBurnedPerDay;
    }

}
