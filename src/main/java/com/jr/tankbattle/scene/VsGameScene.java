package com.jr.tankbattle.scene;

import com.jr.tankbattle.controller.GameDlg;
import com.jr.tankbattle.controller.MapScr;
import com.jr.tankbattle.entity.*;
import com.jr.tankbattle.util.MapData;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class VsGameScene {
    @FXML
    private Canvas canvas =new Canvas(900,720);
    private GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
    private boolean running = false;
    private Tank playerTank;
    private Tank2 playerTank2;
    private MapData map = new MapData(this);
    public List<Bullet> bullets = new ArrayList<>();
    public List<Tree> trees = new ArrayList<>();
    public List<Rock> rocks = new ArrayList<>();
    public List<Sheild> sheilds = new ArrayList<>();
    public List<Iron> irons = new ArrayList<>();
    public List<Heart> hearts = new ArrayList<>();
    public List<Pool> pools = new ArrayList<>();
    public List<Landmine> landmines = new ArrayList<>();
    public List<Explode> explodes = new ArrayList<>();
    private final Image backImage = new Image(this.getClass().getResourceAsStream("/com/jr/tankbattle/img/background1.jpg"));


    public void init(Stage stage) {
        AnchorPane root = new AnchorPane(canvas);
        stage.getScene().setRoot(root);
        //设置键盘事件
        stage.getScene().setOnKeyReleased(this::handleKeyReleased);
        stage.getScene().setOnKeyPressed(this::handleKeyPressed);
        hearts.addAll(map.mapData.get(MapScr.getInstance().getId()).hearts);
        irons.addAll(map.mapData.get(MapScr.getInstance().getId()).irons);
        landmines.addAll(map.mapData.get(MapScr.getInstance().getId()).landmines);
        pools.addAll(map.mapData.get(MapScr.getInstance().getId()).pools);
        rocks.addAll(map.mapData.get(MapScr.getInstance().getId()).rocks);
        sheilds.addAll(map.mapData.get(MapScr.getInstance().getId()).sheilds);
        trees.addAll(map.mapData.get(MapScr.getInstance().getId()).trees);
        running = true;
        playerTank = new Tank(400, 500, 40, 40, 2, this);
        //initSprite();
        playerTank2 = new Tank2(800, 500, 40, 40, 2,this);
        //initSprite();
        refresh.start();
        //子弹间隔线程
        new Thread(playerTank).start();
        new Thread(playerTank2).start();
    }
    // 处理按键按下事件
    private void handleKeyPressed(KeyEvent event) {
        if(event.getCode() == KeyCode.ESCAPE){
            running = false;
            GameDlg.getInstance().Show("pause");
        }
        if(GameDlg.getInstance().isFlag()){
            running = true;
        }
        if(running){
            playerTank.pressed(event.getCode());
            playerTank2.pressed(event.getCode());
        }
    }
    // 处理按键松开事件
    private void handleKeyReleased(KeyEvent event) {
        // 实现坦克停止移动的逻辑
        if(running){
            playerTank.released(event.getCode());
            playerTank2.released(event.getCode());
        }
    }
    // 刷新游戏界面
    private void render() {
        graphicsContext.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        // 绘制背景
        graphicsContext.drawImage(backImage, 0,0 );
        for(int i = 0; i < bullets.size(); i++){
            Bullet bullet = bullets.get(i);
            bullet.collisionBullet(bullets);
            bullet.draw();
            bullet.move();
        }
        // 绘制玩家坦克
        if(playerTank.isAlive()){
            playerTank.draw();
            //playerTank.drawLives();
            playerTank.move();
            playerTank.collisionRocks(rocks);
            playerTank.collisionTrees(trees);
            playerTank.collisionSheild(sheilds);
            playerTank.collisionBullet(bullets);
            playerTank.collisionIrons(irons);
            playerTank.collisionPools(pools);
            playerTank.collisionLandmines(landmines);
            playerTank.collisionHeart(hearts);
            playerTank.collisionTank(playerTank2);
        }
        if(playerTank2.isAlive()){
            playerTank2.draw();
            //playerTank2.drawLives();
            playerTank2.move();
            playerTank2.collisionRocks(rocks);
            playerTank2.collisionTrees(trees);
            playerTank2.collisionSheild(sheilds);
            playerTank2.collisionBullet(bullets);
            playerTank2.collisionIrons(irons);
            playerTank2.collisionPools(pools);
            playerTank2.collisionLandmines(landmines);
            playerTank2.collisionHeart(hearts);
            playerTank2.collisionTank(playerTank);
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
        //更新水池
        for(int i = 0; i < pools.size(); i++){
            Pool pool = pools.get(i);
            if(!pool.isAlive()){
                pools.remove(i);
            }
        }
        //绘制水池
        for(int i = 0; i < pools.size(); i++){
            Pool pool = pools.get(i);
            pool.draw();
        }
        //更新铁块
        for(int i = 0; i < irons.size(); i++){
            Iron iron = irons.get(i);
            if(!iron.isAlive()){
                irons.remove(i);
            }
        }
        //绘制铁块
        for(int i = 0; i < irons.size(); i++){
            Iron iron = irons.get(i);
            iron.collisionBullet(bullets);
            iron.draw();
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
        //更新桃心
        for(int i = 0; i < hearts.size(); i++){
            Heart heart = hearts.get(i);
            if(!heart.isAlive()){
                hearts.remove(i);
            }
        }
        //绘制桃心
        for(int i = 0; i < hearts.size(); i++){
            Heart heart = hearts.get(i);
            heart.draw();
        }
        //更新地雷
        for(int i = 0; i < landmines.size(); i++){
            Landmine landmine = landmines.get(i);
            if(!landmine.isAlive()){
                landmines.remove(i);
            }
        }
        //绘制地雷
        for(int i = 0; i < landmines.size(); i++){
            Landmine landmine = landmines.get(i);
            landmine.draw();
        }
        //更新盾牌
        for(int i = 0; i < sheilds.size(); i++){
            Sheild sheild = sheilds.get(i);
            if(!sheild.isAlive()){
                sheilds.remove(i);
            }
        }
        //绘制盾牌
        for(int i = 0; i < sheilds.size(); i++){
            Sheild sheild = sheilds.get(i);
            sheild.draw();
            if(playerTank.checkCollision(sheild) && playerTank.isInvincible()){
                sheild.draw(playerTank);
            }
            if (playerTank2.checkCollision(sheild) && playerTank2.isInvincible()){
                sheild.draw(playerTank2);
            }
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

