package edu.saddleback.cs4b.Backend;

import edu.saddleback.cs4b.Backend.Logging.LogEnum;
import edu.saddleback.cs4b.Backend.Logging.LogEvent;
import edu.saddleback.cs4b.Backend.Logging.ServerLog;
import edu.saddleback.cs4b.Backend.Logging.LogToConsole;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
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
    private ServerSocket server;

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
            server = new ServerSocket(port);
            ServerLog.log(new LogEvent(LogEnum.PORT, Integer.toString(port)));
            ServerLog.log(new LogEvent(LogEnum.HOST, server.getInetAddress().toString()));
            while (isRunning) {
                client = server.accept();
                clientConnect = new ClientConnection(client, messages);
                clients.add(clientConnect);
                worker = new Thread(clientConnect);
                worker.start();
                logNewUsers();
            }
        } catch (SocketException e1) {
            isRunning = false;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void logNewUsers() {
        String time = LocalTime.now().getHour() + ":" +
                      LocalTime.now().getMinute() + ":" +
                      LocalTime.now().getSecond();
        String message = time + " : new user connected";
        ServerLog.log(new LogEvent(LogEnum.EVENT_LOG, message));
    }

    public void terminate() {
        try {
            server.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
