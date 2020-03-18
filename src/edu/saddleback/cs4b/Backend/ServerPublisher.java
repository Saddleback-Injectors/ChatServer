package edu.saddleback.cs4b.Backend;

import edu.saddleback.cs4b.Backend.Messages.BaseMessage;
import edu.saddleback.cs4b.Backend.Messages.DisconnectMessage;
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

    /**
     *Todo is it ineffective to create new output streams everytime?
     * was not working the old way
     */
    private void distribute(Packet packet) {
        ObjectOutputStream out = null;
        for (ClientConnection c : clients) {
            try {
                // need a better way to get the message for now temporary
                String channel = ((TextMessage)packet.getData()).getChannel();
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
