package edu.saddleback.cs4b.Backend;

import edu.saddleback.cs4b.Backend.Messages.TextMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.Serializable;

import static org.junit.jupiter.api.Assertions.*;

class PacketSpec {
    @Test
    @DisplayName("Packet should return type as string")
    void shouldReturnTypeAsString() {
        Packet packet = new Packet("Test", new TextMessage("", "", ""));
        assertEquals("Test", packet.getType());
    }

    @Test
    @DisplayName("Packet should return data as serializable")
    void shouldReturnDataAsSerializable() {
        Packet packet = new Packet("Test", new TextMessage("", "", ""));
        assertTrue(packet.getData() instanceof Serializable);
    }
}