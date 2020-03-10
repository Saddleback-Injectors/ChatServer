package edu.saddleback.cs4b.Backend;

import java.io.Serializable;

public class Packet {
    private String type;
    private Serializable data;

    public Packet(String type, Serializable data) {
        setType(type);
        setData(data);
    }

    private void setType(String type) {
        this.type = type;
    }

    private void setData(Serializable data) {
        this.data = data;
    }

    public String getType() { return type; }
    public Serializable getData() { return data; }
}
