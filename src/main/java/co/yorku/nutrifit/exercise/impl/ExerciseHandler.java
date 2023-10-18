package co.yorku.nutrifit.exercise.impl;

import co.yorku.nutrifit.database.userdata.IUserDatabase;
import co.yorku.nutrifit.exercise.Exercise;
import co.yorku.nutrifit.exercise.calculators.ExerciseCalculator;
import co.yorku.nutrifit.exercise.calculators.WeightLossCalculator;

public class ExerciseHandler {

    private ExerciseCalculator exerciseCalculator;
    private WeightLossCalculator weightLossCalculator;
    private IUserDatabase userDatabaseInterface;

    public ExerciseHandler(ExerciseCalculator exerciseCalculator, WeightLossCalculator weightLossCalculator, IUserDatabase userDatabaseInterface) {
        this.exerciseCalculator = exerciseCalculator;
        this.weightLossCalculator = weightLossCalculator;
        this.userDatabaseInterface = userDatabaseInterface;
    }

}
