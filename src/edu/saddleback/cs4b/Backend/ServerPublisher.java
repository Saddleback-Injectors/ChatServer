package edu.saddleback.cs4b.Backend;

import java.util.List;
import java.util.concurrent.BlockingQueue;

public class ServerPublisher implements Runnable {
    private List<ClientConnection> clients;
    private BlockingQueue<Packet> messages;
    ServerPublisher(List<ClientConnection> clients,
                    BlockingQueue<Packet> messages) {
        this.clients = clients;
        this.messages = messages;
    }

    @Override
    public void run() {

    }
}
