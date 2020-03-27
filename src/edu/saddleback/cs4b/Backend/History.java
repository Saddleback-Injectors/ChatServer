package edu.saddleback.cs4b.Backend;

import java.io.Serializable;
import java.util.ArrayList;

public class History implements Serializable {
    private ArrayList<String> msgLog;
    private byte[] recentPic;

    History() {
        this.msgLog = new ArrayList<>();
        this.recentPic = null;
    }
}
