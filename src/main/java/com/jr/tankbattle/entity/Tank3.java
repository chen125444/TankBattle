package com.jr.tankbattle.entity;

import com.jr.tankbattle.controller.Account;
import com.jr.tankbattle.scene.GameScene;
import com.jr.tankbattle.scene.OnlineGameScene;
import com.jr.tankbattle.scene.VsGameScene;
import com.jr.tankbattle.util.Direction;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;

import static com.jr.tankbattle.controller.HomePage.status;
import static java.lang.Math.abs;

public class Tank3 extends AbstractObject implements Runnable {
    private Direction direction = Direction.UP;
    private boolean moving = false;
    private boolean canFire = true;
    private int width;
    private int height;
    private int lives =4;
    //坦克速度
    private int speed;

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    private String playerId;

    public void setTankType(TankType tankType) {
        this.tankType = tankType;
    }

    private TankType tankType;
    private Image upImage = new Image(this.getClass().getResourceAsStream("/com/jr/tankbattle/img/enemy1U.png"));
    private Image downImage = new Image(this.getClass().getResourceAsStream("/com/jr/tankbattle/img/enemy1D.png"));
    private Image leftImage = new Image(this.getClass().getResourceAsStream("/com/jr/tankbattle/img/enemy1L.png"));
    private Image rightImage = new Image(this.getClass().getResourceAsStream("/com/jr/tankbattle/img/enemy1R.png"));
    private List<Direction> directions = new ArrayList<>();

    public Tank3(int x, int y, int width, int height, int speed, OnlineGameScene onlineGameScene) {
        super(x, y, width, height, onlineGameScene);
        super.setImage(upImage);
        this.speed = speed;
        this.width = width;
        this.height = height;
    }
    public Tank3(int x, int y, int width, int height, int speed, OnlineGameScene onlineGameScene,TankType tankType,String playerId) {
        super(x, y, width, height, onlineGameScene);
        super.setImage(upImage);
        this.speed = speed;
        this.width = width;
        this.height = height;
        this.tankType = tankType;
        this.playerId = playerId;
    }

    public Tank3() {
        super();
    }

    public enum TankType {
        TYPE1, TYPE2, TYPE3, TYPE4
    }

    private void loadImages() {
        switch (tankType) {
            case TYPE1:
                upImage = new Image(this.getClass().getResourceAsStream("/com/jr/tankbattle/img/enemy1U.png"));
                downImage = new Image(this.getClass().getResourceAsStream("/com/jr/tankbattle/img/enemy1D.png"));
                leftImage = new Image(this.getClass().getResourceAsStream("/com/jr/tankbattle/img/enemy1L.png"));
                rightImage = new Image(this.getClass().getResourceAsStream("/com/jr/tankbattle/img/enemy1R.png"));
                break;
            case TYPE2:
                upImage = new Image(this.getClass().getResourceAsStream("/com/jr/tankbattle/img/enemy2U.png"));
                downImage = new Image(this.getClass().getResourceAsStream("/com/jr/tankbattle/img/enemy2D.png"));
                leftImage = new Image(this.getClass().getResourceAsStream("/com/jr/tankbattle/img/enemy2L.png"));
                rightImage = new Image(this.getClass().getResourceAsStream("/com/jr/tankbattle/img/enemy2R.png"));
                break;
            case TYPE3:
                upImage = new Image(this.getClass().getResourceAsStream("/com/jr/tankbattle/img/enemy3U.png"));
                downImage = new Image(this.getClass().getResourceAsStream("/com/jr/tankbattle/img/enemy3D.png"));
                leftImage = new Image(this.getClass().getResourceAsStream("/com/jr/tankbattle/img/enemy3L.png"));
                rightImage = new Image(this.getClass().getResourceAsStream("/com/jr/tankbattle/img/enemy3R.png"));
                break;
            case TYPE4:
                upImage = new Image(this.getClass().getResourceAsStream("/com/jr/tankbattle/img/enemy4U.png"));
                downImage = new Image(this.getClass().getResourceAsStream("/com/jr/tankbattle/img/enemy4D.png"));
                leftImage = new Image(this.getClass().getResourceAsStream("/com/jr/tankbattle/img/enemy4L.png"));
                rightImage = new Image(this.getClass().getResourceAsStream("/com/jr/tankbattle/img/enemy4R.png"));
                break;
        }
    }


    @Override
    public void move() {
        // 实现坦克的移动逻辑
        if (!moving || directions.contains(direction)) {
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
        switch (direction) {
            case UP ->
                    getOnlineGameScene().getGraphicsContext().drawImage(super.getImage(), super.getX(), super.getY());
            case DOWN -> getOnlineGameScene().getGraphicsContext().drawImage(downImage, super.getX(), super.getY());
            case LEFT -> getOnlineGameScene().getGraphicsContext().drawImage(leftImage, super.getX(), super.getY());
            case RIGHT -> getOnlineGameScene().getGraphicsContext().drawImage(rightImage, super.getX(), super.getY());
        }

    }

    public void pressed(KeyCode keyCode) {
        if (!playerId.equals(Account.uid)) {
            return; // 不是当前玩家的坦克，不响应键盘事件
        }
        switch (keyCode) {
            case W:
                if(direction!=Direction.UP)back(direction);
                direction = Direction.UP;
                moving = true;
                break;
            case S:
                if(direction!=Direction.UP)back(direction);
                direction = Direction.DOWN;
                moving = true;
                break;
            case A:
                if(direction!=Direction.UP)back(direction);
                direction = Direction.LEFT;
                moving = true;
                break;
            case D:
                if(direction!=Direction.UP)back(direction);
                direction = Direction.RIGHT;
                moving = true;
                break;
            default:
                break;
        }
    }

    public void released(KeyCode keyCode) {
        if (!playerId.equals(Account.uid)) {
            return; // 不是当前玩家的坦克，不响应键盘事件
        }
        switch (keyCode) {
            case W:
            case S:
            case A:
            case D:
                moving = false;
                break;
            case J:
                if (canFire) {
                    openFire();
                    canFire = false;
                }

//                System.out.println("fire");
                break;
            default:
                break;
        }
    }

    @Override
    public void run() {
        while (true) {
            System.out.print("");
            if (!canFire) {
                try {
                    Thread.sleep(500);
                    canFire = true;
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

            }
        }
    }

    public boolean checkCollision(AbstractObject other) {
        // 实现坦克与其他对象的碰撞检测逻辑
        if (other.isAlive()) {
            return getRectangle().intersects(other.getRectangle());
        }
        return false;
    }

    public void collisionBullet(List<Bullet> bullets) {
        // 实现坦克与子弹的碰撞检测逻辑
        for (int i = 0; i < bullets.size(); i++) {
            Bullet bullet = bullets.get(i);
            if (checkCollision(bullet)) {
                lives--;
                if(lives == 0) {
                    setAlive(false);
                }
                bullet.setAlive(false);
            }
        }
    }

    public void collisionRocks(List<Rock> rocks) {
        // 实现玩家与石头的碰撞检测逻辑
        for (int i = 0; i < rocks.size(); i++) {
            Rock rock = rocks.get(i);
            if (checkCollision(rock)) {
                directions.add(direction);
                switch (direction) {
                    case UP -> setY(getY() + speed);
                    case DOWN -> setY(getY() - speed);
                    case LEFT -> setX(getX() + speed);
                    case RIGHT -> setX(getX() - speed);
                }
            } else directions.remove(direction);
        }
    }

    public void collisionTrees(List<Tree> trees) {
        // 实现玩家与树丛的碰撞检测逻辑
        for (int i = 0; i < trees.size(); i++) {
            Tree tree = trees.get(i);
            if (checkCollision(tree)) {
                directions.add(direction);
                switch (direction) {
                    case UP -> setY(getY() + speed);
                    case DOWN -> setY(getY() - speed);
                    case LEFT -> setX(getX() + speed);
                    case RIGHT -> setX(getX() - speed);
                }
            } else directions.remove(direction);
        }
    }
    public void collisionPools(List<Pool> pools) {
        // 实现玩家与水池的碰撞检测逻辑
        for(int i = 0; i < pools.size(); i++) {
            Pool pool = pools.get(i);
            if(checkCollision(pool)) {
                directions.add(direction);
                int dx = pool.getX()-getX();
                int dy = pool.getY()-getY();
                if(abs(dx)>=abs(dy)) {
                    if(dx<0&&dx>=-40)setX(getX() + dx + 40);
                    if(dx>0&&dx<=40)setX(getX() + dx - 40);
                }
                else {
                    if(dy<0&&dy>-40)setY(getY() + dy + 40);
                    if(dy>0&&dy<40)setY(getY() + dy - 40);
                }
            }
            else{
                directions.remove(direction);
            }
        }
    }
    //坦克之間的碰撞
    public void collisionTank(Tank3 tank) {
        if (checkCollision(tank)) {
            switch (direction) {
                case UP -> tank.setY(tank.getY() - speed);
                case DOWN -> tank.setY(tank.getY() + speed);
                case LEFT -> tank.setX(tank.getX() - speed);
                case RIGHT -> tank.setX(tank.getX() + speed);
            }
        } else return;
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
        if (status == 1) {
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
        if (status == 2) {
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
        if (status == 3) {
            switch (direction) {
                case UP:
                    Bullet bullet0 = new Bullet(getX() + width / 2 - 5, getY() - 22, 22, 10, Direction.UP, 5, getOnlineGameScene());
                    bullet0.draw();
                    getOnlineGameScene().bullets.add(bullet0);
                    break;
                case DOWN:
                    Bullet bullet1 = new Bullet(getX() + width / 2 - 5, getY() + height, 22, 10, Direction.DOWN, 5, getOnlineGameScene());
                    bullet1.draw();
                    getOnlineGameScene().bullets.add(bullet1);
                    break;
                case LEFT:
                    Bullet bullet2 = new Bullet(getX() - 22, getY() + height / 2 - 5, 22, 10, Direction.LEFT, 5, getOnlineGameScene());
                    bullet2.draw();
                    getOnlineGameScene().bullets.add(bullet2);
                    break;
                case RIGHT:
                    Bullet bullet3 = new Bullet(getX() + width, getY() + height / 2 - 5, 22, 10, Direction.RIGHT, 5, getOnlineGameScene());
                    bullet3.draw();
                    getOnlineGameScene().bullets.add(bullet3);
            }
        }
    }
}


