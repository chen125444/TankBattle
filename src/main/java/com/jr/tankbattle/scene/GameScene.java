package com.jr.tankbattle.scene;
import com.jr.tankbattle.entity.*;
import com.jr.tankbattle.util.MapData;
import javafx.fxml.FXML;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
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
    public List<Rock> rocks = new ArrayList<>();
    public List<AiTank> aiTanks = map.mapData.get(1).aitanks;
    public List<Tree> trees = map.mapData.get(1).trees;
    public List<Explode> explodes = new ArrayList<>();
    private Image backImage = new Image(this.getClass().getResourceAsStream("/com/jr/tankbattle/img/background.jpg"));

    public Map<Integer,GameScene> gameScenes = new HashMap<>();

    public void init(Stage stage) {
        AnchorPane root = new AnchorPane(canvas);
        stage.getScene().setRoot(root);
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
            new Thread(playerTank).start();
        }
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
        graphicsContext.drawImage(backImage, 0,0 );
        // 绘制玩家坦克
        if(playerTank.isAlive()){
            playerTank.collisionBullet(bullets);
            playerTank.collisionPlayer(aiTanks);
            playerTank.collisionRocks(rocks);
            playerTank.collisionTrees(trees);
            playerTank.move();
            playerTank.draw();
        }

        // 绘制子弹
        for(int i = 0; i < bullets.size(); i++){
            Bullet bullet = bullets.get(i);
            bullet.collisionBullet(bullets);
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
            aiTank.collisionRocks(rocks);
            aiTank.collisionTrees(trees);
            aiTank.collisionAi(aiTanks);
            aiTank.move();
            aiTank.draw();
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
            if(!tree.isAlive()){
                trees.remove(i);
            }
        }
        //绘制树丛
        for(int i = 0; i < trees.size(); i++){
            Tree tree = trees.get(i);
            tree.collisionBullet(bullets);
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
