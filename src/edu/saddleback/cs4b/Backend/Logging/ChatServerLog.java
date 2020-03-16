package edu.saddleback.cs4b.Backend.Logging;

public class ChatServerLog {
    private static final Logger logger = new Logger();

    public synchronized static void log(String msg) {
        logger.log(msg);
    }

    public static Logger getLogger() { return logger; }
}
