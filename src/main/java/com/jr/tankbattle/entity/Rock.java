package com.jr.tankbattle.entity;

import com.jr.tankbattle.scene.GameScene;
import com.jr.tankbattle.util.Direction;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;

import static com.jr.tankbattle.controller.HomePage.status;

public class Rock extends AbstractObject{
    //构造函数
    public Rock(int x, int y, int width, int height, GameScene gameScene) {
        super(x, y, width, height, gameScene);
        super.setImage(new Image(this.getClass().getResourceAsStream("/com/jr/tankbattle/img/BrickWall_height.jpg")));
    }

    // 实现石头的移动逻辑
    @Override
    public void move() {

    }

    // 实现石头的绘制逻辑
    public void draw() {
        if(status == 1){
            getGameScene().getGraphicsContext().drawImage(super.getImage(), super.getX(), super.getY());
        }
        if(status == 2){
            getVsGameScene().getGraphicsContext().drawImage(super.getImage(), super.getX(), super.getY());
        }
    }
    // 实现石头与其他对象的碰撞检测逻辑
    @Override
    public boolean checkCollision(AbstractObject other) {
        if(other.isAlive()){
            return getRectangle().intersects(other.getRectangle());
        }
        return false;
    }

}
