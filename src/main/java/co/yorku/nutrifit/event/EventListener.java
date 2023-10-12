package co.yorku.nutrifit.event;

public interface EventListener {
    void onEvent(String eventType, String data);
}
