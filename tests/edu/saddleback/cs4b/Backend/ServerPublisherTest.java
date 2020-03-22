package edu.saddleback.cs4b.Backend;

import edu.saddleback.cs4b.Backend.Messages.DisconnectMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ServerPublisherTest {
    Set<String> channels;
    List<ClientConnection> clientConnections;
    ClientConnection cc;
    ServerPublisher pub;

    @BeforeEach
    void init() {
        channels = new HashSet<>();
        clientConnections = new ArrayList<>();
        cc = new ClientConnection(channels);
        pub = new ServerPublisher(clientConnections, null);
        clientConnections.add(cc);
    }

    @Test
    void disconnectMethodHasNoChannel() {
        String ch = pub.getChannel(new Packet("", new DisconnectMessage( "" , cc)));
        assertEquals("", ch);
    }

    

}