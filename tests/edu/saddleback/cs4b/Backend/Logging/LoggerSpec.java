package edu.saddleback.cs4b.Backend.Logging;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class LoggerSpec {
    private Logger logger;
    private final PrintStream systemOut = System.out;
    private final ByteArrayOutputStream consoleOut = new ByteArrayOutputStream();

    @BeforeEach
    void init() {
        logger = new Logger();
    }

    @Test
    @DisplayName("New Log should have empty message")
    void newLogHasEmptyMessage() {
        assertEquals("", logger.getMessage());
    }

    @Test
    @DisplayName("New Log should have no observers")
    void newLogHasNoObservers() {
        assertEquals(0, logger.getObservers().size());
    }

    @Test
    @DisplayName("Logger has 1 observer when observer subscribes")
    void logHasOneObserverWhenObserverSubscribes() {
        LogToConsole observer = new LogToConsole(logger);
        assertEquals(1, logger.getObservers().size());
    }

    @Test
    @DisplayName("Logger has message when called to log")
    void loggerHasMessageWhenCallToLog() {
        logger.log("test");
        assertEquals("test", logger.getMessage());
    }

    @Test
    @DisplayName("Observers notified when log is called")
    void observersNotifiedOnCallToLog() {
        changePrintStream();
        LogToConsole observer = new LogToConsole(logger);
        logger.log("test");
        String str = consoleOut.toString();
        assertEquals("test\n", str);
        restorePrintStream();
    }

    private void changePrintStream() {
        System.setOut(new PrintStream(consoleOut));
    }

    private void restorePrintStream() {
        System.setOut(systemOut);
    }
}