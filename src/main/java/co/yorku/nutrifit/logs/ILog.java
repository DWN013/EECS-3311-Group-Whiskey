package co.yorku.nutrifit.logs;

import java.util.Date;

// Abstract class ILog
public abstract class ILog {

    // Private instance variable to store the date
    private Date date;

    // Constructor initializing an ILog object with a date
    public ILog(Date date) {
        this.date = date; // Assign the date
    }

    // Getter method to retrieve the date
    public Date getDate() {
        return date;
    }
}

