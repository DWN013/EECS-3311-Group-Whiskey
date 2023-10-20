package co.yorku.nutrifit.logs.impl;

import co.yorku.nutrifit.logs.Log;
import co.yorku.nutrifit.object.ActivityType;
import co.yorku.nutrifit.object.Intensity;

import java.util.Date;

public class Exercise extends Log {
    private int timeSpentInSeconds;
    private ActivityType activityType;
    private Intensity intensity;
    private int totalCaloriesBurned;

    public Exercise(Date date, int timeSpentInSeconds, ActivityType activityType, Intensity intensity, int totalCaloriesBurned) {
        super(date);
        this.timeSpentInSeconds = timeSpentInSeconds;
        this.activityType = activityType;
        this.intensity = intensity;
        this.totalCaloriesBurned = totalCaloriesBurned;
    }

    public int getTimeSpentInSeconds() {
        return timeSpentInSeconds;
    }

    public ActivityType getActivityType() {
        return activityType;
    }

    public Intensity getIntensity() {
        return intensity;
    }

    public int getTotalCaloriesBurned() {
        return totalCaloriesBurned;
    }
}
