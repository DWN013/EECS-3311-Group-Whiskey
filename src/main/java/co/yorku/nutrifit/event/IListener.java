package co.yorku.nutrifit.event;

import java.util.Date;

public interface IListener {

    void onDateRangeUpdate(Date newFromDate, Date newToDate);

}
