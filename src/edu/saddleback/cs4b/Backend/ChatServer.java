package edu.saddleback.cs4b.Backend;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ChatServer {
    private List<String> channels;
    private BlockingQueue<Packet> messages;
    private List<ClientConnection> clients;
    private ServerPublisher publisher;
    private int port = 8000;

    private void runPublisher(ServerPublisher publisher) {

    }

    public ChatServer() {
        this.channels = new ArrayList<>();
        this.messages = new LinkedBlockingQueue<>();
        this.clients  = new ArrayList<>();
        this.publisher = new ServerPublisher(clients);
        runPublisher(publisher);
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
                clientConnect = new ClientConnection(client);

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
