package co.yorku.nutrifit.object.daterange;

import java.util.Date;
import java.util.concurrent.TimeUnit;
/*

    This class is used for date ranges
    It extends DateRange and automatically sets the start/end time of the range

 */
public class FullDayDateRange extends DateRange {

    public FullDayDateRange() {
        this(System.currentTimeMillis());
    }

    public FullDayDateRange(Long millis) {
        super(new Date(millis), new Date(millis));

        // Set the fromDate time to the start of the day
        getFrom().setHours(0);
        getFrom().setMinutes(0);
        getFrom().setSeconds(0);

        // Set the toDate time to the end of the day
        getTo().setHours(23);
        getTo().setMinutes(59);
        getTo().setSeconds(59);
    }
}
