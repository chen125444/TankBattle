package com.jr.tankbattle.entity;

import com.jr.tankbattle.scene.GameScene;
import com.jr.tankbattle.util.Direction;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;

import static com.jr.tankbattle.controller.HomePage.status;

public class Sheild extends AbstractObject{
    private boolean alive = true;
    private boolean moving = false;
    private final Image image = new Image(this.getClass().getResourceAsStream("/com/jr/tankbattle/img/Sheild(1).png"));
    //构造函数
    public Sheild(int x, int y, int width, int height, Direction direction, int speed, Image image, GameScene gameScene) {
        super(x, y, width, height, image, gameScene);
    }



    // 实现盾牌的移动逻辑
    @Override
    public void move() {

    }

    // 实现盾牌的绘制逻辑
    public void draw() {
        if(!moving && alive){
            if(status == 1) {
                getGameScene().getGraphicsContext().drawImage(image, super.getX(), super.getY());
            }
            if (status == 2){
                getVsGameScene().getGraphicsContext().drawImage(image, super.getX(), super.getY());
            }
        }
    }
    public void draw(Tank tank) {
        if(moving && alive){
            if(status == 1) {
                getGameScene().getGraphicsContext().drawImage(image, tank.getX(), tank.getY());
            }
            if (status == 2){
                getVsGameScene().getGraphicsContext().drawImage(image, tank.getX(), tank.getY());
            }
        }
    }
    public void draw(Tank2 tank) {
        if(moving && alive){
            if(status == 1) {
                getGameScene().getGraphicsContext().drawImage(image, tank.getX(), tank.getY());
            }
            if (status == 2){
                getVsGameScene().getGraphicsContext().drawImage(image, tank.getX(), tank.getY());
            }
        }
    }
    public void draw(AiTank tank) {
        if(moving && alive){
            if(status == 1) {
                getGameScene().getGraphicsContext().drawImage(image, tank.getX(), tank.getY());
            }
            if (status == 2){
                getVsGameScene().getGraphicsContext().drawImage(image, tank.getX(), tank.getY());
            }
        }
    }


    // 实现盾牌与其他对象的碰撞检测逻辑
    @Override
    public boolean checkCollision(AbstractObject other) {
        return getRectangle().intersects(other.getRectangle());
    }

    @Override
    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }
}
