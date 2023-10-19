package co.yorku.nutrifit.exercise;

import co.yorku.nutrifit.object.ActivityType;
import co.yorku.nutrifit.object.Intensity;
import co.yorku.nutrifit.profile.IProfile;

//This is the interface for the exercise class
//The purpose of this interface is to handle interaction
//related to the user logging of exercise data
public interface IExercise {

    int getTotalCaloriesBurned(ActivityType activityType, Intensity intensity, int durationInSeconds, IProfile profile);

}
