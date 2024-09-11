package com.jr.tankbattle.entity;

import com.jr.tankbattle.Director;
import com.jr.tankbattle.controller.Account;
import com.jr.tankbattle.scene.OnlineGameScene;
import com.jr.tankbattle.util.Direction;
import com.jr.tankbattle.util.TankType;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static java.lang.Math.abs;

public class Tank3 extends AbstractObject implements Runnable {
    private Direction direction = Direction.UP;
    private boolean moving = false;
    private boolean canFire = true;
    //无敌时刻
    private boolean invincible = false;
    private boolean treadRunning = true;
    private int width;
    private int height;
    private int lives = 4;
    //坦克速度
    private int speed;

    private String playerId;

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }
    public String getPlayerId() {
        return playerId;
    }

    public void setPosition(int x, int y) {
        super.setX(x);
        super.setY(y);
    }

    public void setTankType(TankType tankType) {
        this.tankType = tankType;
    }

    public TankType tankType;
    private Image upImage, downImage, leftImage, rightImage;
    private List<Direction> directions = new ArrayList<>();


    public Tank3() {
        super();
    }

    public Tank3(int width,int height,int speed,OnlineGameScene onlineGameScene) {
        super(onlineGameScene);
        this.width = width;
        this.height = height;
        this.speed=speed;
    }

    public Tank3(int x, int y, int width, int height, int speed, OnlineGameScene onlineGameScene) {
        super(x, y, width, height, onlineGameScene);
        super.setImage(upImage);
        this.speed = speed;
        this.width = width;
        this.height = height;
    }

    public Tank3(int x, int y, int width, int height, int speed, OnlineGameScene onlineGameScene, TankType tankType, String playerId) {
        super(x, y, width, height, onlineGameScene);
        super.setImage(upImage);
        this.speed = speed;
        this.width = width;
        this.height = height;
        this.tankType = tankType;
        this.playerId = playerId;
    }

    public void loadImages() {
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
                upImage = new Image(this.getClass().getResourceAsStream("/com/jr/tankbattle/img/enemy4U.jpg"));
                downImage = new Image(this.getClass().getResourceAsStream("/com/jr/tankbattle/img/enemy4D.jpg"));
                leftImage = new Image(this.getClass().getResourceAsStream("/com/jr/tankbattle/img/enemy4L.jpg"));
                rightImage = new Image(this.getClass().getResourceAsStream("/com/jr/tankbattle/img/enemy4R.jpg"));
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

    //边界检测
    public boolean edgeDetector(){
        if((getX() <= 0 && direction == Direction.LEFT)
                ||(getY() <= 0 && direction == Direction.UP)
                ||(getX() >= 860 && direction == Direction.RIGHT)
                ||(getY() >= 680 && direction == Direction.DOWN)){
            return true;
        }
        return false;
    }

    public void draw() {
        switch (direction) {
            case UP ->
                    getOnlineGameScene().getGraphicsContext().drawImage(upImage, super.getX(), super.getY());
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
        while (treadRunning) {
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
            if(checkCollision(bullet)) {
                bullet.setAlive(false);
                if(!invincible){
                    lives--;
                }
                if(lives == 0) {
                    setAlive(false);
                }
            }
        }
    }
    public void collisionHeart(List<Heart> hearts){
        // 实现坦克与桃心的碰撞检测逻辑
        for(int i = 0; i < hearts.size(); i++) {
            Heart heart = hearts.get(i);
            if(checkCollision(heart)) {
                heart.setAlive(false);
                lives += 2;
            }
        }
    }
    public void collisionSheild(List<Sheild> sheilds) {
        for (Sheild sheild : sheilds) {
            if (checkCollision(sheild)) {
                sheild.setMoving(true);
                invincible = true;

                // 创建一个任务来在5秒后执行
                ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
                scheduler.schedule(() -> {
                    sheild.setAlive(false);
                    invincible = false;
                }, 5, TimeUnit.SECONDS);

                // 可以关闭调度器以节省资源
                scheduler.shutdown();
            }
        }
    }

    public void collisionRocks(List<Rock> rocks) {
        // 实现玩家与石头的碰撞检测逻辑
        for (int i = 0; i < rocks.size(); i++) {
            Rock rock = rocks.get(i);
            if (checkCollision(rock)) {
                directions.add(direction);
                int dx = rock.getX()-getX();
                int dy = rock.getY()-getY();
                if(abs(dx)>=abs(dy)) {
                    if(dx<0&&dx>=-40)setX(getX() + dx + 40);
                    if(dx>0&&dx<=40)setX(getX() + dx - 40);
                }
                else {
                    if(dy<0&&dy>-40)setY(getY() + dy + 40);
                    if(dy>0&&dy<40)setY(getY() + dy - 40);
                }
            } else directions.remove(direction);
        }
    }
    public void collisionLandmines(List<Landmine> landmines) {
        // 实现玩家与地雷的碰撞检测逻辑
        for(int i = 0; i < landmines.size(); i++) {
            Landmine landmine = landmines.get(i);
            if(checkCollision(landmine)) {
                landmine.setAlive(false);
                setAlive(false);
            }
        }
    }
    public void collisionTrees(List<Tree> trees) {
        // 实现玩家与树丛的碰撞检测逻辑
        for (int i = 0; i < trees.size(); i++) {
            Tree tree = trees.get(i);
            if (checkCollision(tree)) {
                directions.add(direction);
                int dx = tree.getX()-getX();
                int dy = tree.getY()-getY();
                if(abs(dx)>=abs(dy)) {
                    if(dx<0&&dx>=-40)setX(getX() + dx + 40);
                    if(dx>0&&dx<=40)setX(getX() + dx - 40);
                }
                else {
                    if(dy<0&&dy>-40)setY(getY() + dy + 40);
                    if(dy>0&&dy<40)setY(getY() + dy - 40);
                }
            } else directions.remove(direction);
        }
    }

    public void collisionIrons(List<Iron> irons) {
        // 实现玩家与铁块的碰撞检测逻辑
        for (int i = 0; i < irons.size(); i++) {
            Iron iron = irons.get(i);
            if (checkCollision(iron)) {
                directions.add(direction);
                int dx = iron.getX() - getX();
                int dy = iron.getY() - getY();
                if (abs(dx) >= abs(dy)) {
                    if (dx < 0 && dx >= -40) setX(getX() + dx + 40);
                    if (dx > 0 && dx <= 40) setX(getX() + dx - 40);
                } else {
                    if (dy < 0 && dy > -40) setY(getY() + dy + 40);
                    if (dy > 0 && dy < 40) setY(getY() + dy - 40);
                }
            } else directions.remove(direction);
        }
    }

    public void collisionPools(List<Pool> pools) {
        // 实现玩家与水池的碰撞检测逻辑
        for (int i = 0; i < pools.size(); i++) {
            Pool pool = pools.get(i);
            if (checkCollision(pool)) {
                directions.add(direction);
                int dx = pool.getX() - getX();
                int dy = pool.getY() - getY();
                if (abs(dx) >= abs(dy)) {
                    if (dx < 0 && dx >= -40) setX(getX() + dx + 40);
                    if (dx > 0 && dx <= 40) setX(getX() + dx - 40);
                } else {
                    if (dy < 0 && dy > -40) setY(getY() + dy + 40);
                    if (dy > 0 && dy < 40) setY(getY() + dy - 40);
                }
            } else {
                directions.remove(direction);
            }
        }
    }

    //坦克之間的碰撞
    public void collisionTank(Tank3 tank) {

        if (checkCollision(tank) && !tank.edgeDetector()) {
            directions.add(direction);
            int dx = tank.getX() - getX();
            int dy = tank.getY() - getY();
            if (abs(dx) >= abs(dy)) {
                if (dx < 0 && dx >= -40) setX(getX() + dx + 40);
                if (dx > 0 && dx <= 40) setX(getX() + dx - 40);
            } else {
                if (dy < 0 && dy > -40) setY(getY() + dy + 40);
                if (dy > 0 && dy < 40) setY(getY() + dy - 40);
            }
        } else return;
    }

    public boolean isInvincible() {
        return invincible;
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

    // Convert a single tank's data to a comma-separated string
    public String toDataString() {
        return playerId+":"+getX() + "," + getY() + "," + direction + "," + playerId + "," + tankType.name();
    }

    // Static method to create a Tank3 object from a data string
    public static Tank3 fromDataString(String dataString) {
        String[] parts = dataString.split(",");
        Tank3 tank = new Tank3(Integer.parseInt(parts[0]),Integer.parseInt(parts[1]),40,40,2, Director.onlineGameScene);
        tank.direction = Direction.valueOf(parts[2]);
        tank.playerId = parts[3];
        tank.tankType = TankType.valueOf(parts[4]);
        tank.loadImages();
        return tank;
    }

}


