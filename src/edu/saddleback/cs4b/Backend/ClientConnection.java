package edu.saddleback.cs4b.Backend;

import edu.saddleback.cs4b.Backend.Messages.RegMessage;
import edu.saddleback.cs4b.Backend.Messages.TextMessage;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

/**
 * This class will deligate messages from the client
 * to the server
 */
public class ClientConnection implements Runnable {
    private Socket socket;
    private List<String> channels;
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
        this.channels = null;
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
                // move this inside of delegate message
                //messages.add(packet);
                delegateMsg(packet);
            }
        } catch (EOFException eof) {
            connected = false;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void delegateMsg(Packet packet) {
        // Message msg = (Message)packet.getData()
        Serializable data = packet.getData();
        if (data instanceof RegMessage) {
            register((RegMessage)data);
            // add a notification message since we don't
            // want another client to have direct access to registeration
            notifyAllChannels();
        } else if (data instanceof TextMessage) {
            messages.add(packet);
        }
    }

    private void notifyAllChannels() {
        // this is temporary for testing
        for (String c : channels) {
            String joinMsg = username + " has joined " + c;
            messages.add(new Packet("Text-Message", new TextMessage(c, joinMsg)));
        }
    }

    private void register(RegMessage message) {
        channels = message.getChannels();
        username = message.getUserName();
    }

   /**
    * TODO how to prevent multiple threads trying to write to output stream
    */
    public ObjectOutputStream getOutputStream() {
        return out;
    }

    public InputStream getInputSteam() {
        return in;
    }

    /**
     * TODO return a copy of the channels listening but not direct access,
     * need to make this immutable
     */
    public List<String> getChannelsListening() { return channels; }
}
