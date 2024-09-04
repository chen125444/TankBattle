package com.jr.tankbattle.entity;

import com.jr.tankbattle.controller.StartScr;
import javafx.scene.image.Image;

public abstract class AbstractObject {
    // 坐标
    private int x;
    private int y;
    // 图像
    private Image image;
    // 界面
    private StartScr startScr;
    // 对象是否存活
    private boolean alive;

    // 构造方法
    public AbstractObject(int x, int y, Image image, StartScr startScr) {
        this.x = x;
        this.y = y;
        this.image = image;
        this.startScr = startScr;
        this.alive = true;
    }

    // 构造方法重载，允许不设置图像
    public AbstractObject(int x, int y, StartScr startScr) {
        this(x, y, null, startScr);
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

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public StartScr getStartScr() {
        return startScr;
    }

    public void setStartScr(StartScr startScr) {
        this.startScr = startScr;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    // 抽象方法，由子类实现
    public abstract void move();  // 移动方法
    public abstract void draw();  // 绘制对象方法
    public abstract void checkCollision(AbstractObject other);  // 碰撞检测
}

