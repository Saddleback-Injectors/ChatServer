package edu.saddleback.cs4b.Backend.Logging;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class LoggerSpec {
    private Logger logger = ServerLog.getLogger();
    private Logger tstLogger;
    private final PrintStream systemOut = System.out;
    private final ByteArrayOutputStream consoleOut = new ByteArrayOutputStream();

    @BeforeEach
    void init() {
        tstLogger = new Logger();
    }

    @Test
    @DisplayName("New Log should have empty message")
    void newLogHasEmptyMessage() {
        assertEquals(null, tstLogger.getEvent());
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
        ServerLog.log(new LogEvent(LogEnum.EVENT_LOG, "test"));
        assertEquals("test", logger.getMessage());
    }

    @Test
    @DisplayName("Observers notified when log is called")
    void observersNotifiedOnCallToLog() {
        changePrintStream();
        LogToConsole observer = new LogToConsole(tstLogger);
        tstLogger.log(new LogEvent(LogEnum.EVENT_LOG, "test"));
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

    @Test
    @DisplayName("Removing observer decreases observers by one")
    void removingObserversDecreaseObserversByOne() {
        LogToConsole console1 = new LogToConsole(logger);
        LogToConsole console2 = new LogToConsole(logger);
        int size = logger.getObservers().size();
        logger.removeObserver(console1);
        assertEquals(size - 1, logger.getObservers().size());
    }

    // add test to check for and prevent duplicate observers
}