package com.jr.tankbattle.entity;

import com.jr.tankbattle.controller.StartScr;
import com.jr.tankbattle.scene.GameScene;
import com.jr.tankbattle.util.Direction;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.shape.Rectangle;

public class Tank extends AbstractObject{
    private Direction direction = Direction.UP;
    private boolean moving = false;
    //坦克速度
    private int speed;
    public Tank(int x, int y, int width, int height, int speed, Image image, GameScene gameScene) {
        super(x, y, width, height, image, gameScene);
        this.speed = speed;
    }

    @Override
    public void move() {
        // 实现坦克的移动逻辑
        if(!moving){
            return;

        }
        switch (direction){
            case UP -> setY(getY() - speed);
            case DOWN -> setY(getY() + speed);
            case RIGHT -> setX(getX() + speed);
            case LEFT -> setX(getX() - speed);
        }

    }

    public void draw(Image downImage, Image leftImage, Image rightImage) {
        // 实现坦克的绘制逻辑
        switch (direction){
            case UP -> getGameScene().getGraphicsContext().drawImage(super.getImage(), super.getX(), super.getY());
            case DOWN -> getGameScene().getGraphicsContext().drawImage(downImage, super.getX(), super.getY());
            case LEFT -> getGameScene().getGraphicsContext().drawImage(leftImage, super.getX(), super.getY());
            case RIGHT -> getGameScene().getGraphicsContext().drawImage(rightImage, super.getX(), super.getY());
        }
    }

    @Override
    public Rectangle getrectangle() {
        return new Rectangle(super.getWidth(),super.getHeight());
    }

    public void pressed(KeyCode keyCode){
        switch (keyCode) {
            case W:
                direction = Direction.UP;
                moving = true;
                break;
            case S:
                direction = Direction.DOWN;
                moving = true;
                break;
            case A:
                direction = Direction.LEFT;
                moving = true;
                break;
            case D:
                direction = Direction.RIGHT;
                moving = true;
                break;
            default:
                break;
        }
    }

    public void released(KeyCode keyCode){
        switch (keyCode){
            case W:
            case S:
            case A:
            case D:
                moving = false;
                break;
            default:
                break;
        }
    }

    @Override
    public boolean checkCollision(AbstractObject other) {
        // 实现坦克与其他对象的碰撞检测逻辑
        return Math.abs(super.getX() - other.getX()) < Math.min(super.getWidth(), other.getWidth()) ||
                Math.abs(super.getY() - other.getY()) < Math.min(super.getHeight(), other.getHeight());
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }
}
