package edu.saddleback.cs4b.UI;

import edu.saddleback.cs4b.Backend.ChatServer;
import edu.saddleback.cs4b.Backend.Logging.*;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class ServerScreenController implements LogObserver {
    private LogSubject logger = ServerLog.getLogger();
    private Thread serverThread = null;

    @FXML
    private Label portField;

    @FXML
    private Label hostField;

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
        } else if (event.getEventType().equals(LogEnum.HOST.getType())) {
            Platform.runLater(()->hostField.setText(event.getMessage()));
        } else if (event.getEventType().equals(LogEnum.PORT.getType())) {
            Platform.runLater(()->portField.setText(event.getMessage()));
        }
    }

    @FXML
    void onStartUp() {
        if (serverThread == null) {
            serverThread = new Thread(()-> {
                new ChatServer().turnOn();
            });
            serverThread.start();
        }
    }

    @FXML
    void onShutDown(Event e) {

    }

}
