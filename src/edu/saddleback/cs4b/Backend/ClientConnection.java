package edu.saddleback.cs4b.Backend;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * This class will wrap necessary information about a client
 * necessary to a connection
 */
public class ClientConnection {
    private Socket socket;
    private List<String> channels;
    // maybe a publisher here later

    public ClientConnection(Socket socket) {
        this.socket = socket;
        this.channels = new ArrayList<>();
    }
}
