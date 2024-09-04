package com.jr.tankbattle;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Director {
    public static final int WIDTH = 1080, HEIGHT = 720;
    private static Director instance = new Director();
    private Stage stage;
    //private GameScene gameScene = new GameScene();

    private Director() {}

    public static Director getInstance() {
        return instance;
    }

    public void init(Stage stage) throws IOException {
        /*AnchorPane root = new AnchorPane();
        Scene scene = new Scene(root , WIDTH, HEIGHT);
        stage.setTitle("坦克");
        stage.getIcons().add(new Image("image/logo.png"));
        stage.setResizable(false);
        stage.setScene(scene);
        stage.setWidth(WIDTH);
        stage.setHeight(HEIGHT);
        this.stage = stage;
        toIndex();
        stage.show();*/

        this.stage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/com/jr/tankbattle/fxml/StartScr.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), WIDTH, HEIGHT);
        stage.setTitle("TankBattle");
        stage.setScene(scene);
        stage.show();
    }

    public void toLoginScr() throws IOException {
//        try {
//            Parent root = FXMLLoader.load(Main.class.getResource("/com/jr/tankbattle/fxml/LoginScr.fxml"));
//            stage.getScene().setRoot(root);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/com/jr/tankbattle/fxml/LoginScr.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), WIDTH, HEIGHT);
        stage.setTitle("TankBattle");
        stage.setScene(scene);
        stage.show();
    }
}
