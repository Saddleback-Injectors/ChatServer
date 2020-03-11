package edu.saddleback.cs4b.Backend;

import java.util.concurrent.BlockingQueue;

public class ServerPublisher {
    private BlockingQueue<ClientConnection> clients;
    ServerPublisher(BlockingQueue<ClientConnection> clients) {
        this.clients = clients;
    }
}
