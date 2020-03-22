package edu.saddleback.cs4b.Backend;

import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ServerPublisherTest {
    @BeforeEach
    void init() {
        Set<String> channels = new HashSet<>();
        List<ClientConnection> clientConnections = new ArrayList<>();
        ClientConnection cc = new ClientConnection(channels);
        ServerPublisher pub = new ServerPublisher(clientConnections, null);
    }

    

}