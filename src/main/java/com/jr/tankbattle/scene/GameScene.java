package com.jr.tankbattle.scene;

import com.jr.tankbattle.Director;
import javafx.fxml.FXML;
import com.jr.tankbattle.entity.Tank;
import com.jr.tankbattle.util.Direction;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.stage.Stage;

import java.awt.*;


public class GameScene {
    @FXML
    private Canvas canvas = new Canvas(720,720);
    @FXML
    private GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
    //private KeyProcess keyProcess = new KeyProcess();
    //private Refresh refresh = new Refresh();
    private boolean running = false;
    private Tank playerTank;

    //private Background background = new Background(new Image("com/jr/tankbattle/img/background.jpg"));


    public void init(Stage stage) {
        AnchorPane root = new AnchorPane(canvas);
        stage.getScene().setRoot(root);
        //设置键盘事件
        stage.getScene().setOnKeyReleased(this::handleKeyPressed);
        stage.getScene().setOnKeyPressed(this::handleKeyReleased);
        running = true;
        playerTank = new Tank(400, 500, 100, 100, 30, new Image(this.getClass().getResourceAsStream("/com/jr/tankbattle/img/p1tankU.gif")),this);
        //initSprite();
        refresh.start();
    }
    // 处理按键按下事件
    private void handleKeyPressed(KeyEvent event) {
        playerTank.tankState = true;
        switch (event.getCode()) {
            case W:
                playerTank.setDirection(Direction.UP);
                break;
            case S:
                playerTank.setDirection(Direction.DOWN);
                break;
            case A:
                playerTank.setDirection(Direction.LEFT);
                break;
            case D:
                playerTank.setDirection(Direction.RIGHT);
                break;
            default:
                break;
        }
        playerTank.move();
    }
    // 处理按键松开事件
    private void handleKeyReleased(KeyEvent event) {
        // 实现坦克停止移动的逻辑（例如设置方向为停止）
        playerTank.stopMoving();
    }
    // 刷新游戏界面
    private void render() {
        graphicsContext.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        // 绘制背景
        graphicsContext.drawImage(new Image(this.getClass().getResourceAsStream("/com/jr/tankbattle/img/background.jpg")), 0, 0);

        // 绘制玩家坦克
        graphicsContext.drawImage(new Image(this.getClass().getResourceAsStream("/com/jr/tankbattle/img/p1tankU.gif")), playerTank.getX(), playerTank.getY());

        // 绘制其他游戏元素（如敌方坦克、子弹等）
    }

    // 刷新任务（类似游戏主循环）
    private final AnimationTimer refresh = new AnimationTimer() {
        @Override
        public void handle(long now) {
            if (running) {
                render();  // 每一帧都调用 render() 以刷新游戏界面
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
