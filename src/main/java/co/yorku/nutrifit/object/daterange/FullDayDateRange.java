package co.yorku.nutrifit.object.daterange;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class FullDayDateRange extends DateRange {

    public FullDayDateRange() {
        this(System.currentTimeMillis());
    }

    public FullDayDateRange(Long millis) {
        super(new Date(millis), new Date(millis));

        getFrom().setHours(0);
        getFrom().setMinutes(0);
        getFrom().setSeconds(0);

        getTo().setHours(23);
        getTo().setMinutes(59);
        getTo().setSeconds(59);
    }
}
