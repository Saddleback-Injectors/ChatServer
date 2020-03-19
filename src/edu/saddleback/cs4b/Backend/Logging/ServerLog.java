package edu.saddleback.cs4b.Backend.Logging;

public class ServerLog {
    private static final Logger logger = new Logger();

    public synchronized static void log(LogEvent event) {
        logger.log(event);
    }

    public static Logger getLogger() { return logger; }
}
