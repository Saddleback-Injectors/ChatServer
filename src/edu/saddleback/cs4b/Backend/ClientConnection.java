package edu.saddleback.cs4b.Backend;

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

        // the following is just testing multiple clients connected
        for (int i = 0; i < 5; ++i) {
            try {
                Thread.sleep(3500);
                System.out.println(socket.getInetAddress().getHostName());
            }catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
