package co.yorku.nutrifit.object.daterange;

import java.util.Date;

/*

    This class is used for date ranges

 */
public class DateRange {

    private Date from;
    private Date to;
    public DateRange(Date from, Date to) {
        this.from = from;
        this.to = to;
    }

    /*
        Getter Methods
     */

    public Date getFrom() {
        return from;
    }

    public Date getTo() {
        return to;
    }
}
