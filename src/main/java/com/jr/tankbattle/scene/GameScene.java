package com.jr.tankbattle.scene;
import com.jr.tankbattle.controller.GameDlg;
import com.jr.tankbattle.controller.MapScr;
import com.jr.tankbattle.entity.*;
import com.jr.tankbattle.util.MapData;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.util.*;


public class GameScene {
    @FXML
    private Canvas canvas =new Canvas(1080,720);
    private GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
    private boolean running = false;
    private Tank playerTank;
    private MapData map = new MapData(this);
    public List<Bullet> bullets = new ArrayList<>();
    public List<AiTank> aiTanks = new ArrayList<>();
    public List<Tree> trees = new ArrayList<>();
    public List<Rock> rocks = new ArrayList<>();
    public List<Sheild> sheilds = new ArrayList<>();
    public List<Iron> irons = new ArrayList<>();
    public List<Heart> hearts = new ArrayList<>();
    public List<Pool> pools = new ArrayList<>();
    public List<Landmine> landmines = new ArrayList<>();
    public List<Explode> explodes = new ArrayList<>();
    private final Image backImage0 = new Image(Objects.requireNonNull(this.getClass().getResourceAsStream("/com/jr/tankbattle/img/bkg5.jpg")));
    private final Image backImage = new Image(Objects.requireNonNull(this.getClass().getResourceAsStream("/com/jr/tankbattle/img/background1.jpg")));

    public Map<Integer,GameScene> gameScenes = new HashMap<>();

    public void init(Stage stage) {
        AnchorPane root = new AnchorPane(canvas);
        stage.getScene().setRoot(root);
        aiTanks.addAll(map.mapData.get(MapScr.getInstance().getId()).aiTanks);
        hearts.addAll(map.mapData.get(MapScr.getInstance().getId()).hearts);
        irons.addAll(map.mapData.get(MapScr.getInstance().getId()).irons);
        landmines.addAll(map.mapData.get(MapScr.getInstance().getId()).landmines);
        pools.addAll(map.mapData.get(MapScr.getInstance().getId()).pools);
        rocks.addAll(map.mapData.get(MapScr.getInstance().getId()).rocks);
        sheilds.addAll(map.mapData.get(MapScr.getInstance().getId()).sheilds);
        trees.addAll(map.mapData.get(MapScr.getInstance().getId()).trees);
        //设置键盘事件
        stage.getScene().setOnKeyReleased(this::handleKeyReleased);
        stage.getScene().setOnKeyPressed(this::handleKeyPressed);
        running = true;
        //产生玩家坦克
        playerTank = new Tank(400, 500, 40, 40, 2, this);
        //initSprite();
        refresh.start();
        //子弹间隔线程
        new Thread(playerTank).start();
        for (AiTank aiTank : aiTanks){
            new Thread(aiTank).start();
        }
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
        }
    }
    // 处理按键松开事件
    private void handleKeyReleased(KeyEvent event) {
        // 实现坦克停止移动的逻辑
        if(running){
            playerTank.released((event.getCode()));
        }
    }
    // 刷新游戏界面
    private void render() {
        graphicsContext.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        // 绘制背景
        graphicsContext.drawImage(backImage0, 0,0 );
        graphicsContext.drawImage(backImage, 0,0 );
        // 绘制子弹
        for(int i = 0; i < bullets.size(); i++){
            Bullet bullet = bullets.get(i);
            bullet.collisionBullet(bullets);
            bullet.draw();
            bullet.move();
        }
        // 绘制玩家坦克
        if(playerTank.isAlive()){
            playerTank.collisionBullet(bullets);
            playerTank.collisionPlayer(aiTanks);
            playerTank.collisionRocks(rocks);
            playerTank.collisionTrees(trees);
            playerTank.collisionSheild(sheilds);
            playerTank.collisionPools(pools);
            playerTank.collisionIrons(irons);
            playerTank.collisionHeart(hearts);
            playerTank.collisionLandmines(landmines);
            playerTank.move();
            playerTank.draw();
            playerTank.drawLives();
        }
        else gameOver();
        //更新人机坦克
        for(int i = 0; i < aiTanks.size(); i++){
            AiTank aiTank = aiTanks.get(i);
            aiTank.collisionBullet(bullets);
            if(!aiTank.isAlive()) {
                aiTanks.remove(i);
            }
        }
        // 绘制人机坦克
        for(int i = 0; i < aiTanks.size(); i++){
            AiTank aiTank = aiTanks.get(i);
            aiTank.collisionTank(playerTank);
            aiTank.collisionRocks(rocks);
            aiTank.collisionTrees(trees);
            aiTank.collisionAi(aiTanks);
            aiTank.collisionIrons(irons);
            aiTank.collisionLandmines(landmines);
            aiTank.collisionPools(pools);
            aiTank.collisionSheild(sheilds);
            aiTank.aiMove(playerTank,aiTanks);
            aiTank.draw();
        }
        //更新石头
        for(int i = 0; i < rocks.size(); i++){
            Rock rock = rocks.get(i);
            if(!rock.isAlive()) {
                rocks.remove(i);
            }
        }
        //绘制石头
        for (Rock rock : rocks) {
            rock.collisionBullet(bullets);
            rock.draw();
        }
        //更新桃心
        for(int i = 0; i < hearts.size(); i++){
            Heart heart = hearts.get(i);
            if(!heart.isAlive()){
                hearts.remove(i);
            }
        }
        //绘制桃心
        for (Heart heart : hearts) {
            heart.draw();
        }
        //更新水池
        for(int i = 0; i < pools.size(); i++){
            Pool pool = pools.get(i);
            if(!pool.isAlive()){
                pools.remove(i);
            }
        }
        //绘制水池
        for (Pool pool : pools) {
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
        for (Iron iron : irons) {
            iron.collisionBullet(bullets);
            iron.draw();
        }
        //更新树丛
        for(int i = 0; i < trees.size(); i++){
            Tree tree = trees.get(i);
            if(!tree.isAlive()) trees.remove(i);
        }
        //绘制树丛
        for (Tree tree : trees) {
            tree.collisionBullet(bullets);
            tree.draw();
        }
        //更新地雷
        for (Landmine landmine : landmines) {
            landmine.draw();
        }
        //更新盾牌
        for(int i = 0; i < sheilds.size(); i++){
            Sheild sheild = sheilds.get(i);
            if(!sheild.isAlive()) {
                sheilds.remove(i);
            }
        }
        //绘制盾牌
        for (Sheild sheild : sheilds) {
            sheild.draw();
            if (playerTank.checkCollision(sheild) && playerTank.isInvincible()) {
                sheild.draw(playerTank);
            }
            for (AiTank aiTank : aiTanks) {
                if (aiTank.checkCollision(sheild) && aiTank.isInvincible()) {
                    sheild.draw(aiTank);
                }
            }
        }
        //产生爆炸
        for (Explode e : explodes) {
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

    public void gameOver() {
        running = false;
        aiTanks.clear();
        bullets.clear();
        explodes.clear();
        hearts.clear();
        irons.clear();
        landmines.clear();
        pools.clear();
        rocks.clear();
        sheilds.clear();
        trees.clear();

        if(!playerTank.isAlive()){
            playerTank.setThreadRunning(false);
            for (AiTank aiTank : aiTanks){
                aiTank.setThreadRunning(false);
            }
            refresh.stop();
            Platform.runLater(() -> {
                GameDlg.getInstance().Show("gameLoseSingle");
                aiTanks.addAll(map.mapData.get(MapScr.getInstance().getId()).aiTanks);
                hearts.addAll(map.mapData.get(MapScr.getInstance().getId()).hearts);
                irons.addAll(map.mapData.get(MapScr.getInstance().getId()).irons);
                landmines.addAll(map.mapData.get(MapScr.getInstance().getId()).landmines);
                pools.addAll(map.mapData.get(MapScr.getInstance().getId()).pools);
                rocks.addAll(map.mapData.get(MapScr.getInstance().getId()).rocks);
                sheilds.addAll(map.mapData.get(MapScr.getInstance().getId()).sheilds);
                trees.addAll(map.mapData.get(MapScr.getInstance().getId()).trees);
                refresh.start();
            });
        }
        else {
            refresh.stop();
            Platform.runLater(() -> {
                GameDlg.getInstance().Show("gameWinSingle");
                aiTanks.addAll(map.mapData.get(MapScr.getInstance().getId()).aiTanks);
                hearts.addAll(map.mapData.get(MapScr.getInstance().getId()).hearts);
                irons.addAll(map.mapData.get(MapScr.getInstance().getId()).irons);
                landmines.addAll(map.mapData.get(MapScr.getInstance().getId()).landmines);
                pools.addAll(map.mapData.get(MapScr.getInstance().getId()).pools);
                rocks.addAll(map.mapData.get(MapScr.getInstance().getId()).rocks);
                sheilds.addAll(map.mapData.get(MapScr.getInstance().getId()).sheilds);
                trees.addAll(map.mapData.get(MapScr.getInstance().getId()).trees);
                refresh.start();
            });
        }
    }

}
