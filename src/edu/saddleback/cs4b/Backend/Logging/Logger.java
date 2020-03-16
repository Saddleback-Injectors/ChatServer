package edu.saddleback.cs4b.Backend.Logging;

import java.util.ArrayList;
import java.util.List;

public class Logger implements LogSubject {
    private List<String> errorMessages;
    private List<LogObserver> observers;

    public Logger() {
        this.errorMessages = new ArrayList<>();
        this.observers = new ArrayList<>();
    }

    @Override
    public void registerObserver(LogObserver o) {

    }

    @Override
    public void removeObserver(LogObserver o) {

    }

    @Override
    public void notifyObservers() {

    }
}
