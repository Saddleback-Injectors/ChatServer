package edu.saddleback.cs4b.Backend.Logging;

import java.util.ArrayList;
import java.util.List;

public class Logger implements LogSubject {
    private String message;
    private List<LogObserver> observers;

    public Logger() {
        this.message = "";
        this.observers = new ArrayList<>();
    }

    @Override
    public void registerObserver(LogObserver o) {
        observers.add(o);
    }

    @Override
    public void removeObserver(LogObserver o) {
        observers.remove(o);
    }

    @Override
    public void notifyObservers() {
        for (LogObserver o : observers) {
            o.update(message);
        }
    }

    public synchronized void log(String msg) {
        message = msg;
        notifyObservers();
    }

    List<LogObserver> getObservers() { return observers; }
    String getMessage() { return message; }
}
