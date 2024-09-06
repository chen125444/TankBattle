package com.jr.tankbattle.entity;

import com.jr.tankbattle.controller.StartScr;
import com.jr.tankbattle.scene.GameScene;
import com.jr.tankbattle.scene.VsGameScene;
import com.jr.tankbattle.util.Direction;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.shape.Rectangle;

public class Tank2 extends AbstractObject {
    private Direction direction = Direction.UP;
    private boolean moving = false;
    private int width;
    private int height;
    //坦克速度
    private int speed;

    public Tank2(int x, int y, int width, int height, int speed, Image image, VsGameScene vsGameScene) {
        super(x, y, width, height, image, vsGameScene);
        this.speed = speed;
        this.width = width;
        this.height = height;
    }

    @Override
    public void move() {
        // 实现坦克的移动逻辑
        if (!moving) {
            return;

        }
        switch (direction) {
            case UP -> setY(getY() - speed);
            case DOWN -> setY(getY() + speed);
            case RIGHT -> setX(getX() + speed);
            case LEFT -> setX(getX() - speed);
        }

    }

    public void draw() {
        // 实现坦克的绘制逻辑
        switch (direction) {
            case UP -> getVsGameScene().getGraphicsContext().drawImage(super.getImage(), super.getX(), super.getY());
            case DOWN ->
                    getVsGameScene().getGraphicsContext().drawImage(new Image(this.getClass().getResourceAsStream("/com/jr/tankbattle/img/p1tankD.gif")), super.getX(), super.getY());
            case LEFT ->
                    getVsGameScene().getGraphicsContext().drawImage(new Image(this.getClass().getResourceAsStream("/com/jr/tankbattle/img/p1tankL.gif")), super.getX(), super.getY());
            case RIGHT ->
                    getVsGameScene().getGraphicsContext().drawImage(new Image(this.getClass().getResourceAsStream("/com/jr/tankbattle/img/p1tankR.gif")), super.getX(), super.getY());
        }
    }

    @Override
    public Rectangle getrectangle() {
        return new Rectangle(super.getWidth(), super.getHeight());
    }

    public void pressed(KeyCode keyCode) {
        switch (keyCode) {
            case UP:
                direction = Direction.UP;
                moving = true;
                break;
            case DOWN:
                direction = Direction.DOWN;
                moving = true;
                break;
            case LEFT:
                direction = Direction.LEFT;
                moving = true;
                break;
            case RIGHT:
                direction = Direction.RIGHT;
                moving = true;
                break;
            default:
                break;
        }
    }

    public void released(KeyCode keyCode) {
        switch (keyCode) {
            case UP:
            case DOWN:
            case LEFT:
            case RIGHT:
                moving = false;
                break;
            case ENTER:
                openFire();
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

    public void openFire() {
        switch (direction) {
            case UP:
                Bullet bullet0 = new Bullet(getX() + width / 2 - 5, getY() - 22, 22, 10, Direction.UP, 5, getVsGameScene());
                bullet0.draw2();
                getVsGameScene().bullets2.add(bullet0);
                break;
            case DOWN:
                Bullet bullet1 = new Bullet(getX() + width / 2 - 5, getY() + height, 22, 10, Direction.DOWN, 5, getVsGameScene());
                bullet1.draw2();
                getVsGameScene().bullets2.add(bullet1);
                break;
            case LEFT:
                Bullet bullet2 = new Bullet(getX() - 22, getY() + height / 2 - 5, 22, 10, Direction.LEFT, 5, getVsGameScene());
                bullet2.draw2();
                getVsGameScene().bullets2.add(bullet2);
                break;
            case RIGHT:
                Bullet bullet3 = new Bullet(getX() + width, getY() + height / 2 - 5, 22, 10, Direction.RIGHT, 5, getVsGameScene());
                bullet3.draw2();
                getVsGameScene().bullets2.add(bullet3);
        }
    }
}

