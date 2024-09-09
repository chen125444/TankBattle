package com.jr.tankbattle.entity;

import com.jr.tankbattle.controller.StartScr;
import com.jr.tankbattle.scene.GameScene;
import com.jr.tankbattle.scene.VsGameScene;
import com.jr.tankbattle.util.Direction;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;

public class Tank2 extends AbstractObject {
    private Direction direction = Direction.UP;
    private boolean moving = false;
    private int width;
    private int height;
    //坦克速度
    private int speed;
    private List<Direction> directions = new ArrayList<>();
    private Image downImage = new Image(this.getClass().getResourceAsStream("/com/jr/tankbattle/img/enemy2D.png"));
    private Image leftImage = new Image(this.getClass().getResourceAsStream("/com/jr/tankbattle/img/enemy2L.png"));
    private Image rightImage = new Image(this.getClass().getResourceAsStream("/com/jr/tankbattle/img/enemy2R.png"));

    public Tank2(int x, int y, int width, int height, int speed, VsGameScene vsGameScene) {
        super(x, y, width, height, vsGameScene);
        super.setImage(new Image(this.getClass().getResourceAsStream("/com/jr/tankbattle/img/enemy2U.png")));
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
                    getVsGameScene().getGraphicsContext().drawImage(getDownImage(), super.getX(), super.getY());
            case LEFT ->
                    getVsGameScene().getGraphicsContext().drawImage(getLeftImage(), super.getX(), super.getY());
            case RIGHT ->
                    getVsGameScene().getGraphicsContext().drawImage(getRightImage(), super.getX(), super.getY());
        }
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

    public boolean checkCollision(AbstractObject other) {
        // 实现坦克与其他对象的碰撞检测逻辑
        if(other.isAlive()){
            return getRectangle().intersects(other.getRectangle());
        }
        return false;
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
    public void collisionRocks(List<Rock> rocks) {
        // 实现玩家与石头的碰撞检测逻辑
        for(int i = 0; i < rocks.size(); i++) {
            Rock rock = rocks.get(i);
            if(checkCollision(rock)) {
                directions.add(direction);
                switch (direction) {
                    case UP -> setY(getY() + speed);
                    case DOWN -> setY(getY() - speed);
                    case LEFT -> setX(getX() + speed);
                    case RIGHT -> setX(getX() - speed);
                }
            }
            else directions.remove(direction);
        }
    }
    public void collisionTrees(List<Tree> trees) {
        // 实现玩家与树丛的碰撞检测逻辑
        for(int i = 0; i < trees.size(); i++) {
            Tree tree = trees.get(i);
            if(checkCollision(tree)) {
                directions.add(direction);
                switch (direction) {
                    case UP -> setY(getY() + speed);
                    case DOWN -> setY(getY() - speed);
                    case LEFT -> setX(getX() + speed);
                    case RIGHT -> setX(getX() - speed);
                }
            }
            else directions.remove(direction);
        }
    }
    //坦克之間的碰撞
    public void collisionTank(Tank tank){
        if(checkCollision(tank)){
            switch (direction) {
                case UP -> tank.setY(tank.getY() - speed);
                case DOWN -> tank.setY(tank.getY() + speed);
                case LEFT -> tank.setX(tank.getX() - speed);
                case RIGHT -> tank.setX(tank.getX() + speed);
            }
        }
        else return;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public Image getDownImage() {
        return downImage;
    }

    public Image getLeftImage() {
        return leftImage;
    }

    public Image getRightImage() {
        return rightImage;
    }

    public void openFire() {
        switch (direction) {
            case UP:
                Bullet bullet0 = new Bullet(getX() + width / 2 - 5, getY() - 22, 22, 10, Direction.UP, 5, getVsGameScene());
                bullet0.draw();
                getVsGameScene().bullets.add(bullet0);
                break;
            case DOWN:
                Bullet bullet1 = new Bullet(getX() + width / 2 - 5, getY() + height, 22, 10, Direction.DOWN, 5, getVsGameScene());
                bullet1.draw();
                getVsGameScene().bullets.add(bullet1);
                break;
            case LEFT:
                Bullet bullet2 = new Bullet(getX() - 22, getY() + height / 2 - 5, 22, 10, Direction.LEFT, 5, getVsGameScene());
                bullet2.draw();
                getVsGameScene().bullets.add(bullet2);
                break;
            case RIGHT:
                Bullet bullet3 = new Bullet(getX() + width, getY() + height / 2 - 5, 22, 10, Direction.RIGHT, 5, getVsGameScene());
                bullet3.draw();
                getVsGameScene().bullets.add(bullet3);
        }
    }
}

