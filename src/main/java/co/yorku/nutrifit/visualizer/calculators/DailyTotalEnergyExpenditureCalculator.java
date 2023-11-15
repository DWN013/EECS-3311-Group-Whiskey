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

public class DailyTotalEnergyExpenditureCalculator{
	
	public LinkedHashMap<String, Integer>  getTDEE(Date dateFrom, Date dateTo)
	{
		
		 LogIterator logIterator = NutriFit.getInstance().getUserDatabase().getUserExerciseLogs(
	                NutriFit.getInstance().getLoadedProfile().getId(),
	                dateFrom,
	                dateTo
	        );
	        logIterator.sortByDateAscending();

	        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM dd yyyy");
	        LinkedHashMap<String, Integer> caloriesBurnedPerDay = Maps.newLinkedHashMap();
	        
	        int bmr = new BMRCalculator().getBMR(NutriFit.getInstance().getLoadedProfile());
	        double activityR = new ActivityRatingCalculator().getUserActivityRating();
	        int neat = 0;
	        if (activityR < 1.5)
	        {
	        	neat = 250;
	        }
	        else if (activityR == 1.55)
	        {
	        	neat = 350;
	        }
	        else
	        {
	        	neat = 500;
	        }
	        int tef = (int) Math.round(bmr*0.1);
	        int tdee = bmr + tef + neat; 
	        

	        while (logIterator.hasNext()) {
	            Exercise exercise = (Exercise)  logIterator.getNext();
	            Date date = exercise.getDate();
	            String key = simpleDateFormat.format(date);

	            caloriesBurnedPerDay.put(key, caloriesBurnedPerDay.getOrDefault(key, tdee) + exercise.getTotalCaloriesBurned());
	        }
		return caloriesBurnedPerDay;
	}

}
