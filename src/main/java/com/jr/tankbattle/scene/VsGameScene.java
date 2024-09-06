package com.jr.tankbattle.scene;

import com.jr.tankbattle.entity.Bullet;
import com.jr.tankbattle.entity.Tank;
import com.jr.tankbattle.entity.Tank2;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class VsGameScene {
    @FXML
    private Canvas canvas =new Canvas(1080,720);
    private GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
    //private KeyProcess keyProcess = new KeyProcess();
    //private Refresh refresh = new Refresh();
    private boolean running = false;
    private Tank playerTank;
    private Tank2 playerTank2;
    public List<Bullet> bullets = new ArrayList<>();
    public List<Bullet> bullets2 = new ArrayList<>();

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
        playerTank2 = new Tank2(800, 500, 60, 60, 2, new Image(this.getClass().getResourceAsStream("/com/jr/tankbattle/img/p1tankU.gif")),this);
        //initSprite();
        refresh.start();
    }
    // 处理按键按下事件
    private void handleKeyPressed(KeyEvent event) {
        playerTank.pressed(event.getCode());
        playerTank2.pressed(event.getCode());
    }
    // 处理按键松开事件
    private void handleKeyReleased(KeyEvent event) {
        // 实现坦克停止移动的逻辑
        playerTank.released(event.getCode());
        playerTank2.released(event.getCode());
    }
    // 刷新游戏界面
    private void render() {
        graphicsContext.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        // 绘制背景
        graphicsContext.drawImage(new Image(this.getClass().getResourceAsStream("/com/jr/tankbattle/img/background.jpg")), 0,0 );
        // 绘制玩家坦克
        playerTank.draw2();
        playerTank2.draw();
        // 绘制子弹
        for(int i = 0; i < bullets.size(); i++){
            Bullet bullet = bullets.get(i);
            bullet.move();
            bullet.draw2();
        }
        for(int i = 0; i < bullets2.size(); i++){
            Bullet bullet2 = bullets2.get(i);
            bullet2.move();
            bullet2.draw2();
        }
    }

    // 刷新任务（类似游戏主循环）
    private final AnimationTimer refresh = new AnimationTimer() {
        @Override
        public void handle(long now) {
            if (running) {
                render();  // 每一帧都调用 render() 以刷新游戏界面
                playerTank.move();
                playerTank2.move();
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

