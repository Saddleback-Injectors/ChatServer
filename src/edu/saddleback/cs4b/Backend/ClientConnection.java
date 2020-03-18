package edu.saddleback.cs4b.Backend;

import edu.saddleback.cs4b.Backend.Logging.ServerLog;
import edu.saddleback.cs4b.Backend.Messages.*;

import java.io.*;
import java.net.Socket;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.BlockingQueue;

/**
 * This class will delegate messages from the client
 * to the server
 */
public class ClientConnection implements Runnable {
    private Socket socket;
    private Set<String> channels;
    private BlockingQueue<Packet> messages;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private String username;

    private void createIOStreams() {
        try {
            this.in = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
            this.out = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ClientConnection(Socket socket, BlockingQueue<Packet> messages) {
        this.socket = socket;
        this.channels = new HashSet<>();
        this.messages = messages;
        this.username = "";
        createIOStreams();
    }

    @Override
    public void run() {
        // listen for messages from client until disconnect
        // propagate the message to the publisher
        boolean connected = true;
        try {
            //ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
            while (connected) {
                Packet packet = (Packet) in.readObject();
                delegateMsg(packet);
            }
        } catch (EOFException eof) {
            connected = false;
            logDisconnection();
            BaseMessage disconnect = new DisconnectMessage(username, this);
            delegateMsg(new Packet(disconnect.getType(), disconnect));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void logDisconnection() {
        String time = LocalTime.now().getHour() + ":" +
                LocalTime.now().getMinute() + ":" +
                LocalTime.now().getSecond();
        ServerLog.log(time + " : user has left");
    }

    private void delegateMsg(Packet packet) {
        // Message msg = (Message)packet.getData()
        Serializable data = packet.getData();
        if (data instanceof RegMessage) {
            register((RegMessage)data);
            // add a notification message since we don't
            // want another client to have direct access to registeration
            notifyAllChannels(username + " has joined");
        } else if (data instanceof TextMessage ||
                   data instanceof PicMessage) {
            messages.add(packet);
        } else if (data instanceof DisconnectMessage) {
            messages.add(packet);
            notifyAllChannels(username + " has left");
        } else if (data instanceof UpdateMessage) {
            // check the diff's and send out necessary notifications
            updateAndNotify((UpdateMessage)data);
        }
    }

    private void updateAndNotify(UpdateMessage msg) {
        List<String> updatedChannels = msg.getUpdatedChannels();
        for (String chan : updatedChannels) {
            if (!channels.contains(chan)) {
                // a channel has been added
                String joinMsg = username + " has joined " + chan;
                channels.add(chan);
                messages.add(new Packet("Text-Message", new TextMessage(username, chan, joinMsg)));
            }
        }

        // O(n^2) until we change channels to a set
        Iterator<String> iterator = channels.iterator();
        while (iterator.hasNext()) {
            String chan = iterator.next();
            if (!updatedChannels.contains(chan)) {
                String leaveMsg = username + " has left " + chan;
                iterator.remove();
                messages.add(new Packet("Text-Message", new TextMessage(username, chan, leaveMsg)));
            }
        }
    }

    private void notifyAllChannels(String message) {
        for (String c : channels) {
            String joinMsg = message + " " + c;
            messages.add(new Packet("Text-Message", new TextMessage(username, c, joinMsg)));
        }
    }

    private void register(RegMessage message) {
        channels.addAll(message.getChannels());
        username = message.getUserName();
    }

   /**
    * TODO how to prevent multiple threads trying to write to output stream
    */
    public ObjectOutputStream getOutputStream() { return out; }

    public InputStream getInputSteam() { return in; }

    /**
     * TODO return a copy of the channels listening but not direct access,
     * need to make this immutable
     */
    public Set<String> getChannelsListening() { return channels; }
}
