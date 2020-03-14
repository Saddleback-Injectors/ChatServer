package edu.saddleback.cs4b.Backend;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.concurrent.BlockingQueue;

public class ServerPublisher implements Runnable {
    private List<ClientConnection> clients;
    private BlockingQueue<Packet> messages;
    private boolean isRunning;

    ServerPublisher(List<ClientConnection> clients,
                    BlockingQueue<Packet> messages) {
        this.clients = clients;
        this.messages = messages;
        this.isRunning = true;
    }

    @Override
    public void run() {
        while (isRunning) {
            try {
                Packet curPacket = messages.take();
                distribute(curPacket);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void distribute(Packet packet) {
        ObjectOutputStream out = null;
        for (ClientConnection c : clients) {
            out = c.getOutputStream();
            try {
                out.writeObject(packet);
                out.flush();
                out.reset();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
