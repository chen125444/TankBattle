package com.jr.tankbattle;

import com.jr.tankbattle.controller.StartScr;
import com.jr.tankbattle.scene.GameScene;
import com.jr.tankbattle.scene.OnlineGameScene;
import com.jr.tankbattle.scene.VsGameScene;
import com.jr.tankbattle.util.Sound;
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
    private OnlineGameScene onlineGameScene = new OnlineGameScene();
    private VsGameScene vsGameScene = new VsGameScene();


    private Director() {}

    public static Director getInstance() {
        return instance;
    }

    public Stage getStage() {
        return stage;
    }

    public void init(Stage stage) throws IOException { //初始化游戏
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/com/jr/tankbattle/fxml/StartScr.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), WIDTH, HEIGHT);
        stage.setTitle("TankBattle");
        stage.getIcons().add(new Image(this.getClass().getResourceAsStream("/com/jr/tankbattle/img/logo.png")));
        stage.setScene(scene);
        stage.show();

        this.stage = stage;
    }

    public void toStartScr(){ //跳转初始界面
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("/com/jr/tankbattle/fxml/StartScr.fxml")));
            stage.getScene().setRoot(root);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void toLoginScr() throws IOException { //跳转登录界面
      try {
          Parent root = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("/com/jr/tankbattle/fxml/LoginScr.fxml")));
           stage.getScene().setRoot(root);
       }
      catch (IOException e) {
           e.printStackTrace();
       }
    }

    public void toRegisterScr() throws IOException { //跳转注册界面
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("/com/jr/tankbattle/fxml/RegisterScr.fxml")));
            stage.getScene().setRoot(root);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void toHomePage() throws IOException { //跳转游戏主页面
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("/com/jr/tankbattle/fxml/HomePage.fxml")));
            stage.getScene().setRoot(root);
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        Sound.getInstance().BgmChg(1);//播放bgm
    }

    public void toSetting(){ //跳转设置界面
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("/com/jr/tankbattle/fxml/Setting.fxml")));
            stage.getScene().setRoot(root);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void toAccount(){ //跳转账号界面
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("/com/jr/tankbattle/fxml/Account.fxml")));
            stage.getScene().setRoot(root);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void toGameScene(){ //跳转单人游戏界面
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("/com/jr/tankbattle/fxml/GameScene.fxml")));
            stage.getScene().setRoot(root);
            gameScene.init(stage);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void toOnlineGameScene(){ //跳转双人游戏界面
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("/com/jr/tankbattle/fxml/OnlineGameScene.fxml")));
            stage.getScene().setRoot(root);
            onlineGameScene.init(stage);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void toVsGameScene(){ //跳转双人游戏界面
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("/com/jr/tankbattle/fxml/VsGameScene.fxml")));
            stage.getScene().setRoot(root);
            vsGameScene.init(stage);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void toHelp(){ //跳转游戏帮助界面
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("/com/jr/tankbattle/fxml/Help.fxml")));
            stage.getScene().setRoot(root);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void toSoundSrc(){ //跳转声音界面
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("/com/jr/tankbattle/fxml/SoundScr.fxml")));
            stage.getScene().setRoot(root);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
