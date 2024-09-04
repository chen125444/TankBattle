package com.jr.tankbattle.entity;

import com.jr.tankbattle.controller.StartScr;
import javafx.scene.image.Image;

public class Tank extends AbstractObject{
    private Direction direction = Direction.UP;
    //坦克速度
    private int speed;
    //坦克尺寸
    private int width;
    private int height;
    public Tank(int x, int y, int width, int height, int speed, Image image, StartScr startScr) {
        super(x, y, image, startScr);
        this.width = width;
        this.height = height;
        this.speed = speed;
    }

    @Override
    public void move() {
        // 实现坦克的移动逻辑
    }

    @Override
    public void draw() {
        // 实现坦克的绘制逻辑
    }

    @Override
    public void checkCollision(AbstractObject other) {
        // 实现坦克与其他对象的碰撞检测逻辑
    }
}
