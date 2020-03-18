package edu.saddleback.cs4b.Backend;

import edu.saddleback.cs4b.Backend.Messages.BaseMessage;
import edu.saddleback.cs4b.Backend.Messages.DisconnectMessage;
import edu.saddleback.cs4b.Backend.Messages.PicMessage;
import edu.saddleback.cs4b.Backend.Messages.TextMessage;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.concurrent.BlockingQueue;

public class ServerPublisher implements Runnable {
    private List<ClientConnection> clients;
    private BlockingQueue<Packet> messages;
    private boolean isRunning;

    ServerPublisher(List<ClientConnection> clients,
                    BlockingQueue<Packet> messages) {
        this.clients = clients;
        this.messages = messages;
        this.isRunning = true;
    }

    @Override
    public void run() {
        while (isRunning) {
            try {
                Packet curPacket = messages.take();
                BaseMessage msg = (BaseMessage)curPacket.getData();
                if (msg instanceof DisconnectMessage) {
                    ClientConnection cc = (ClientConnection)((DisconnectMessage) msg).getClient();
                    clients.remove(cc);
                } else {
                    distribute(curPacket);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void distribute(Packet packet) {
        ObjectOutputStream out = null;
        for (ClientConnection c : clients) {
            try {
                // need a better way to get the message for now temporary
                String channel = getChannel(packet);
                if (c != null && isSubscriber(c, channel)) {
                    out = c.getOutputStream();
                    out.writeObject(packet);
                    out.flush();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Todo how do we handle, exception, empty string...if no channel nothing
     * to check... you could add an interface called channable or something
     * like that
     */
    private String getChannel(Packet packet) {
        BaseMessage msg = (BaseMessage)packet.getData();
        if (msg instanceof TextMessage) {
            return ((TextMessage) msg).getChannel();
        } else if (msg instanceof PicMessage) {
            return ((PicMessage) msg).getChannel();
        }
        return "";
    }


    /**
     * for each client connected, checks if the client is a subscriber
     * to the channel in which the message is being sent too
     */
    private boolean isSubscriber(ClientConnection client, String channel) {
        // try to make this more efficient later
        // todo make sure this become a copy not a refereence
        List<String> channels = client.getChannelsListening();
        for (String c : channels) {
            if (c.equals(channel)) {
                return true;
            }
        }
        return false;
    }
}
