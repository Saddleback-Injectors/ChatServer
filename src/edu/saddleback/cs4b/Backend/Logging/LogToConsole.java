package edu.saddleback.cs4b.Backend.Logging;

public class LogToConsole implements LogObserver, LogDisplayable {
    private LogSubject logger;
    private String logMessage;

    public LogToConsole(LogSubject subject) {
        this.logger = subject;
        logger.registerObserver(this);
        this.logMessage = "";
    }

    @Override
    public void display() {

    }

    @Override
    public void update(String error) {

    }
}
