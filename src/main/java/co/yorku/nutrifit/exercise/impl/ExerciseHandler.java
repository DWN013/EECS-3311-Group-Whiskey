package co.yorku.nutrifit.exercise.impl;

import co.yorku.nutrifit.database.userdata.UserDatabaseInterface;
import co.yorku.nutrifit.exercise.Exercise;
import co.yorku.nutrifit.exercise.calculators.ExerciseCalculator;
import co.yorku.nutrifit.exercise.calculators.WeightLossCalculator;

public class ExerciseHandler implements Exercise {

    private ExerciseCalculator exerciseCalculator;
    private WeightLossCalculator weightLossCalculator;
    private UserDatabaseInterface userDatabaseInterface;

    public ExerciseHandler(ExerciseCalculator exerciseCalculator, WeightLossCalculator weightLossCalculator, UserDatabaseInterface userDatabaseInterface) {
        this.exerciseCalculator = exerciseCalculator;
        this.weightLossCalculator = weightLossCalculator;
        this.userDatabaseInterface = userDatabaseInterface;
    }
}
