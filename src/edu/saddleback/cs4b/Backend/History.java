package edu.saddleback.cs4b.Backend;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Maintains a history of chat exchanges between multiple users on channel
 *   - authorized users can have a copy of the log or picture but shouldn't
 *     be able to modify existing data. They can add to existing data on
 *     the original object
 *
 *     todo come back and address if a History object should be cloneable
 */
public class History implements Serializable {
    private List<String> textLog;
    private byte[] fileData;

    private History(ArrayList<String> textLog, byte[] fileData) {
        this.textLog = textLog;
        this.fileData = fileData;
    }

    History() {
       this (new ArrayList<>(), null);
    }

    public void logText(String text) {
        textLog.add(text);
    }

    public void addFileData(byte[] fileData) {
        // todo should we just make a copy instead?? come back to
        this.fileData = fileData;
    }



}
