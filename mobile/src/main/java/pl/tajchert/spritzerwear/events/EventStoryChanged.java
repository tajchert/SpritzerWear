package pl.tajchert.spritzerwear.events;


public class EventStoryChanged {
    public String title;
    public enum EventType {Deleted, Test};
    public EventType eventType;

    public EventStoryChanged(String title, EventType eventType) {
        this.title = title;
        this.eventType = eventType;
    }
}
