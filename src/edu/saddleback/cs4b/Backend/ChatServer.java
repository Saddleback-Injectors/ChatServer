package edu.saddleback.cs4b.Backend;

import edu.saddleback.cs4b.Backend.Logging.ServerLog;
import edu.saddleback.cs4b.Backend.Logging.LogToConsole;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
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
        this.publisher = new ServerPublisher(clients, messages);
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
                clientConnect = new ClientConnection(client, messages);
                clients.add(clientConnect);
                worker = new Thread(clientConnect);
                worker.start();
                logNewUsers();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void logNewUsers() {
        String time = LocalTime.now().getHour() + ":" +
                      LocalTime.now().getMinute() + ":" +
                      LocalTime.now().getSecond();
        ServerLog.log(time + " : new user connected");
    }

    public static void main(String[] args) {
        // will temporarily run the server, later done through
        // our Main class
        new Thread(()-> {
            LogToConsole consoleLog = new LogToConsole(ServerLog.getLogger());
        }).start();
        new ChatServer().turnOn();
    }
}
