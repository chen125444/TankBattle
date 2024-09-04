package com.jr.tankbattle.entity;

import com.jr.tankbattle.controller.StartScr;
import com.jr.tankbattle.util.Direction;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;

public class Tank extends AbstractObject{
    private Direction direction = Direction.UP;
    //坦克速度
    private int speed;
    public Tank(int x, int y, int width, int height, int speed, Image image, StartScr startScr) {
        super(x, y, width, height, image, startScr);
        this.speed = speed;
    }

    @Override
    public void move() {
        // 实现坦克的移动逻辑
        switch (direction){
            case UP -> setY(getY() - speed);
            case DOWN -> setY(getY() + speed);
            case RIGHT -> setX(getX() - speed);
            case LEFT -> setX(getX() + speed);
        }
    }

    @Override
    public void draw() {
        // 实现坦克的绘制逻辑
    }

    @Override
    public Rectangle getrectangle() {
        return new Rectangle(super.getWidth(),super.getHeight());
    }

    @Override
    public void checkCollision(AbstractObject other) {
        // 实现坦克与其他对象的碰撞检测逻辑
        if(Math.abs(super.getX() - other.getX()) < Math.min(super.getWidth(), other.getWidth())||
                Math.abs(super.getY() - other.getY()) < Math.min(super.getHeight(), other.getHeight())){
            super.setAlive(false);
            other.setAlive(false);
        }
    }
}
