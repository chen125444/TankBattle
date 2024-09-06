package com.jr.tankbattle.entity;

import com.jr.tankbattle.scene.GameScene;
import com.jr.tankbattle.util.Direction;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;

public class Bomb extends AbstractObject {
    //构造函数
    public Bomb(int x, int y, int width, int height, Direction direction, int speed, Image image, GameScene gameScene) {
        super(x, y, width, height, image, gameScene);
    }

    // 实现炸弹的移动逻辑
    @Override
    public void move() {

    }

    // 实现炸弹的绘制逻辑
    public void draw() {

    }

    // 实现炸弹与其他对象的碰撞检测逻辑
    @Override
    public boolean checkCollision(AbstractObject other) {
        return Math.abs(super.getX() - other.getX()) < Math.min(super.getWidth(), other.getWidth()) ||
                Math.abs(super.getY() - other.getY()) < Math.min(super.getHeight(), other.getHeight());
    }

}

