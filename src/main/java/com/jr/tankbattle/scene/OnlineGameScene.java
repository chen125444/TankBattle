package com.jr.tankbattle.scene;

import com.jr.tankbattle.entity.*;
import com.jr.tankbattle.util.MapData;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.util.*;

public class OnlineGameScene {
    @FXML
    private Canvas canvas = new Canvas(1080, 720);
    private GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
    //private KeyProcess keyProcess = new KeyProcess();
    //private Refresh refresh = new Refresh();
    private boolean running = false;
    private Map<String, Tank3> playerTanks=new HashMap<>();
    private List<String> playerList;
    private Tank3 playerTank1;
    private Tank3 playerTank2;
    private Tank3 playerTank3;
    private Tank3 playerTank4;

    private MapData map = new MapData(this);
    public List<Bullet> bullets = new ArrayList<>();
    public List<Rock> rocks = new ArrayList<>();
    public List<Tree> trees = map.mapData.get(1).trees;
    public List<Explode> explodes = new ArrayList<>();
    //private Background background = new Background(new Image("com/jr/tankbattle/img/background.jpg"));


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

        // Assign tanks to players based on playerList
        for (int i = 0; i < playerList.size(); i++) {
            String playerId = playerList.get(i);
            Tank3.TankType tankType = tankTypes.get(i % tankTypes.size()); // Assign type cyclically
            Tank3 tank = new Tank3();
            tank.setTankType(tankType);
            tank.setPlayerId(playerId);

            // Set default positions for tanks; this can be customized
            switch (i) {
                case 0 -> tank.setPosition(0, 0);
                case 1 -> tank.setPosition(860, 0);
                case 2 -> tank.setPosition(0, 680);
                case 3 -> tank.setPosition(860, 680);
            }

            tank.loadImages();
            playerTanks.put(playerId, tank);
        }
        // Set the player tank references based on the map
        if (playerTanks.containsKey(playerList.get(0))) playerTank1 = playerTanks.get(playerList.get(0));
        if (playerTanks.size() > 1 && playerTanks.containsKey(playerList.get(1))) playerTank2 = playerTanks.get(playerList.get(1));
        if (playerTanks.size() > 2 && playerTanks.containsKey(playerList.get(2))) playerTank3 = playerTanks.get(playerList.get(2));
        if (playerTanks.size() > 3 && playerTanks.containsKey(playerList.get(3))) playerTank4 = playerTanks.get(playerList.get(3));
    }

    // 处理按键按下事件
    private void handleKeyPressed(KeyEvent event) {
        // Adjust according to which player tank is controlled by which keys
        if (playerTank1 != null) playerTank1.pressed(event.getCode());
        if (playerTank2 != null) playerTank2.pressed(event.getCode());
        if (playerTank3 != null) playerTank3.pressed(event.getCode());
        if (playerTank4 != null) playerTank4.pressed(event.getCode());
    }

    // 处理按键松开事件
    private void handleKeyReleased(KeyEvent event) {
        // 实现坦克停止移动的逻辑
        if (playerTank1 != null) playerTank1.released(event.getCode());
        if (playerTank2 != null) playerTank2.released(event.getCode());
        if (playerTank3 != null) playerTank3.released(event.getCode());
        if (playerTank4 != null) playerTank4.released(event.getCode());
    }

    // 刷新游戏界面
    private void render() {
        graphicsContext.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        // 绘制背景
        graphicsContext.drawImage(new Image(this.getClass().getResourceAsStream("/com/jr/tankbattle/img/background.jpg")), 0, 0);
        // 绘制玩家坦克
        if (playerTank1 != null && playerTank1.isAlive()) {
            playerTank1.draw();
            playerTank1.move();
            playerTank1.collisionRocks(rocks);
            playerTank1.collisionTrees(trees);
            playerTank1.collisionBullet(bullets);
            playerTank1.collisionTank(playerTank2);
        }

        if (playerTank2 != null && playerTank2.isAlive()) {
            playerTank2.draw();
            playerTank2.move();
            playerTank2.collisionRocks(rocks);
            playerTank2.collisionTrees(trees);
            playerTank2.collisionBullet(bullets);
            playerTank2.collisionTank(playerTank1);
        }

        if (playerTank3 != null && playerTank3.isAlive()) {
            playerTank3.draw();
            playerTank3.move();
            playerTank3.collisionRocks(rocks);
            playerTank3.collisionTrees(trees);
            playerTank3.collisionBullet(bullets);
            playerTank3.collisionTank(playerTank1);
            playerTank3.collisionTank(playerTank2);
        }

        if (playerTank4 != null && playerTank4.isAlive()) {
            playerTank4.draw();
            playerTank4.move();
            playerTank4.collisionRocks(rocks);
            playerTank4.collisionTrees(trees);
            playerTank4.collisionBullet(bullets);
            playerTank4.collisionTank(playerTank1);
            playerTank4.collisionTank(playerTank2);
            playerTank4.collisionTank(playerTank3);
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

