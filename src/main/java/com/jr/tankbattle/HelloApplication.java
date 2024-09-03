package com.jr.tankbattle;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    int WIDTH = 1080;
    int HEIGHT = 720;
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/com/jr/tankbattle/fxml/StartScr.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), WIDTH, HEIGHT);
        stage.setTitle("TankBattle");
        stage.setScene(scene);
        stage.show();
    }

   public static void main(String[] args) {
        launch();
    }
}