package edu.saddleback.cs4b.Backend;

import edu.saddleback.cs4b.Backend.Messages.DisconnectMessage;
import edu.saddleback.cs4b.Backend.Messages.PicMessage;
import edu.saddleback.cs4b.Backend.Messages.RegMessage;
import edu.saddleback.cs4b.Backend.Messages.TextMessage;
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
    void disconnectMessageHasNoChannel() {
        String ch = pub.getChannel(new Packet("", new DisconnectMessage( "" , cc)));
        assertEquals("", ch);
    }

    @Test
    void regMessageHasNoChannel() {
        String ch = pub.getChannel(new Packet("", new RegMessage( "" , "", null)));
        assertEquals("", ch);
    }

    @Test
    void messagesThatSupportChannelsWillReportChannel() {
        String ch = pub.getChannel(new Packet("", new TextMessage("", "A", "")));
        String ch2 = pub.getChannel(new Packet("", new PicMessage("",  null, "B")));
        assertEquals("A", ch);
        assertEquals("B", ch2);
    }

    @Test
    void clientWhoSubscribeToAChannelComesBackTrue() {
        channels.add("A");
        channels.add("C");
        assertTrue(pub.isSubscriber(cc, "A"));
        assertTrue(pub.isSubscriber(cc,"C"));
    }

    @Test
    void clientWhoIsNotSubscribedWillComeBackFalse() {
        assertFalse(pub.isSubscriber(cc, "A"));
    }
}