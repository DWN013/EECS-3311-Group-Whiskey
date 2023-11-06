package co.yorku.nutrifit.event;

import java.util.*;

public class EventManager {
    private List<IListener> listeners = new ArrayList<>();

    public void subscribe(IListener listener) {
        listeners.add(listener);
    }

    public void unsubscribe(IListener listener) {
        listeners.remove(listener);
    }

    public void notify(String type, Date newFromDate, Date newToDate) {
        for (IListener listener : listeners) {
            listener.onDateRangeUpdate(type, newFromDate, newToDate);
        }
    }
}