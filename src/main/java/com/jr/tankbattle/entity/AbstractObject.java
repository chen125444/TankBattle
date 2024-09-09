package com.jr.tankbattle.entity;

import com.jr.tankbattle.scene.GameScene;
import com.jr.tankbattle.scene.OnlineGameScene;
import com.jr.tankbattle.scene.VsGameScene;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;

public abstract class AbstractObject {
    // 坐标
    private int x;
    private int y;
    //尺寸
    private int width;
    private int height;
    // 图像
    private Image image;
    // 界面
    private GameScene gameScene;
    private VsGameScene vsGameScene;
    private OnlineGameScene onlineGameScene;
    // 对象是否存活
    private boolean alive;

    // 构造方法
    public AbstractObject(int x, int y, int width, int height, Image image, GameScene gameScene) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.image = image;
        this.gameScene = gameScene;
        this.alive = true;
    }
    public AbstractObject(int x, int y, int width, int height, Image image, VsGameScene vsGameScene) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.image = image;
        this.vsGameScene = vsGameScene;
        this.alive = true;
    }
    public AbstractObject(int x, int y, int width, int height, Image image, OnlineGameScene onlineGameScene) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.image = image;
        this.onlineGameScene = onlineGameScene;
        this.alive = true;
    }

    //得到矩形
    public Rectangle2D getRectangle(){
        return new Rectangle2D(x, y, width, height);
    }


    // 构造方法重载，允许不设置图像
    public AbstractObject(int x, int y, int width, int height, GameScene gameScene) {
        this(x, y, width, height,null, gameScene);
    }
    public AbstractObject(int x, int y, int width, int height, VsGameScene vsGameScene) {
        this(x, y, width, height,null, vsGameScene);
    }
    public AbstractObject(int x, int y, int width, int height, OnlineGameScene onlineGameScene) {
        this(x, y, width, height,null, onlineGameScene);
    }

    // Getter 和 Setter 方法
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public VsGameScene getVsGameScene() {
        return vsGameScene;
    }

    public GameScene getGameScene() {
        return gameScene;
    }

    public OnlineGameScene getOnlineGameScene() {
        return onlineGameScene;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    // 抽象方法，由子类实现
    public abstract void move();  // 移动方法
    public abstract boolean checkCollision(AbstractObject other);  // 碰撞检测
}

