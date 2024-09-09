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
    private Image downImage = new Image(this.getClass().getResourceAsStream("/com/jr/tankbattle/img/enemy1D.png"));
    private Image leftImage = new Image(this.getClass().getResourceAsStream("/com/jr/tankbattle/img/enemy1L.png"));
    private Image rightImage = new Image(this.getClass().getResourceAsStream("/com/jr/tankbattle/img/enemy1R.png"));
    private List<Direction> directions = new ArrayList<>();
    private int lives =4;

    public Tank(int x, int y, int width, int height, int speed, GameScene gameScene) {
        super(x, y, width, height, gameScene);
        super.setImage(new Image(this.getClass().getResourceAsStream("/com/jr/tankbattle/img/enemy1U.png")));
        this.speed = speed;
        this.width = width;
        this.height = height;
    }
    public Tank(int x, int y, int width, int height, int speed, VsGameScene vsGameScene) {
        super(x, y, width, height, vsGameScene);
        super.setImage(new Image(this.getClass().getResourceAsStream("/com/jr/tankbattle/img/enemy1U.png")));
        this.speed = speed;
        this.width = width;
        this.height = height;
    }

    @Override
    public void move() {
        // 实现坦克的移动逻辑
        if (!moving|| directions.contains(direction)) {
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
        if(status == 1) {
            switch (direction) {
                case UP -> getGameScene().getGraphicsContext().drawImage(super.getImage(), super.getX(), super.getY());
                case DOWN -> getGameScene().getGraphicsContext().drawImage(downImage, super.getX(), super.getY());
                case LEFT -> getGameScene().getGraphicsContext().drawImage(leftImage, super.getX(), super.getY());
                case RIGHT -> getGameScene().getGraphicsContext().drawImage(rightImage, super.getX(), super.getY());
            }
        }
        if (status == 2){
            switch (direction) {
                case UP -> getVsGameScene().getGraphicsContext().drawImage(super.getImage(), super.getX(), super.getY());
                case DOWN ->
                        getVsGameScene().getGraphicsContext().drawImage(downImage, super.getX(), super.getY());
                case LEFT ->
                        getVsGameScene().getGraphicsContext().drawImage(leftImage, super.getX(), super.getY());
                case RIGHT ->
                        getVsGameScene().getGraphicsContext().drawImage(rightImage, super.getX(), super.getY());
            }
        }
    }

    public void pressed(KeyCode keyCode) {
            switch (keyCode) {
                case W:
                    back(direction);
                    direction = Direction.UP;
                    moving = true;
                    break;
                case S:
                    back(direction);
                    direction = Direction.DOWN;
                    moving = true;
                    break;
                case A:
                    back(direction);
                    direction = Direction.LEFT;
                    moving = true;
                    break;
                case D:
                    back(direction);
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
                openFire();
//                System.out.println("fire");
                break;
            default:
                break;
        }
    }

    public boolean checkCollision(AbstractObject other) {
        // 实现坦克与其他对象的碰撞检测逻辑
        if (other.isAlive()){
            return getRectangle().intersects(other.getRectangle());
        }
        return false;
    }
    public void collisionBullet(List<Bullet> bullets) {
        // 实现坦克与子弹的碰撞检测逻辑
        for(int i = 0; i < bullets.size(); i++) {
            Bullet bullet = bullets.get(i);
            if(checkCollision(bullet)) {
                lives--;
                if(lives == 0) {
                    setAlive(false);
                }
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
                    case UP -> aiTank.setY(aiTank.getY() - speed);
                    case DOWN -> aiTank.setY(aiTank.getY() + speed);
                    case LEFT -> aiTank.setX(aiTank.getX() - speed);
                    case RIGHT -> aiTank.setX(aiTank.getX() + speed);
                    }
                }
            else directions.remove(direction);
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
            else{
                directions.remove(direction);
            }
        }
    }
    //坦克之間的碰撞
    public void collisionTank(Tank2 tank){
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

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void back(Direction direction) {
        switch (direction) {
            case UP -> setY(getY() + speed);
            case DOWN -> setY(getY() - speed);
            case LEFT -> setX(getX() + speed);
            case RIGHT -> setX(getX() - speed);
        }
    }
    public void openFire() {
        if(status == 1){
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
        if(status == 2){
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
}

