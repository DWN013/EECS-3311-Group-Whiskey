package co.yorku.nutrifit.exercise;

import co.yorku.nutrifit.logs.impl.Exercise;

/*This is the interface for the exercise class
  The purpose of this interface is to handle interaction
  related to the user logging of exercise data
*/
public interface IExercise {

    void updateTotalCaloriesBurned(Exercise exercise);

}
