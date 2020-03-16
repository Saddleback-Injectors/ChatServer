package edu.saddleback.cs4b.Backend;

import java.io.BufferedOutputStream;
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

    /**
     *Todo is it ineffective to create new output streams everytime?
     * was not working the old way
     */
    private void distribute(Packet packet) {
        ObjectOutputStream out = null;
        for (ClientConnection c : clients) {
            try {
                out = new ObjectOutputStream(new BufferedOutputStream(c.getOutputStream()));
                out.writeObject(packet);
                out.flush();
                out.reset();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
