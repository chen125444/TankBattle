package com.jr.tankbattle.entity;

import com.jr.tankbattle.Director;
import com.jr.tankbattle.controller.Account;
import com.jr.tankbattle.controller.StartScr;
import com.jr.tankbattle.scene.GameScene;
import com.jr.tankbattle.scene.OnlineGameScene;
import com.jr.tankbattle.scene.VsGameScene;
import com.jr.tankbattle.util.Direction;
import com.jr.tankbattle.util.Sound;
import com.jr.tankbattle.util.TankType;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.shape.Rectangle;

import java.util.List;

import static com.jr.tankbattle.controller.HomePage.status;

public class Bullet extends AbstractObject {
    private static int idCounter = 0;
    private String id;
    private int speed;//子弹速度
    private Direction direction;//子弹方向
    private Image upImage = new Image(this.getClass().getResourceAsStream("/com/jr/tankbattle/img/bulletGreenUp.png"));
    private Image downImage = new Image(this.getClass().getResourceAsStream("/com/jr/tankbattle/img/bulletGreenDown.png"));
    private Image leftImage = new Image(this.getClass().getResourceAsStream("/com/jr/tankbattle/img/bulletGreenLeft.png"));
    private Image rightImage = new Image(this.getClass().getResourceAsStream("/com/jr/tankbattle/img/bulletGreenRight.png"));

    //构造函数;
    public Bullet(int x, int y, int width, int height, Direction direction, int speed, GameScene gameScene) {
        super(x, y, width, height, gameScene);
        this.speed = speed;
        this.direction = direction;
        this.id = Account.uid + idCounter++;//标识
    }

    public Bullet(int x, int y, int width, int height, Direction direction, int speed, VsGameScene vsGameScene) {
        super(x, y, width, height, vsGameScene);
        this.speed = speed;
        this.direction = direction;
    }

    public Bullet(int x, int y, int width, int height, Direction direction, int speed, OnlineGameScene onlineGameScene) {
        super(x, y, width, height, onlineGameScene);
        this.speed = speed;
        this.direction = direction;
    }

    // 实现子弹的移动逻辑
    @Override
    public void move() {
        //边界检测
        if (getX() <= 0) {
            setAlive(false);
        }
        if (getY() <= 0) {
            setAlive(false);
        }
        if (getX() >= 860) {
            setAlive(false);
        }
        if (getY() >= 680) {
            setAlive(false);
        }
        switch (direction) {
            case UP -> setY(getY() - speed);
            case DOWN -> setY(getY() + speed);
            case RIGHT -> setX(getX() + speed);
            case LEFT -> setX(getX() - speed);
        }
    }

    // 实现子弹的绘制逻辑
    public void draw() {
        if (status == 1) {
            if (isAlive()) {
                switch (direction) {
                    case UP -> getGameScene().getGraphicsContext().drawImage(upImage, super.getX(), super.getY());
                    case DOWN -> getGameScene().getGraphicsContext().drawImage(downImage, super.getX(), super.getY());
                    case LEFT -> getGameScene().getGraphicsContext().drawImage(leftImage, super.getX(), super.getY());
                    case RIGHT -> getGameScene().getGraphicsContext().drawImage(rightImage, super.getX(), super.getY());
                }
            } else {
                getGameScene().bullets.remove(this);
                getGameScene().explodes.add(new Explode(getX(), getY(), getGameScene()));
                Sound.getInstance().playAudio(0);
            }
        }
        if (status == 2) {
            if (isAlive()) {
                switch (direction) {
                    case UP -> getVsGameScene().getGraphicsContext().drawImage(upImage, super.getX(), super.getY());
                    case DOWN -> getVsGameScene().getGraphicsContext().drawImage(downImage, super.getX(), super.getY());
                    case LEFT -> getVsGameScene().getGraphicsContext().drawImage(leftImage, super.getX(), super.getY());
                    case RIGHT ->
                            getVsGameScene().getGraphicsContext().drawImage(rightImage, super.getX(), super.getY());
                }
            } else {
                getVsGameScene().bullets.remove(this);
                getVsGameScene().explodes.add(new Explode(getX(), getY(), getVsGameScene()));
            }
        }
        if (status == 3) {
            if (isAlive()) {
                switch (direction) {
                    case UP -> getOnlineGameScene().getGraphicsContext().drawImage(upImage, super.getX(), super.getY());
                    case DOWN ->
                            getOnlineGameScene().getGraphicsContext().drawImage(downImage, super.getX(), super.getY());
                    case LEFT ->
                            getOnlineGameScene().getGraphicsContext().drawImage(leftImage, super.getX(), super.getY());
                    case RIGHT ->
                            getOnlineGameScene().getGraphicsContext().drawImage(rightImage, super.getX(), super.getY());
                }
            } else {
                getOnlineGameScene().bullets.remove(this);
                getOnlineGameScene().explodes.add(new Explode(getX(), getY(), getOnlineGameScene()));
            }
        }
    }


    // 实现子弹与其他对象的碰撞检测逻辑
    @Override
    public boolean checkCollision(AbstractObject other) {
        return getRectangle().intersects(other.getRectangle());
    }

    public void collisionBullet(List<Bullet> bullets) {
        // 实现子弹与子弹的碰撞检测逻辑
        for (int i = 0; i < bullets.size(); i++) {
            Bullet bullet = bullets.get(i);
            if (bullet != this) {
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

    // Convert Bullet data to a comma-separated string
    public String toDataString() {
        return id + "," + super.getX() + "," + super.getY() + "," + direction;
    }

    // Static method to create a Bullet object from a data string
    public static Bullet fromDataString(String dataString) {
        String[] parts = dataString.split(",");
        int x = Integer.parseInt(parts[1]);
        int y = Integer.parseInt(parts[2]);
        Direction direction = Direction.valueOf(parts[3]);
        Bullet bullet = new Bullet(x, y, 22, 10, direction, 5, Director.onlineGameScene);
        bullet.id = parts[0];
        return bullet;
    }


    public String getId() {
        return id;
    }


}


