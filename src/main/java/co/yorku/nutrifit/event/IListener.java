package co.yorku.nutrifit.event;

import co.yorku.nutrifit.object.daterange.DateRange;

import java.util.Date;

/*

    This is a IListener interface.
    It is an interface that classes implement
    when they want to subscribe to some event.

 */
public interface IListener {

    // This method listens for a date range update and is implemented in specific classes that want to
    // listen for an event
    void onDateRangeUpdate(String type, String expandData, DateRange dateRange);

}
