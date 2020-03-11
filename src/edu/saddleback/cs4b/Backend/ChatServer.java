package edu.saddleback.cs4b.Backend;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ChatServer {
    private static List<String> channels = new ArrayList<>();
    private static BlockingQueue<ClientConnection> clients = new LinkedBlockingQueue<>();
    private static int port = 8080;

    public static void turnOn() {
        boolean isRunning = true;
        Socket client = null;
        try {
            ServerSocket server = new ServerSocket(8080);

            while (isRunning) {
                client = server.accept();
                System.out.println("Client Connected");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // will temporarily run the server, later done through
        // our Main class
        ChatServer.turnOn();
    }
}
