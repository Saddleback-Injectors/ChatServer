package edu.saddleback.cs4b.Backend.Logging;

public class LogToConsole implements LogObserver, LogPrintable {
    private LogSubject logger;
    private String logMessage;

    public LogToConsole(LogSubject subject) {
        this.logger = subject;
        logger.registerObserver(this);
        this.logMessage = "";
    }

    @Override
    public void printLog() {
        System.out.println(logMessage);
    }

    @Override
    public void update(String msg) {
        logMessage = msg;
        printLog();
    }
}
