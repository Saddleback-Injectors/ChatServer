package edu.saddleback.cs4b.Backend.Logging;

import java.util.ArrayList;
import java.util.List;

public class Logger implements LogSubject {
    private LogEvent event;
    private List<LogObserver> observers;

    public Logger() {
        this.event = null;
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
            o.update(event);
        }
    }

    public synchronized void log(LogEvent event) {
        this.event = event;
        notifyObservers();
    }

    List<LogObserver> getObservers() { return observers; }
    String getMessage() { return event.getMessage(); }
    LogEvent getEvent() { return event; }
}
