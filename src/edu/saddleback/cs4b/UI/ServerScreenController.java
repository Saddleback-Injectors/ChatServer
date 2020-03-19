package edu.saddleback.cs4b.UI;

import edu.saddleback.cs4b.Backend.Logging.*;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public class ServerScreenController implements LogObserver {
    private LogSubject logger = ServerLog.getLogger();

    @FXML
    private TextArea userConnection;

    @FXML
    private TextArea logScreen;

    public ServerScreenController() {
        logger.registerObserver(this);
    }

    @Override
    public void update(LogEvent event) {
        if (event.getEventType().equals(LogEnum.EVENT_LOG.getType())) {
            logScreen.appendText(event.getMessage() + "\n");
        } else if (event.getEventType().equals(LogEnum.USERS.getType())) {
            userConnection.appendText(event.getMessage() + "\n");
        }
    }
}
