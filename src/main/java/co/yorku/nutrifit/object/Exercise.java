package co.yorku.nutrifit.object;

import java.util.Date;

public class Exercise {
    private Date date;
    private int timeSpentInSeconds;
    private ActivityType activityType;
    private Intensity intensity;
    private int totalCaloriesBurned;

    public Exercise(Date date, int timeSpentInSeconds, ActivityType activityType, Intensity intensity, int totalCaloriesBurned) {
        this.date = date;
        this.timeSpentInSeconds = timeSpentInSeconds;
        this.activityType = activityType;
        this.intensity = intensity;
        this.totalCaloriesBurned = totalCaloriesBurned;
    }

    public Date getDate() {
        return date;
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
