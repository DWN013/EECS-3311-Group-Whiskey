package co.yorku.nutrifit.event;

import java.util.Date;

public interface IListener {

    void onDateRangeUpdate(String type, Date newFromDate, Date newToDate);

}
