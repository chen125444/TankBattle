package com.jr.tankbattle.entity;

import com.jr.tankbattle.scene.GameScene;
import com.jr.tankbattle.scene.VsGameScene;
import com.jr.tankbattle.util.Direction;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;

import static com.jr.tankbattle.controller.HomePage.status;

public class Tank extends AbstractObject {
    private Direction direction = Direction.UP;
    private boolean moving = false;
    private int width;
    private int height;
    //坦克速度
    private int speed;
    private List<Direction> directions = new ArrayList<>();

    public Tank(int x, int y, int width, int height, int speed, Image image, GameScene gameScene) {
        super(x, y, width, height, image, gameScene);
        this.speed = speed;
        this.width = width;
        this.height = height;
    }
    public Tank(int x, int y, int width, int height, int speed, Image image, VsGameScene vsGameScene) {
        super(x, y, width, height, image, vsGameScene);
        this.speed = speed;
        this.width = width;
        this.height = height;
    }

    @Override
    public void move() {
        // 实现坦克的移动逻辑
        if (!moving||directions.contains(direction)) {
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
            case UP -> getGameScene().getGraphicsContext().drawImage(super.getImage(), super.getX(), super.getY());
            case DOWN ->
                    getGameScene().getGraphicsContext().drawImage(new Image(this.getClass().getResourceAsStream("/com/jr/tankbattle/img/p1tankD.gif")), super.getX(), super.getY());
            case LEFT ->
                    getGameScene().getGraphicsContext().drawImage(new Image(this.getClass().getResourceAsStream("/com/jr/tankbattle/img/p1tankL.gif")), super.getX(), super.getY());
            case RIGHT ->
                    getGameScene().getGraphicsContext().drawImage(new Image(this.getClass().getResourceAsStream("/com/jr/tankbattle/img/p1tankR.gif")), super.getX(), super.getY());
        }
    }
    public void draw2() {
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

    public void pressed(KeyCode keyCode) {
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

    public void released(KeyCode keyCode) {
        switch (keyCode) {
            case W:
            case S:
            case A:
            case D:
                moving = false;
                break;
            case J:
                if(status == 1){
                    openFire();
                }
                if (status == 2) {
                    openFire2();
                }
                break;
            default:
                break;
        }
    }

    public boolean checkCollision(AbstractObject other) {
        // 实现坦克与其他对象的碰撞检测逻辑
        return getRectangle().intersects(other.getRectangle());

    }
    public void collisionBullet(List<Bullet> bullets) {
        // 实现坦克与子弹的碰撞检测逻辑
        for(int i = 0; i < bullets.size(); i++) {
            Bullet bullet = bullets.get(i);
            if(checkCollision(bullet)) {
                setAlive(false);
                bullet.setAlive(false);
            }
        }
    }
    public void collisionPlayer(List<AiTank> aiTanks) {
        // 实现玩家与Ai坦克的碰撞检测逻辑
        for(int i = 0; i < aiTanks.size(); i++) {
            AiTank aiTank = aiTanks.get(i);
            if(checkCollision(aiTank)) {
                directions.add(direction);
                switch (direction) {
                    case UP -> setY(getY() + speed);
                    case DOWN -> setY(getY() - speed);
                    case LEFT -> setX(getX() + speed);
                    case RIGHT -> setX(getX() - speed);
                    }
                switch (aiTank.getDirection()) {
                    case UP -> setY(getY() - speed);
                    case DOWN -> setY(getY() + speed);
                    case LEFT -> setX(getX() - speed);
                    case RIGHT -> setX(getX() + speed);
                }
                }
            else directions.remove(direction);
        }
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void openFire() {
        switch (direction) {
            case UP:
                Bullet bullet0 = new Bullet(getX() + width / 2 - 5, getY() - 22, 22, 10, Direction.UP, 5, getGameScene());
                bullet0.draw();
                getGameScene().bullets.add(bullet0);
                break;
            case DOWN:
                Bullet bullet1 = new Bullet(getX() + width / 2 - 5, getY() + height, 22, 10, Direction.DOWN, 5, getGameScene());
                bullet1.draw();
                getGameScene().bullets.add(bullet1);
                break;
            case LEFT:
                Bullet bullet2 = new Bullet(getX() - 22, getY() + height / 2 - 5, 22, 10, Direction.LEFT, 5, getGameScene());
                bullet2.draw();
                getGameScene().bullets.add(bullet2);
                break;
            case RIGHT:
                Bullet bullet3 = new Bullet(getX() + width, getY() + height / 2 - 5, 22, 10, Direction.RIGHT, 5, getGameScene());
                bullet3.draw();
                getGameScene().bullets.add(bullet3);
        }
    }
    public void openFire2() {
        switch (direction) {
            case UP:
                Bullet bullet0 = new Bullet(getX() + width / 2 - 5, getY() - 22, 22, 10, Direction.UP, 5, getVsGameScene());
                bullet0.draw2();
                getVsGameScene().bullets.add(bullet0);
                break;
            case DOWN:
                Bullet bullet1 = new Bullet(getX() + width / 2 - 5, getY() + height, 22, 10, Direction.DOWN, 5, getVsGameScene());
                bullet1.draw2();
                getVsGameScene().bullets.add(bullet1);
                break;
            case LEFT:
                Bullet bullet2 = new Bullet(getX() - 22, getY() + height / 2 - 5, 22, 10, Direction.LEFT, 5, getVsGameScene());
                bullet2.draw2();
                getVsGameScene().bullets.add(bullet2);
                break;
            case RIGHT:
                Bullet bullet3 = new Bullet(getX() + width, getY() + height / 2 - 5, 22, 10, Direction.RIGHT, 5, getVsGameScene());
                bullet3.draw2();
                getVsGameScene().bullets.add(bullet3);
        }
    }
}

