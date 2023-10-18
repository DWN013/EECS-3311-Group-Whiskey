package co.yorku.nutrifit.object;

import java.util.Date;

public class Exercise {
    private Date date;
    private int timeSpentInSeconds;
    private String typeOfExercise;
    private Intensity intensity;
    private int totalCaloriesBurned;

    public Exercise(Date date, int timeSpentInSeconds, String typeOfExercise, Intensity intensity, int totalCaloriesBurned) {
        this.date = date;
        this.timeSpentInSeconds = timeSpentInSeconds;
        this.typeOfExercise = typeOfExercise;
        this.intensity = intensity;
        this.totalCaloriesBurned = totalCaloriesBurned;
    }

    public Date getDate() {
        return date;
    }

    public int getTimeSpentInSeconds() {
        return timeSpentInSeconds;
    }

    public String getTypeOfExercise() {
        return typeOfExercise;
    }

    public Intensity getIntensity() {
        return intensity;
    }

    public int getTotalCaloriesBurned() {
        return totalCaloriesBurned;
    }
}
