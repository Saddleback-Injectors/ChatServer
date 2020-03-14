package edu.saddleback.cs4b.Backend;

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
    private final List<String> channels;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private BlockingQueue<Packet> messages;

    private void setupIOStreams(ObjectInputStream in, ObjectOutputStream out) {
        try {
            in = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
            out = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ClientConnection(Socket socket, BlockingQueue<Packet> messages) {
        this.socket = socket;
        this.channels = new ArrayList<>();
        setupIOStreams(this.in, this.out);
        this.messages = messages;
    }

    @Override
    public void run() {
        // listen for messages from client until disconnect
        // propagate the message to the publisher
        try {
            ObjectInputStream in = new ObjectInputStream(
                    new BufferedInputStream(socket.getInputStream()));
            Packet packet = (Packet)in.readObject();
            messages.add(packet);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * TODO how to prevent multiple threads trying to write to output stream
     */
    public ObjectOutputStream getOutputStream() { return out; }
    public ObjectInputStream getInputSteam() { return in; }
    public List<String> getChannelsListening() { return channels; }
}
