package com.jr.tankbattle.scene;

import com.jr.tankbattle.Director;
import com.jr.tankbattle.entity.AiTank;
import com.jr.tankbattle.entity.Bullet;
import com.jr.tankbattle.entity.Tree;
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
import java.util.Random;


public class GameScene {
    @FXML
    private Canvas canvas =new Canvas(1080,720);
    private GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
    //private KeyProcess keyProcess = new KeyProcess();
    //private Refresh refresh = new Refresh();
    private boolean running = false;
    private Tank playerTank;
    public List<Bullet> bullets = new ArrayList<>();
    public List<AiTank> aiTanks = new ArrayList<>();
    public List<Tree> trees = new ArrayList<>();
    //private Background background = new Background(new Image("com/jr/tankbattle/img/background.jpg"));


    public void init(Stage stage) {
        AnchorPane root = new AnchorPane(canvas);
        stage.getScene().setRoot(root);
        //设置键盘事件
        stage.getScene().setOnKeyReleased(this::handleKeyReleased);
        stage.getScene().setOnKeyPressed(this::handleKeyPressed);
        running = true;
        //产生玩家坦克
        playerTank = new Tank(400, 500, 60, 60, 2, this);
        //产生人机坦克
        for(int i=0;i<5;i++){
            Random random = new Random();
            int randomX = random.nextInt(60);
            AiTank aiTank = new AiTank(randomX+150*i,randomX+120*i,60,60,2,this);
            aiTanks.add(aiTank);
        }
        //产生树丛
        for(int i=0;i<20;i++){
            Random random = new Random();
            int randomX = random.nextInt(1024);
            int randomY = random.nextInt(720);
            Tree tree = new Tree(randomX,randomY,60,60,this);
            trees.add(tree);
        }
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
        graphicsContext.drawImage(new Image(this.getClass().getResourceAsStream("/com/jr/tankbattle/img/background.jpg")), 0,0 );
        // 绘制玩家坦克
        if(playerTank.isAlive()){
            playerTank.collisionBullet(bullets);
            playerTank.collisionPlayer(aiTanks);
            playerTank.move();
            playerTank.draw();
        }
        //更新子弹
        for(int i = 0; i < bullets.size(); i++){
            Bullet bullet = bullets.get(i);
            if(!bullet.isAlive()){
                bullets.remove(i);
            }
        }
        // 绘制子弹
        for(int i = 0; i < bullets.size(); i++){
            Bullet bullet = bullets.get(i);
            bullet.move();
            bullet.draw();
        }
        //更新人机坦克
        for(int i = 0; i < aiTanks.size(); i++){
            AiTank aiTank = aiTanks.get(i);
            aiTank.collisionBullet(bullets);
            if(!aiTank.isAlive()){
                aiTanks.remove(i);
            }
        }
        // 绘制人机坦克
        for(int i = 0; i < aiTanks.size(); i++){
            AiTank aiTank = aiTanks.get(i);
            aiTank.collisionTank(playerTank);
            aiTank.collisionAi(aiTanks);
            aiTank.move();
            aiTank.draw();
        }
        //更新树丛
        for(int i = 0; i < trees.size(); i++){
            Tree tree = trees.get(i);
            if(!tree.isAlive()){
                trees.remove(i);
            }
        }
        //绘制树丛
        for(int i = 0; i < trees.size(); i++){
            Tree tree = trees.get(i);
            //tree.
            tree.draw();
        }
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
