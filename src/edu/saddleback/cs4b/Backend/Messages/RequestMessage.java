package edu.saddleback.cs4b.Backend.Messages;

import edu.saddleback.cs4b.Backend.History;
import edu.saddleback.cs4b.Backend.Interfaces.Requestable;

public class RequestMessage extends BaseMessage {
    private RequestType type;
    private String channel;
    private Requestable requestable;

    private RequestMessage(String sender, String type,
                           RequestType requestType,
                           String channel, Requestable requestable) {
        super(sender, type);
        this.type = requestType;
        this.channel = channel;
        this.requestable = requestable;
    }

    public RequestMessage(RequestType requestType, String channel) {
        this("", requestType.getType(), requestType, channel, null);
    }

    public RequestMessage(String sender, RequestType requestType, String channel) {
        this(sender, requestType.getType(), requestType, channel, null);
    }

    public RequestType getRequestType() { return type; }
    public String getChannel() { return channel; }
    public Requestable getRequestable() { return requestable; }
}
