package edu.saddleback.cs4b.Backend;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * This class will deligate messages from the client
 * to the server
 */
public class ClientConnection implements Runnable {
    private Socket socket;
    private List<String> channels;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private ServerPublisher publisher;

    private void setupIOStreams(ObjectInputStream in, ObjectOutputStream out) {
        try {
            in = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
            out = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ClientConnection(Socket socket, ServerPublisher publisher) {
        this.socket = socket;
        this.channels = new ArrayList<>();
        setupIOStreams(this.in, this.out);
        this.publisher = publisher;
    }

    @Override
    public void run() {
        // listen for messages from client until disconnect
        // propagate the message to the publisher
        try {
            ObjectInputStream in = new ObjectInputStream(
                    new BufferedInputStream(socket.getInputStream()));
            Packet packet = (Packet)in.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
