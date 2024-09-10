package com.jr.tankbattle.entity;

import com.jr.tankbattle.scene.GameScene;
import com.jr.tankbattle.scene.OnlineGameScene;
import com.jr.tankbattle.scene.VsGameScene;
import com.jr.tankbattle.util.Direction;
import javafx.scene.image.Image;

import static com.jr.tankbattle.controller.HomePage.status;

public class Landmine extends AbstractObject {
    private boolean alive = true;
    private final Image image = new Image(this.getClass().getResourceAsStream("/com/jr/tankbattle/img/mine(1).png"));
    //构造函数
    public Landmine(int x, int y, int width, int height, GameScene gameScene) {
        super(x, y, width, height, gameScene);
    }
    public Landmine(int x, int y, int width, int height, VsGameScene vsGameScene) {
        super(x, y, width, height, vsGameScene);
    }
    public Landmine(int x, int y, int width, int height, OnlineGameScene onlineGameScene) {
        super(x, y, width, height, onlineGameScene);
    }

    // 实现地雷的移动逻辑
    @Override
    public void move() {

    }

    // 实现地雷的绘制逻辑
    public void draw() {
        if(status == 1){
            if(isAlive()){
                getGameScene().getGraphicsContext().drawImage(image, super.getX(), super.getY());
            }
            else {
                getGameScene().landmines.remove(this);
                getGameScene().explodes.add(new Explode(getX(), getY(), getGameScene()));
            }
        }
        if(status == 2){
            if(isAlive()){
                getVsGameScene().getGraphicsContext().drawImage(image, super.getX(), super.getY());
            }
            else {
                getVsGameScene().landmines.remove(this);
                getVsGameScene().explodes.add(new Explode(getX(), getY(), getVsGameScene()));
            }
        }
        if(status == 3){
            if(isAlive()){
                getOnlineGameScene().getGraphicsContext().drawImage(image, super.getX(), super.getY());
            }
            else {
                getOnlineGameScene().landmines.remove(this);
                getOnlineGameScene().explodes.add(new Explode(getX(), getY(), getOnlineGameScene()));
            }
        }
    }

    // 实现地雷与其他对象的碰撞检测逻辑
    @Override
    public boolean checkCollision(AbstractObject other) {
        if(alive){
            return getRectangle().intersects(other.getRectangle());
        }
        return false;
    }

}

