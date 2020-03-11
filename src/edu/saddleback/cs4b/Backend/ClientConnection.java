package edu.saddleback.cs4b.Backend;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
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
    // maybe a publisher here later

    public ClientConnection(Socket socket) {
        this.socket = socket;
        this.channels = new ArrayList<>();
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
