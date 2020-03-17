package edu.saddleback.cs4b.Backend.Messages;

public class DisconnectMessage extends BaseMessage
{
    public DisconnectMessage(String sender)
    {
        super(sender, "Dis-Msg");
    }
}
