package com.jr.tankbattle.entity;

import com.jr.tankbattle.controller.StartScr;
import com.jr.tankbattle.scene.GameScene;
import com.jr.tankbattle.scene.OnlineGameScene;
import com.jr.tankbattle.scene.VsGameScene;
import com.jr.tankbattle.util.Direction;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static java.lang.Math.abs;

public class Tank2 extends AbstractObject implements Runnable{
    private Direction direction = Direction.UP;
    private boolean moving = false;
    private  boolean canFire = true;
    //无敌时刻
    private boolean invincible = false;
    private int width;
    private int height;
    private int lives =4;
    //坦克速度
    private int speed;
    private List<Direction> directions = new ArrayList<>();
    private Image upImage = new Image((this.getClass().getResourceAsStream("/com/jr/tankbattle/img/enemy2U.png")));
    private Image downImage = new Image(this.getClass().getResourceAsStream("/com/jr/tankbattle/img/enemy2D.png"));
    private Image leftImage = new Image(this.getClass().getResourceAsStream("/com/jr/tankbattle/img/enemy2L.png"));
    private Image rightImage = new Image(this.getClass().getResourceAsStream("/com/jr/tankbattle/img/enemy2R.png"));

    public Tank2(int x, int y, int width, int height, int speed, VsGameScene vsGameScene) {
        super(x, y, width, height, vsGameScene);
        super.setImage(upImage);
        this.speed = speed;
        this.width = width;
        this.height = height;
    }
    public Tank2(int x, int y, int width, int height, int speed, OnlineGameScene onlineGameScene) {
        super(x, y, width, height, onlineGameScene);
        super.setImage(upImage);
        this.speed = speed;
        this.width = width;
        this.height = height;
    }

    @Override
    public void move() {
        // 实现坦克的移动逻辑
        if (!moving|| directions.contains(direction)||edgeDetector()) {
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
                if(canFire){
                    openFire();
                    canFire = false;
                }
                break;
            default:
                break;
        }
    }
    @Override
    public void run() {
        while (true){
            System.out.print("");
            if(!canFire){
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
                if(!invincible){
                    lives--;
                }
                if(lives == 0) {
                    setAlive(false);
                }
                bullet.setAlive(false);
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
        for(int i = 0; i < rocks.size(); i++) {
            Rock rock = rocks.get(i);
            if(checkCollision(rock)) {
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
            }
            else directions.remove(direction);
        }
    }
    public void collisionIrons(List<Iron> irons) {
        // 实现玩家与铁块的碰撞检测逻辑
        for(int i = 0; i < irons.size(); i++) {
            Iron iron = irons.get(i);
            if(checkCollision(iron)) {
                directions.add(direction);
                int dx = iron.getX()-getX();
                int dy = iron.getY()-getY();
                if(abs(dx)>=abs(dy)) {
                    if(dx<0&&dx>=-40)setX(getX() + dx + 40);
                    if(dx>0&&dx<=40)setX(getX() + dx - 40);
                }
                else {
                    if(dy<0&&dy>-40)setY(getY() + dy + 40);
                    if(dy>0&&dy<40)setY(getY() + dy - 40);
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
            }
            else directions.remove(direction);
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
    public void collisionTank(Tank tank){
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

