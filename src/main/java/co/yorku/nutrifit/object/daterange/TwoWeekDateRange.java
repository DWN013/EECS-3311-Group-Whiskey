package co.yorku.nutrifit.object.daterange;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class TwoWeekDateRange extends DateRange {

    public TwoWeekDateRange() {
        this(System.currentTimeMillis());
    }

    public TwoWeekDateRange(Long millis) {
        super(new Date(millis - TimeUnit.DAYS.toMillis(14)), new Date(millis));

        getFrom().setHours(0);
        getFrom().setMinutes(0);
        getFrom().setSeconds(0);

        getTo().setHours(23);
        getTo().setMinutes(59);
        getTo().setSeconds(59);
    }
}
