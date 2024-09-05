package com.jr.tankbattle;

import com.jr.tankbattle.controller.StartScr;
import com.jr.tankbattle.scene.GameScene;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class Director {
    public static final int WIDTH = 1080, HEIGHT = 720;
    private static Director instance = new Director();
    private Stage stage;
    private GameScene gameScene = new GameScene();

    private Director() {}

    public static Director getInstance() {
        return instance;
    }

    public Stage getStage() {
        return stage;
    }

    public void init(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/com/jr/tankbattle/fxml/StartScr.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), WIDTH, HEIGHT);
        stage.setTitle("TankBattle");
        stage.getIcons().add(new Image(this.getClass().getResourceAsStream("/com/jr/tankbattle/img/logo.png")));
        stage.setScene(scene);
        stage.show();

        this.stage = stage;
    }

    public void toLoginScr() throws IOException {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("/com/jr/tankbattle/fxml/LoginScr.fxml")));
            stage.getScene().setRoot(root);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void toRegisterScr() throws IOException {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("/com/jr/tankbattle/fxml/RegisterScr.fxml")));
            stage.getScene().setRoot(root);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void toHomePage() throws IOException {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("/com/jr/tankbattle/fxml/HomePage.fxml")));
            stage.getScene().setRoot(root);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void toSetting(){
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("/com/jr/tankbattle/fxml/Setting.fxml")));
            stage.getScene().setRoot(root);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void toAccount(){
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("/com/jr/tankbattle/fxml/Account.fxml")));
            stage.getScene().setRoot(root);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void toGame(){
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("/com/jr/tankbattle/fxml/GameScene.fxml")));
            stage.getScene().setRoot(root);
            gameScene.init(stage);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
