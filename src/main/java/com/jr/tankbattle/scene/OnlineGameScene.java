package com.jr.tankbattle.scene;

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

import java.util.*;

public class OnlineGameScene {
    @FXML
    private Canvas canvas = new Canvas(900, 720);
    private GraphicsContext graphicsContext = canvas.getGraphicsContext2D();

    private boolean running = false;
    private Map<String, Tank3> playerTanks = new HashMap<>();
    private List<String> playerList;
    private Tank3 playerTank1;
    private Tank3 playerTank2;
    private Tank3 playerTank3;
    private Tank3 playerTank4;

    private MapData map = new MapData(this);
    public List<Bullet> bullets = new ArrayList<>();
    public List<Rock> rocks = map.mapData.get(1).rocks;
    public List<Tree> trees = map.mapData.get(1).trees;
    public List<Sheild> sheilds = map.mapData.get(1).sheilds;
    public List<Landmine> landmines = map.mapData.get(1).landmines;
    public List<Iron> irons = map.mapData.get(1).irons;
    public List<Heart> hearts = map.mapData.get(1).hearts;
    public List<Pool> pools = map.mapData.get(1).pools;
    public List<Explode> explodes = new ArrayList<>();

    public Image backgroundImage = new Image(this.getClass().getResourceAsStream("/com/jr/tankbattle/img/background.jpg"));

    public void init(Stage stage, List<String> playerList) {
        AnchorPane root = new AnchorPane(canvas);
        stage.getScene().setRoot(root);
        //设置键盘事件
        stage.getScene().setOnKeyReleased(this::handleKeyReleased);
        stage.getScene().setOnKeyPressed(this::handleKeyPressed);
        running = true;

        this.playerList = playerList;
        initializePlayerTanks();

        refresh.start();
        //子弹间隔线程
        if (playerTank1 != null) {
            new Thread(playerTank1).start();
        }
        if (playerTank2 != null) {
            new Thread(playerTank2).start();
        }
        if (playerTank3 != null) {
            new Thread(playerTank3).start();
        }
        if (playerTank4 != null) {
            new Thread(playerTank4).start();
        }

    }

    private void initializePlayerTanks() {
// Assuming tankTypes are predefined or determined somewhere
        List<Tank3.TankType> tankTypes = new ArrayList<>(List.of(Tank3.TankType.values()));

        // Shuffle the tank types to assign randomly
        Collections.shuffle(tankTypes);
        Collections.shuffle(playerList);
        // Assign tanks to players based on playerList
        for (int i = 0; i < playerList.size(); i++) {
            String playerId = playerList.get(i);
            Tank3.TankType tankType = tankTypes.get(i % tankTypes.size()); // Assign type cyclically
            Tank3 tank=new Tank3();
            // Set default positions for tanks; this can be customized
            switch (i) {
                case 0 -> tank = new Tank3(0,0,40,40,2,this);
                case 1 -> tank = new Tank3(100,100,40,40,2,this);
                case 2 -> tank = new Tank3(200,200,40,40,2,this);
                case 3 -> tank = new Tank3(300,300,40,40,2,this);
            }
            tank.setTankType(tankType);
            tank.setPlayerId(playerId);

//            tank.loadImages();
            playerTanks.put(playerId, tank);
        }
        // Set the player tank references based on the map
        if (playerTanks.containsKey(playerList.get(0))) playerTank1 = playerTanks.get(playerList.get(0));
        if (playerTanks.size() > 1 && playerTanks.containsKey(playerList.get(1)))
            playerTank2 = playerTanks.get(playerList.get(1));
        if (playerTanks.size() > 2 && playerTanks.containsKey(playerList.get(2)))
            playerTank3 = playerTanks.get(playerList.get(2));
        if (playerTanks.size() > 3 && playerTanks.containsKey(playerList.get(3)))
            playerTank4 = playerTanks.get(playerList.get(3));
    }

    // 处理按键按下事件
    private void handleKeyPressed(KeyEvent event) {
        // Adjust according to which player tank is controlled by which keys
        if(event.getCode() == KeyCode.ESCAPE){
            if(running){
                running = false;
            }else {
                running = true;
            }
        }
        if(running){
            if (playerTank1 != null) playerTank1.pressed(event.getCode());
            if (playerTank2 != null) playerTank2.pressed(event.getCode());
            if (playerTank3 != null) playerTank3.pressed(event.getCode());
            if (playerTank4 != null) playerTank4.pressed(event.getCode());
        }

    }

    // 处理按键松开事件
    private void handleKeyReleased(KeyEvent event) {
        // 实现坦克停止移动的逻辑
        if(running){
            if (playerTank1 != null) playerTank1.released(event.getCode());
            if (playerTank2 != null) playerTank2.released(event.getCode());
            if (playerTank3 != null) playerTank3.released(event.getCode());
            if (playerTank4 != null) playerTank4.released(event.getCode());
        }
    }

    // 刷新游戏界面
    private void render() {
        graphicsContext.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        // 绘制背景
        graphicsContext.drawImage(backgroundImage, 0, 0);
        // 绘制子弹
        for(int i = 0; i < bullets.size(); i++){
            Bullet bullet = bullets.get(i);
            bullet.collisionBullet(bullets);
            bullet.draw();
            bullet.move();
        }
        // 绘制玩家坦克
        if (playerTank1 != null && playerTank1.isAlive()) {
            playerTank1.draw();
            playerTank1.move();
            playerTank1.collisionRocks(rocks);
            playerTank1.collisionTrees(trees);
            playerTank1.collisionBullet(bullets);
            playerTank1.collisionPools(pools);
            playerTank1.collisionIrons(irons);
            playerTank1.collisionLandmines(landmines);
            playerTank1.collisionSheild(sheilds);
            playerTank1.collisionHeart(hearts);
            if(playerTank2 != null) {
                playerTank1.collisionTank(playerTank2);
            }
            if(playerTank3 != null) {
                playerTank1.collisionTank(playerTank3);
            }
            if(playerTank4 != null) {
                playerTank1.collisionTank(playerTank4);
            }
        }

        if (playerTank2 != null && playerTank2.isAlive()) {
            playerTank2.draw();
            playerTank2.move();
            playerTank2.collisionRocks(rocks);
            playerTank2.collisionTrees(trees);
            playerTank2.collisionBullet(bullets);
            playerTank2.collisionPools(pools);
            playerTank2.collisionIrons(irons);
            playerTank2.collisionLandmines(landmines);
            playerTank2.collisionSheild(sheilds);
            playerTank2.collisionHeart(hearts);
            if(playerTank1 != null) {
                playerTank2.collisionTank(playerTank1);
            }
            if(playerTank3 != null) {
                playerTank2.collisionTank(playerTank3);
            }
            if(playerTank4 != null) {
                playerTank2.collisionTank(playerTank4);
            }
        }

        if (playerTank3 != null && playerTank3.isAlive()) {
            playerTank3.draw();
            playerTank3.move();
            playerTank3.collisionRocks(rocks);
            playerTank3.collisionTrees(trees);
            playerTank3.collisionBullet(bullets);
            playerTank3.collisionPools(pools);
            playerTank3.collisionIrons(irons);
            playerTank3.collisionLandmines(landmines);
            playerTank3.collisionSheild(sheilds);
            playerTank3.collisionHeart(hearts);
            if(playerTank1 != null) {
                playerTank3.collisionTank(playerTank1);
            }
            if(playerTank2 != null) {
                playerTank3.collisionTank(playerTank2);
            }
            if(playerTank4 != null) {
                playerTank3.collisionTank(playerTank4);
            }
        }

        if (playerTank4 != null && playerTank4.isAlive()) {
            playerTank4.draw();
            playerTank4.move();
            playerTank4.collisionRocks(rocks);
            playerTank4.collisionTrees(trees);
            playerTank4.collisionBullet(bullets);
            playerTank4.collisionPools(pools);
            playerTank4.collisionIrons(irons);
            playerTank4.collisionLandmines(landmines);
            playerTank4.collisionSheild(sheilds);
            playerTank4.collisionHeart(hearts);
            if(playerTank1 != null) {
                playerTank4.collisionTank(playerTank1);
            }
            if(playerTank2 != null) {
                playerTank4.collisionTank(playerTank2);
            }
            if(playerTank3 != null) {
                playerTank4.collisionTank(playerTank3);
            }
        }
        //更新石头
        for (int i = 0; i < rocks.size(); i++) {
            Rock rock = rocks.get(i);
            if (!rock.isAlive()) {
                rocks.remove(i);
            }
        }
        //绘制石头
        for (int i = 0; i < rocks.size(); i++) {
            Rock rock = rocks.get(i);
            rock.collisionBullet(bullets);
            rock.draw();
        }
        //更新树丛
        for (int i = 0; i < trees.size(); i++) {
            Tree tree = trees.get(i);
            tree.collisionBullet(bullets);
            if (!tree.isAlive()) {
                trees.remove(i);
            }
        }
        //绘制树丛
        for (int i = 0; i < trees.size(); i++) {
            Tree tree = trees.get(i);
            tree.draw();
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
            if(playerTank1.checkCollision(sheild) && !sheild.isMoving()){
                sheild.draw(playerTank1);
            }
            if (playerTank2.checkCollision(sheild) && !sheild.isMoving()){
                sheild.draw(playerTank2);
            }
            if(playerTank3.checkCollision(sheild) && !sheild.isMoving()){
                sheild.draw(playerTank3);
            }
            if (playerTank3.checkCollision(sheild) && !sheild.isMoving()){
                sheild.draw(playerTank3);
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

    // 设置 GraphicsContext 对象
    public void setGraphicsContext(GraphicsContext graphicsContext) {
        this.graphicsContext = graphicsContext;
    }


}

