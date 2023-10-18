package co.yorku.nutrifit.event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventManager {

    private Map<String, List<IEventListener>> listeners = new HashMap<>();

    public EventManager(String... eventTypes) {
        for (String operation : eventTypes) {
            this.listeners.put(operation, new ArrayList<>());
        }
    }

    public void subscribe(String eventType, IEventListener listener) {
        List<IEventListener> users = listeners.get(eventType);
        users.add(listener);
    }

    public void unsubscribe(String eventType, IEventListener listener) {
        List<IEventListener> users = listeners.get(eventType);
        users.remove(listener);
    }

    public void onEvent(String eventType, String data) {
        List<IEventListener> users = listeners.get(eventType);
        for (IEventListener listener : users) {
            listener.onEvent(eventType, data);
        }
    }

}
