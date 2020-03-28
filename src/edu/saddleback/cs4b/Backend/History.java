package edu.saddleback.cs4b.Backend;

import edu.saddleback.cs4b.Backend.Interfaces.Requestable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Maintains a history of chat exchanges between multiple users on channel
 *   - authorized users can have a copy of the log or picture but shouldn't
 *     be able to modify existing data. They can add to existing data on
 *     the original object
 *
 *     todo come back and address if a History object should be cloneable
 */
public class History implements Requestable, Cloneable {
    private List<String> textLog;
    private byte[] fileData;

    private History(List<String> textLog, byte[] fileData) {
        this.textLog = textLog;
        this.fileData = fileData;
    }

    public History() {
        this (new ArrayList<>(), null);
    }

    public void logText(String text) {
        textLog.add(text);
    }

    public void addFileData(byte[] fileData) {
        // todo should we just make a copy instead?? come back to
        this.fileData = fileData;
    }

    /**
     * @return text log that cannot be modified, to add, add directly
     * via the object, you cannot remove
     */
    public List<String> getTextLog() {
        return Collections.unmodifiableList(textLog);
    }

    /**
     * @return copy of the data being stored
     */
    public byte[] getFileData() {
        return Arrays.copyOf(fileData, fileData.length);
    }

    @Override
    protected Object clone() {
        List<String> copyText = new ArrayList<>();
        for (String s : textLog) {
            copyText.add(s);
        }

        byte[] copyImage = null;
        if (fileData != null) {
            copyImage = new byte[fileData.length];
            for (int i = 0; i < fileData.length; ++i) {
                copyImage[i] = fileData[i];
            }
        }
        return new History(copyText, copyImage);
    }
}
