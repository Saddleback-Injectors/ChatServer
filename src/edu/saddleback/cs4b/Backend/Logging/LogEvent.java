package edu.saddleback.cs4b.Backend.Logging;

public class LogEvent {
    private LogEnum eventType;
    private String message;

    public LogEvent(LogEnum eventType, String message) {
        this.eventType = eventType;
        this.message = message;
    }

    public String getEventType() { return eventType.getType(); }
    public String getMessage() { return message; }
 }
