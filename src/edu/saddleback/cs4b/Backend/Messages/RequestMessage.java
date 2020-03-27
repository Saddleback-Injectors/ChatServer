package edu.saddleback.cs4b.Backend.Messages;

import edu.saddleback.cs4b.Backend.History;

public class RequestMessage extends BaseMessage {
    private RequestType type;
    private String channel;
    private History channelHistory;

    private RequestMessage(String sender, String type,
                           RequestType requestType,
                           String channel, History channelHistory) {
        super(sender, type);
        this.type = requestType;
        this.channel = channel;
        this.channelHistory = channelHistory;
    }

    public RequestMessage(RequestType requestType, String channel) {
        this("", requestType.getType(), requestType, channel, null);
    }

    public RequestMessage(String sender, RequestType requestType, String channel) {
        this(sender, requestType.getType(), requestType, channel, null);
    }
}
