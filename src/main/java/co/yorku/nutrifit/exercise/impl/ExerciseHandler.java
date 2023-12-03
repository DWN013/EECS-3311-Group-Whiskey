package co.yorku.nutrifit.exercise.impl;

import co.yorku.nutrifit.exercise.IExercise;
import co.yorku.nutrifit.exercise.calculators.ExerciseCalculator;
import co.yorku.nutrifit.logs.impl.Exercise;
import co.yorku.nutrifit.object.ActivityType;
import co.yorku.nutrifit.object.Intensity;
import co.yorku.nutrifit.profile.IProfile;

/*
 * Handler for calculating total calories burned
 */

public class ExerciseHandler implements IExercise {

    private ExerciseCalculator exerciseCalculator;

    public ExerciseHandler(ExerciseCalculator exerciseCalculator) {
        this.exerciseCalculator = exerciseCalculator;
    }

    //Get user exercise data and calculate calories burnt 
    @Override
    public void updateTotalCaloriesBurned(Exercise exercise) {
        this.exerciseCalculator.calculateTotalCaloriesBurned(exercise);
    }
}
