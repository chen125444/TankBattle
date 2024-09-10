package com.jr.tankbattle.entity;

import com.jr.tankbattle.scene.GameScene;
import com.jr.tankbattle.util.Direction;
import javafx.scene.image.Image;

public class Landmine extends AbstractObject {
    //构造函数
    public Landmine(int x, int y, int width, int height, Direction direction, int speed, Image image, GameScene gameScene) {
        super(x, y, width, height, image, gameScene);
    }

    // 实现地雷的移动逻辑
    @Override
    public void move() {

    }

    // 实现地雷的绘制逻辑
    public void draw() {

    }

    // 实现地雷与其他对象的碰撞检测逻辑
    @Override
    public boolean checkCollision(AbstractObject other) {
        return getRectangle().intersects(other.getRectangle());
    }

}

