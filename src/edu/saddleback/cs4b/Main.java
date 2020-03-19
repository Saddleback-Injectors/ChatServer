package edu.saddleback.cs4b;

import edu.saddleback.cs4b.Backend.ChatServer;
import edu.saddleback.cs4b.Backend.Logging.LogToConsole;
import edu.saddleback.cs4b.Backend.Logging.ServerLog;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("UI/ServerScreen.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        new Thread(()-> {
            LogToConsole consoleLog = new LogToConsole(ServerLog.getLogger());
        }).start();
        launch(args);
    }
}
