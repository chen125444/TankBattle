package com.jr.tankbattle.entity;

import com.jr.tankbattle.scene.GameScene;
import com.jr.tankbattle.scene.OnlineGameScene;
import com.jr.tankbattle.scene.VsGameScene;
import javafx.scene.image.Image;
import java.util.List;

import static com.jr.tankbattle.controller.HomePage.status;

public class Rock extends AbstractObject{
    private Image rockImage = new Image(this.getClass().getResourceAsStream("/com/jr/tankbattle/img/walls(1).png"));
    //构造函数
    public Rock(int x, int y, int width, int height, GameScene gameScene) {
        super(x, y, width, height, gameScene);
    }
    public Rock(int x, int y, int width, int height, VsGameScene vsGameScene) {
        super(x, y, width, height, vsGameScene);
    }
    public Rock(int x, int y, int width, int height, OnlineGameScene onlineGameScene) {
        super(x, y, width, height,onlineGameScene);
    }

    // 实现石头的移动逻辑
    @Override
    public void move() {

    }

    // 实现石头的绘制逻辑
    public void draw() {
        if(status == 1){
            getGameScene().getGraphicsContext().drawImage(rockImage, super.getX(), super.getY());
        }
        if(status == 2){
            getVsGameScene().getGraphicsContext().drawImage(rockImage, super.getX(), super.getY());
        }
        if(status == 3){
            getOnlineGameScene().getGraphicsContext().drawImage(rockImage, super.getX(), super.getY());
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
    public void collisionBullet(List<Bullet> bullets) {
        // 实现与子弹的碰撞检测逻辑
        for(int i = 0; i < bullets.size(); i++) {
            Bullet bullet = bullets.get(i);
            if(checkCollision(bullet)) {
                bullet.setAlive(false);
                setAlive(false);
            }
        }
    }
}
