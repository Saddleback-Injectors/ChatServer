package edu.saddleback.cs4b.Backend.Logging;

public interface LogSubject {
    void registerObserver(LogObserver o);
    void removeObserver(LogObserver o);
    void notifyObservers();
}
