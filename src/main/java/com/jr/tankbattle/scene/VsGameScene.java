package com.jr.tankbattle.scene;

import com.jr.tankbattle.entity.*;
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
import java.util.Random;

public class VsGameScene {
    @FXML
    private Canvas canvas =new Canvas(900,720);
    private GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
    private boolean running = false;
    private Tank playerTank;
    private Tank2 playerTank2;
    public List<Bullet> bullets = new ArrayList<>();
    public List<Rock> rocks = new ArrayList<>();
    public List<Tree> trees = new ArrayList<>();
    public List<Explode> explodes = new ArrayList<>();
    private final Image backImage = new Image(this.getClass().getResourceAsStream("/com/jr/tankbattle/img/background.jpg"));


    public void init(Stage stage) {
        AnchorPane root = new AnchorPane(canvas);
        stage.getScene().setRoot(root);
        //设置键盘事件
        stage.getScene().setOnKeyReleased(this::handleKeyReleased);
        stage.getScene().setOnKeyPressed(this::handleKeyPressed);
        running = true;
        playerTank = new Tank(400, 500, 40, 40, 2, this);
        //initSprite();
        playerTank2 = new Tank2(800, 500, 40, 40, 2,this);
        //产生树丛
        for(int i=0;i<20;i++){
            Random random = new Random();
            int randomX = random.nextInt(1024);
            int randomY = random.nextInt(720);
            Tree tree = new Tree(randomX,randomY,40,40,this);
            trees.add(tree);
        }
        //initSprite();
        refresh.start();
        //子弹间隔线程
        new Thread(playerTank).start();
        new Thread(playerTank2).start();
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
        graphicsContext.drawImage(backImage, 0,0 );
        // 绘制玩家坦克
        if(playerTank.isAlive()){
            playerTank.draw();
            playerTank.move();
            playerTank.collisionRocks(rocks);
            playerTank.collisionTrees(trees);
            playerTank.collisionBullet(bullets);
            playerTank.collisionTank(playerTank2);
            // 绘制子弹
            for(int i = 0; i < bullets.size(); i++){
                Bullet bullet = bullets.get(i);
                bullet.move();
                bullet.draw();
            }
        }
        if(playerTank2.isAlive()){
            playerTank2.draw();
            playerTank2.move();
            playerTank2.collisionRocks(rocks);
            playerTank2.collisionTrees(trees);
            playerTank2.collisionBullet(bullets);
            playerTank2.collisionTank(playerTank);
            for(int i = 0; i < bullets.size(); i++){
                Bullet bullet = bullets.get(i);
                bullet.collisionBullet(bullets);
                bullet.move();
                bullet.draw();
            }
        }
        //更新石头
        for(int i = 0; i < rocks.size(); i++){
            Rock rock = rocks.get(i);
            if(!rock.isAlive()){
                rocks.remove(i);
            }
        }
        //绘制石头
        for(int i = 0; i < rocks.size(); i++){
            Rock rock = rocks.get(i);
            rock.collisionBullet(bullets);
            rock.draw();
        }
        //更新树丛
        for(int i = 0; i < trees.size(); i++){
            Tree tree = trees.get(i);
            tree.collisionBullet(bullets);
            if(!tree.isAlive()){
                trees.remove(i);
            }
        }
        //绘制树丛
        for(int i = 0; i < trees.size(); i++){
            Tree tree = trees.get(i);
            tree.draw();
        }
        //产生爆炸
        for (int i = 0; i < explodes.size(); i++) {
            Explode e = explodes.get(i);
            e.draw(graphicsContext);
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



}

