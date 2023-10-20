package co.yorku.nutrifit.logs;

import java.util.Date;

public abstract class ILog {

    private Date date;

    public ILog(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }
}
