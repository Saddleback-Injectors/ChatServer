package edu.saddleback.cs4b.Backend.Logging;

public enum LogEnum {
    EVENT_LOG("Event-Log"),
    USERS("Users"),
    HOST("HostIP"),
    PORT("Port#");

    private String type;
    private LogEnum(String type) {
        this.type = type;
    }

    public String getType() { return type; }
}
