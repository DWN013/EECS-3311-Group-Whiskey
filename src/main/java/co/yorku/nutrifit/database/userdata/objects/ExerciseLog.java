package co.yorku.nutrifit.database.userdata.objects;

import java.util.Date;

public class ExerciseLog {
    private Date date;
    private int timeSpentInSeconds;
    private String typeOfExercise;
    private Intensity intensity;

    public ExerciseLog(Date date, int timeSpentInSeconds, String typeOfExercise, Intensity intensity) {
        this.date = date;
        this.timeSpentInSeconds = timeSpentInSeconds;
        this.typeOfExercise = typeOfExercise;
        this.intensity = intensity;
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
}
