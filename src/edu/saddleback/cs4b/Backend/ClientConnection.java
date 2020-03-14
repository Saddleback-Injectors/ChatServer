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

    private void setupIOStreams() {
        try {
            this.in = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
            this.out = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ClientConnection(Socket socket, BlockingQueue<Packet> messages) {
        this.socket = socket;
        this.channels = new ArrayList<>();
        setupIOStreams();
        this.messages = messages;
    }

    @Override
    public void run() {
        // listen for messages from client until disconnect
        // propagate the message to the publisher
        boolean connected = true;
        try {
            while (connected) {
                Packet packet = (Packet) in.readObject();
                System.out.println((String)packet.getData());
                messages.add(packet);
            }
        } catch (EOFException eof) {
            connected = false;
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
