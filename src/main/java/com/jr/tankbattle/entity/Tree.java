package com.jr.tankbattle.entity;

import com.jr.tankbattle.scene.GameScene;
import com.jr.tankbattle.scene.VsGameScene;
import javafx.scene.image.Image;

import java.util.List;

public class Tree extends AbstractObject{
    //构造函数
    public Tree(int x, int y, int width, int height, GameScene gameScene) {
        super(x, y, width, height, gameScene);
        super.setImage(new Image(this.getClass().getResourceAsStream("/com/jr/tankbattle/img/grass.png")));
    }
    public Tree(int x, int y, int width, int height, VsGameScene vsGameScene) {
        super(x, y, width, height,vsGameScene);
        super.setImage(new Image(this.getClass().getResourceAsStream("/com/jr/tankbattle/img/grass.png")));
    }


    // 实现树丛的移动逻辑
    @Override
    public void move() {

    }

    // 实现树丛的绘制逻辑
    public void draw() {
        getGameScene().getGraphicsContext().drawImage(new Image(this.getClass().getResourceAsStream("/com/jr/tankbattle/img/grass.png")), super.getX(), super.getY());
    }
    public void draw2() {
        getVsGameScene().getGraphicsContext().drawImage(new Image(this.getClass().getResourceAsStream("/com/jr/tankbattle/img/grass.png")), super.getX(), super.getY());
    }

    // 实现树丛与其他对象的碰撞检测逻辑
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
                setAlive(false);
                bullet.setAlive(false);
            }
        }
    }
}
