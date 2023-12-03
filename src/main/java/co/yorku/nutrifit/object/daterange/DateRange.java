package co.yorku.nutrifit.object.daterange;

import java.util.Date;

public class DateRange {

    private Date from;
    private Date to;
    public DateRange(Date from, Date to) {
        this.from = from;
        this.to = to;
    }

    public Date getFrom() {
        return from;
    }

    public Date getTo() {
        return to;
    }
}
