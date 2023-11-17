package co.yorku.nutrifit.logs.impl;

import co.yorku.nutrifit.logs.ILog;
import co.yorku.nutrifit.object.ActivityType;
import co.yorku.nutrifit.object.Intensity;

import java.util.Date;

/*
 * Exercise object for holding exercise data - (extending ILog (iterator))
 */

public class Exercise extends ILog {
	
	//private instance variables
    private int timeSpentInSeconds;
    private ActivityType activityType;
    private Intensity intensity;
    private int totalCaloriesBurned;

    // Constructor initializing a Exercise object
    public Exercise(Date date, int timeSpentInSeconds, ActivityType activityType, Intensity intensity, int totalCaloriesBurned) {
        super(date);
        this.timeSpentInSeconds = timeSpentInSeconds;
        this.activityType = activityType;
        this.intensity = intensity;
        this.totalCaloriesBurned = totalCaloriesBurned;
    }
    
    /*
     * Getter Methods
     */
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
