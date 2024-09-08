package com.jr.tankbattle.entity;

import com.jr.tankbattle.controller.StartScr;
import com.jr.tankbattle.scene.GameScene;
import com.jr.tankbattle.scene.VsGameScene;
import com.jr.tankbattle.util.Direction;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.shape.Rectangle;

import java.util.List;

public class Bullet extends AbstractObject{
    private int speed;//子弹速度
    private Direction direction;//子弹方向
    //构造函数
    public Bullet(int x, int y, int width, int height, Direction direction,int speed, GameScene gameScene) {
        super(x, y, width, height,gameScene);

        this.speed = speed;
        this.direction = direction;
    }
    public Bullet(int x, int y, int width, int height, Direction direction,int speed, VsGameScene vsGameScene) {
        super(x, y, width, height,vsGameScene);
        this.speed = speed;
        this.direction = direction;
    }
    // 实现子弹的移动逻辑
    @Override
    public void move() {
        //边界检测
        if(getX()<=0){
            setAlive(false);
        }
        if(getY()<=0) {
            setAlive(false);
        }
        if(getX()>=1020){
            setAlive(false);
        }
        if(getY()>=660) {
            setAlive(false);
        }
        switch (direction){
            case UP -> setY(getY() - speed);
            case DOWN -> setY(getY() + speed);
            case RIGHT -> setX(getX() + speed);
            case LEFT -> setX(getX() - speed);
        }
    }
    // 实现子弹的绘制逻辑
    public void draw() {
        if(isAlive()){
            switch (direction){
                case UP -> getGameScene().getGraphicsContext().drawImage(new Image(this.getClass().getResourceAsStream("/com/jr/tankbattle/img/bulletGreenUp.png")), super.getX(), super.getY());
                case DOWN -> getGameScene().getGraphicsContext().drawImage(new Image(this.getClass().getResourceAsStream("/com/jr/tankbattle/img/bulletGreenDown.png")), super.getX(), super.getY());
                case LEFT -> getGameScene().getGraphicsContext().drawImage(new Image(this.getClass().getResourceAsStream("/com/jr/tankbattle/img/bulletGreenLeft.png")), super.getX(), super.getY());
                case RIGHT -> getGameScene().getGraphicsContext().drawImage(new Image(this.getClass().getResourceAsStream("/com/jr/tankbattle/img/bulletGreenRight.png")), super.getX(), super.getY());
            }
        }
        else {
            getGameScene().bullets.remove(this);
            getGameScene().explodes.add(new Explode(getX(), getY(), getGameScene()));
        }
    }
    public void draw2() {
        if(isAlive()){
            switch (direction){
                case UP -> getVsGameScene().getGraphicsContext().drawImage(new Image(this.getClass().getResourceAsStream("/com/jr/tankbattle/img/bulletGreenUp.png")), super.getX(), super.getY());
                case DOWN -> getVsGameScene().getGraphicsContext().drawImage(new Image(this.getClass().getResourceAsStream("/com/jr/tankbattle/img/bulletGreenDown.png")), super.getX(), super.getY());
                case LEFT -> getVsGameScene().getGraphicsContext().drawImage(new Image(this.getClass().getResourceAsStream("/com/jr/tankbattle/img/bulletGreenLeft.png")), super.getX(), super.getY());
                case RIGHT -> getVsGameScene().getGraphicsContext().drawImage(new Image(this.getClass().getResourceAsStream("/com/jr/tankbattle/img/bulletGreenRight.png")), super.getX(), super.getY());
            }
        }
        else {
            getGameScene().bullets.remove(this);
            getGameScene().explodes.add(new Explode(getX(), getY(), getGameScene()));
        }
    }

    // 实现子弹与其他对象的碰撞检测逻辑
    @Override
    public boolean checkCollision(AbstractObject other) {
        return getRectangle().intersects(other.getRectangle());
    }

    public void collisionBullet(List<Bullet> bullets) {
        // 实现子弹与子弹的碰撞检测逻辑
        for(int i = 0; i < bullets.size(); i++) {
            Bullet bullet = bullets.get(i);
            if(bullet!=this) {
                if (checkCollision(bullet)) {
                    setAlive(false);
                    bullet.setAlive(false);
                }
            }
        }
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


