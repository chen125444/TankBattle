package com.jr.tankbattle.scene;

import com.jr.tankbattle.Director;
import com.jr.tankbattle.entity.Bullet;
import javafx.fxml.FXML;
import com.jr.tankbattle.entity.Tank;
import com.jr.tankbattle.util.Direction;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.stage.Stage;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public class GameScene {
    private Canvas canvas =new Canvas(720,720);
    private GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
    //private KeyProcess keyProcess = new KeyProcess();
    //private Refresh refresh = new Refresh();
    private boolean running = false;
    private Tank playerTank;
    public List<Bullet> bullets = new ArrayList<>();

    //private Background background = new Background(new Image("com/jr/tankbattle/img/background.jpg"));


    public void init(Stage stage) {
        AnchorPane root = new AnchorPane(canvas);
        stage.getScene().setRoot(root);
        //设置键盘事件
        stage.getScene().setOnKeyReleased(this::handleKeyReleased);
        stage.getScene().setOnKeyPressed(this::handleKeyPressed);
        running = true;
        playerTank = new Tank(400, 500, 60, 60, 2, new Image(this.getClass().getResourceAsStream("/com/jr/tankbattle/img/p1tankU.gif")),this);
        //initSprite();
        refresh.start();
    }
    // 处理按键按下事件
    private void handleKeyPressed(KeyEvent event) {
        playerTank.pressed(event.getCode());
    }
    // 处理按键松开事件
    private void handleKeyReleased(KeyEvent event) {
        // 实现坦克停止移动的逻辑
        playerTank.released((event.getCode()));
    }
    // 刷新游戏界面
    private void render() {
        graphicsContext.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        // 绘制背景
        graphicsContext.drawImage(new Image(this.getClass().getResourceAsStream("/com/jr/tankbattle/img/background.jpg")), 0, 0);
        // 绘制玩家坦克
        playerTank.draw();
        // 绘制子弹
        for(int i = 0; i < bullets.size(); i++){
            Bullet bullet = bullets.get(i);
            bullet.move();
            bullet.draw();
        }
    }

    // 刷新任务（类似游戏主循环）
    private final AnimationTimer refresh = new AnimationTimer() {
        @Override
        public void handle(long now) {
            if (running) {
                render();  // 每一帧都调用 render() 以刷新游戏界面
                playerTank.move();
            }
        }
    };

    // 获取 GraphicsContext 对象
    public GraphicsContext getGraphicsContext() {
        return graphicsContext;
    }

    // 设置 GraphicsContext 对象
    public void setGraphicsContext(GraphicsContext graphicsContext) {
        this.graphicsContext = graphicsContext;
    }


}
