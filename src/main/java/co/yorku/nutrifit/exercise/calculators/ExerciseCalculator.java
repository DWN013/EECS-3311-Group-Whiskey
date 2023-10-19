package co.yorku.nutrifit.exercise.calculators;

import co.yorku.nutrifit.exercise.impl.ActivityCalorieTemp;
import co.yorku.nutrifit.object.ActivityType;
import co.yorku.nutrifit.object.Intensity;
import co.yorku.nutrifit.profile.IProfile;

public class ExerciseCalculator {

    public int calculateTotalCaloriesBurned(ActivityType activityType, Intensity intensity, int durationInSeconds, IProfile profile) {

        float userWeight = profile.getWeight();
        if (profile.isMetric()) {
            userWeight *= 2.2f;
        }

        float caloriesBurned = new ActivityCalorieTemp().getCaloriesBurnedForActivityType(activityType, intensity, userWeight);
        return (int) (caloriesBurned * durationInSeconds);
    }

}
