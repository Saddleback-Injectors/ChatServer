package edu.saddleback.cs4b.Backend;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ChatServer {
    private static List<String> channels = new ArrayList<>();
    private static BlockingQueue<ClientConnection> clients = new LinkedBlockingQueue<>();
    private static int numClients = 0;

    public static void main(String[] args) {
        // will temporarily run the server, later done through
        // our Main class
    }
}
