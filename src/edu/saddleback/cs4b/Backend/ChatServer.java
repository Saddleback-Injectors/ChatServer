package edu.saddleback.cs4b.Backend;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ChatServer {
    private final List<String> channels;
    private final BlockingQueue<Packet> messages;
    private final List<ClientConnection> clients;
    private final ServerPublisher publisher;
    private final int port = 8000;

    public ChatServer() {
        this.channels = new ArrayList<>();
        this.messages = new LinkedBlockingQueue<>();
        this.clients  = new ArrayList<>();
        this.publisher = new ServerPublisher(clients);
        Thread pubThread = new Thread(publisher);
        pubThread.start();
    }

    public void turnOn() {
        boolean isRunning = true;
        Socket client = null;
        ClientConnection clientConnect = null;
        Thread worker = null;
        try {
            ServerSocket server = new ServerSocket(port);
            while (isRunning) {
                client = server.accept();
                clientConnect = new ClientConnection(client, publisher);
                worker = new Thread(clientConnect);
                worker.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // will temporarily run the server, later done through
        // our Main class
    }
}
