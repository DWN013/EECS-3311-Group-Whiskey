package co.yorku.nutrifit.event;

import co.yorku.nutrifit.object.daterange.DateRange;
import com.google.common.collect.Lists;

import java.util.*;

/*

    EventManager class which holds a list of listeners./
    This is specifically used for our Visualizers so that they can be notified
    of an update  when the date/range the user has inputted has changed.
    This uses the "Observer" design pattern.

 */
public class EventManager {

    // List of listeners
    private List<IListener> listeners = Lists.newArrayList();

    public void subscribe(IListener listener) { // This method is used to subscribe a listener
        listeners.add(listener);
    }

    public void unsubscribe(IListener listener) { // This method is used to unsubscribe a listener
        listeners.remove(listener);
    }

    // This method is used to notify all the subscribed listeners of an event
    public void notify(String type, String expandData, DateRange dateRange) {
        for (IListener listener : listeners) { // Loop through every listener
            // Notify the listener of some event and pass in all the data
            listener.onDateRangeUpdate(type, expandData, dateRange);
        }
    }
}