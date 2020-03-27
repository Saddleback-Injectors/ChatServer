package edu.saddleback.cs4b.Backend;

import edu.saddleback.cs4b.Backend.Messages.*;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.BlockingQueue;

public class ServerPublisher implements Runnable {
    private List<ClientConnection> clients;
    private BlockingQueue<Packet> messages;
    private final Map<String, History> historyMap;
    private boolean isRunning;

    ServerPublisher(List<ClientConnection> clients,
                    BlockingQueue<Packet> messages,
                    Map<String, History> historyMap) {
        this.clients = clients;
        this.messages = messages;
        this.historyMap = historyMap;
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
                    ObjectOutputStream os = cc.getOutputStream();
                    os.writeObject(new Packet("Disconnect", new DisconnectMessage(null)));
                    os.flush();
                } else if (msg instanceof ServerTermination) {
                    isRunning = false;
                } else {
                    String msgChannel = getChannel(curPacket);
                    if (!historyMap.containsKey(msgChannel)) {
                        historyMap.put(msgChannel, new History());
                    }
                    logHistory(msgChannel, curPacket);
                    // right now this method only invoked for
                    // picture and text messages
                    distribute(curPacket);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
    }

    private void logHistory(String channel, Packet curPacket) {
        if (curPacket.getData() instanceof TextMessage) {
            TextMessage txt = (TextMessage)curPacket.getData();
            historyMap.get(channel).logText(txt.getMessage());
        } else if (curPacket.getData() instanceof PicMessage) {
            PicMessage pic = (PicMessage)curPacket.getData();
            historyMap.get(channel).addFileData(pic.getImg());
        }
    }

    private void distribute(Packet packet) {
        ObjectOutputStream out = null;
        String channel = getChannel(packet);
        for (ClientConnection c : clients) {
            try {
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
    String getChannel(Packet packet) {
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
    boolean isSubscriber(ClientConnection client, String channel) {
        // try to make this more efficient later
        // todo make sure this become a copy not a refereence
        Set<String> channels = client.getChannelsListening();
        for (String c : channels) {
            if (c.equals(channel)) {
                return true;
            }
        }
        return false;
    }
}
