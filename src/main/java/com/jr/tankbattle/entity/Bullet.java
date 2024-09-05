package com.jr.tankbattle.entity;

import com.jr.tankbattle.controller.StartScr;
import com.jr.tankbattle.scene.GameScene;
import com.jr.tankbattle.util.Direction;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.shape.Rectangle;

public class Bullet extends AbstractObject{
    private int speed;//子弹速度
    private Direction direction;//子弹方向
    //构造函数
    public Bullet(int x, int y, int width, int height, Direction direction,int speed, Image image, GameScene gameScene) {
        super(x, y, width, height, image,gameScene);
        this.speed = speed;
        this.direction = direction;
    }
    // 实现子弹的移动逻辑
    @Override
    public void move() {
        switch (direction){
            case UP -> setY(getY() - speed);
            case DOWN -> setY(getY() + speed);
            case RIGHT -> setX(getX() + speed);
            case LEFT -> setX(getX() - speed);
        }
    }
    // 实现子弹的绘制逻辑
    public void draw() {

    }
    // 获取子弹的轮廓
    @Override
    public Rectangle getrectangle() {
        return new Rectangle(super.getWidth(),super.getHeight());
    }
    // 实现子弹与其他对象的碰撞检测逻辑
    @Override
    public boolean checkCollision(AbstractObject other) {
        return Math.abs(super.getX() - other.getX()) < Math.min(super.getWidth(), other.getWidth()) ||
                Math.abs(super.getY() - other.getY()) < Math.min(super.getHeight(), other.getHeight());
    }
    // 获取子弹的方向
    public Direction getDirection() {
        return direction;
    }
    // 设置子弹的方向
    public void setDirection(Direction direction) {
        this.direction = direction;
    }
}


