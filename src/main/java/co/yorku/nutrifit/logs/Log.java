package co.yorku.nutrifit.logs;

import java.util.Date;

public abstract class Log {

    private Date date;

    public Log(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }
}
