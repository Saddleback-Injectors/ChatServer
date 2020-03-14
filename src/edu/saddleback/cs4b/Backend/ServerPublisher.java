package edu.saddleback.cs4b.Backend;

import java.util.List;

public class ServerPublisher {
    private List<ClientConnection> clients;
    ServerPublisher(List<ClientConnection> clients) {
        this.clients = clients;
    }
}
