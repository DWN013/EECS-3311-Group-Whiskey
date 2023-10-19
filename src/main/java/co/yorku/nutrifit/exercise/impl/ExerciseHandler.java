package co.yorku.nutrifit.exercise.impl;

import co.yorku.nutrifit.exercise.IExercise;
import co.yorku.nutrifit.exercise.calculators.ExerciseCalculator;
import co.yorku.nutrifit.exercise.calculators.WeightLossCalculator;
import co.yorku.nutrifit.object.ActivityType;
import co.yorku.nutrifit.object.Intensity;
import co.yorku.nutrifit.profile.IProfile;

public class ExerciseHandler implements IExercise {

    private ExerciseCalculator exerciseCalculator;
    private WeightLossCalculator weightLossCalculator;

    public ExerciseHandler(ExerciseCalculator exerciseCalculator, WeightLossCalculator weightLossCalculator) {
        this.exerciseCalculator = exerciseCalculator;
        this.weightLossCalculator = weightLossCalculator;
    }

    @Override
    public int getTotalCaloriesBurned(ActivityType activityType, Intensity intensity, int durationInSeconds, IProfile profile) {
        return this.exerciseCalculator.calculateTotalCaloriesBurned(activityType, intensity, durationInSeconds, profile);
    }
}
