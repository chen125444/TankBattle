package com.jr.tankbattle;

import com.jr.tankbattle.client.Client;
import com.jr.tankbattle.controller.Account;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Director.getInstance().init(stage);
    }

    @Override
    public void stop() throws Exception {
        Client client=new Client();
        client.logout(Account.uid);
    }

    public static void main(String[] args) {
        launch();
    }
}