package com.jr.tankbattle;

import com.jr.tankbattle.client.Client;
import com.jr.tankbattle.controller.Account;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    private Client client;

    @Override
    public void start(Stage stage) throws IOException {
        client = new Client();  // Initialize Client instance here
        Director.getInstance().init(stage);

        // Register a shutdown hook to ensure logout is called on program exit
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
              if (client != null&&Account.uid!=null) {
                   client.logout(Account.uid);
                }
            } catch (Exception e) {
                e.printStackTrace();  // Log any errors that occur during logout
            }
        }));
    }

    @Override
    public void stop() throws Exception {
        if (client != null&&Account.uid!=null) {
            client.logout(Account.uid);  // Call logout in stop method
        }
    }

    public static void main(String[] args) {
        launch();
    }
}
