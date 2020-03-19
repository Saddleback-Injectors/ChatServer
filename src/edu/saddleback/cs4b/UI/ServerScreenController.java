package edu.saddleback.cs4b.UI;

import edu.saddleback.cs4b.Backend.Logging.LogObserver;
import edu.saddleback.cs4b.Backend.Logging.LogSubject;
import edu.saddleback.cs4b.Backend.Logging.ServerLog;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public class ServerScreenController implements LogObserver {
    private LogSubject logger = ServerLog.getLogger();

    @FXML
    private TextArea logScreen;

    public ServerScreenController() {
        logger.registerObserver(this);
    }

    @Override
    public void update(String msg) {
        logScreen.appendText(msg + "\n");
    }
}
