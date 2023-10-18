package co.yorku.nutrifit.event;

public interface IEventListener {
    void onEvent(String eventType, String data);
}
