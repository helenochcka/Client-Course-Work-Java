package com.example.clientcourseworkjava;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.IOException;

public class Client extends Application {
    public static Logger logger = LogManager.getLogger();

    public static String serverIP = "127.0.0.1";
    public static int serverPortNumber = 8000;

    public static final char EXTERNAL_SEPARATOR = (char) 35; // #
    public static final char INTERNAL_SEPARATOR = (char) 47; // /
    public static final char SUBINTERNAL_SEPARATOR = (char) 59; //; ;

    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("view.fxml"));
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.setTitle("Client-Course-Work-Java");
        stage.setResizable(false);
        stage.show();

        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                Platform.exit();
                System.exit(0);
            }
        });
    }

    public static void main(String[] args) {
        launch();
    }
}