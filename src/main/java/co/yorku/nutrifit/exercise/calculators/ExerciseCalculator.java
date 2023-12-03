package co.yorku.nutrifit.exercise.calculators;

import co.yorku.nutrifit.NutriFit;
import co.yorku.nutrifit.exercise.impl.CaloriesBurntList;
import co.yorku.nutrifit.logs.impl.Exercise;
import co.yorku.nutrifit.object.ActivityType;
import co.yorku.nutrifit.object.Intensity;
import co.yorku.nutrifit.profile.IProfile;

public class ExerciseCalculator {
    //Calculation to find calories burned from an exercise
    public void calculateTotalCaloriesBurned(Exercise exercise) {

        IProfile profile = NutriFit.getInstance().getLoadedProfile();
        float userWeight = profile.getWeight();
        //Calorie burn list is always given in imperial and converts to metric
        //if the profile has it's info in metric
        if (profile.isMetric()) {
            userWeight *= 2.2f;
        }

        exercise.setTotalCaloriesBurned((int) (new CaloriesBurntList().getCaloriesBurnedForActivityType(exercise.getActivityType(), exercise.getIntensity(), userWeight) * exercise.getTimeSpentInSeconds()));
    }

}
