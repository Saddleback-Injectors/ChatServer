package edu.saddleback.cs4b.Backend.Logging;

public class ChatServerLog {
    private static final Logger logger = new Logger();

    public static void log(String msg) {
        logger.log(msg);
    }
}
