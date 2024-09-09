package com.jr.tankbattle.entity;

import com.jr.tankbattle.scene.GameScene;
import com.jr.tankbattle.util.Direction;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AiTank extends AbstractObject{
    private Direction direction = Direction.UP;
    private boolean moving = true;
    private int width;
    private int height;
    //坦克速度
    private int speed;
    private Image downImage = new Image(this.getClass().getResourceAsStream("/com/jr/tankbattle/img/enemy3D.png"));
    private Image leftImage = new Image(this.getClass().getResourceAsStream("/com/jr/tankbattle/img/enemy3L.png"));
    private Image rightImage = new Image(this.getClass().getResourceAsStream("/com/jr/tankbattle/img/enemy3R.png"));

    private List<Direction> directions = new ArrayList<>();

    public AiTank(int x, int y, int width, int height, int speed, GameScene gameScene) {
        super(x, y, width, height, gameScene);
        super.setImage(new Image(this.getClass().getResourceAsStream("/com/jr/tankbattle/img/enemy3U.png")));
        this.speed = 2;
        this.width = 40;
        this.height = 40;
    }

    @Override
    public void move() {
        if(!moving||directions.contains(direction)) {
            return;
        }
        //边界检测
        if(getX()<=0){
            direction = Direction.RIGHT;
        }
        if(getY()<=0) {
            direction = Direction.DOWN;
        }
        if(getX()>=1020){
            direction = Direction.LEFT;
        }
        if(getY()>=660) {
            direction = Direction.UP;
        }
        // 实现AI坦克的移动逻辑
        Random rand = new Random();
        int randomX = rand.nextInt(100);
        switch (randomX) {
            case 1 -> direction = Direction.RIGHT;
            case 2 -> direction = Direction.LEFT;
            case 3 -> direction = Direction.DOWN;
            case 4 -> direction = Direction.UP;
            case 5 -> openFire();
            default -> direction = direction;
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
                    getGameScene().getGraphicsContext().drawImage(getDownImage(), super.getX(), super.getY());
            case LEFT ->
                    getGameScene().getGraphicsContext().drawImage(getLeftImage(), super.getX(), super.getY());
            case RIGHT ->
                    getGameScene().getGraphicsContext().drawImage(getRightImage(), super.getX(), super.getY());
        }
    }

    @Override
    public boolean checkCollision(AbstractObject other) {
        // 实现AI坦克与其他对象的碰撞检测逻辑
        return getRectangle().intersects(other.getRectangle());
    }

    public void collisionTank(Tank tank) {
        // 实现Ai坦克与玩家坦克的碰撞检测逻辑
            if(checkCollision(tank)) {
                directions.add(direction);
                switch (direction) {
                    case UP -> tank.setY(tank.getY() - speed);
                    case DOWN -> tank.setY(tank.getY() + speed);
                    case LEFT -> tank.setX(tank.getX() - speed);
                    case RIGHT -> tank.setX(tank.getX() + speed);
                }
            }
            else directions.remove(direction);
    }
    public void collisionRocks(List<Rock> rocks) {
        // 实现AiTank与树丛的碰撞检测逻辑
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
        // 实现AiTank与树丛的碰撞检测逻辑
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

    public void collisionAi(List<AiTank> aiTanks) {
        // 实现坦克之间的碰撞检测逻辑
        for (int i = 0; i < aiTanks.size(); i++) {
            AiTank aiTank = aiTanks.get(i);
            if (aiTank != this) {
                if (checkCollision(aiTank)) {
                    directions.add(direction);
                    switch (direction) {
                        case UP -> aiTank.setY(aiTank.getY() - speed);
                        case DOWN -> aiTank.setY(aiTank.getY() + speed);
                        case LEFT -> aiTank.setX(aiTank.getX() - speed);
                        case RIGHT -> aiTank.setX(aiTank.getX() + speed);
                    }
                } else directions.remove(direction);
            }
        }
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

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public Image getLeftImage() {
        return leftImage;
    }

    public Image getRightImage() {
        return rightImage;
    }

    public Image getDownImage() {
        return downImage;
    }
}