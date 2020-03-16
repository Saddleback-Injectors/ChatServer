package edu.saddleback.cs4b.Backend.Logging;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LoggerSpec {
    private Logger logger;

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
}