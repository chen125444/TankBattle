package com.jr.tankbattle.entity;

import com.jr.tankbattle.scene.GameScene;
import com.jr.tankbattle.util.Direction;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.lang.Math.abs;

public class AiTank extends AbstractObject implements Runnable{
    private Direction direction = Direction.UP;
    private boolean moving = true;
    private boolean canFire = true;
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

    @Override
    public void move() {

    }
    public void aiMove(Tank tank,List<AiTank> aiTanks) {
        if(!moving||directions.contains(direction)) {
            return;
        }
        while(edgeDetector()){
            switch(direction){
                case UP :
                    direction = Direction.DOWN;
                    setY(getY()+speed);
                    break;
                case DOWN :
                    direction = Direction.UP;
                    setY(getY()-speed);
                    break;
                case LEFT :
                    direction = Direction.RIGHT;
                    setX(getX()+speed);
                    break;
                case RIGHT :
                    direction = Direction.LEFT;
                    setX(getX()-speed);
                    break;
            }
        }
        // 实现AI坦克的移动逻辑
        if(getX()==tank.getX()&&getY()>tank.getY()){
            direction = Direction.UP;
            if(canFire){
                openFire();
                canFire = false;
            }
        }
        if(getX()==tank.getX()&&getY()<tank.getY()){
            direction = Direction.DOWN;
            if(canFire){
                openFire();
                canFire = false;
            }
        }
        if(getX()>tank.getX()&&getY()==tank.getY()){
            direction = Direction.LEFT;
            if(canFire){
                openFire();
                canFire = false;
            }
        }
        if(getX()<tank.getX()&&getY()==tank.getY()){
            direction = Direction.RIGHT;
            if(canFire){
                openFire();
                canFire = false;
            }
        }
        for (int i=0;i<aiTanks.size();i++){
            AiTank aiTank = aiTanks.get(i);
            if(getX()==aiTank.getX()&&getY()>aiTank.getY()){
                direction = Direction.DOWN;
            }
            if(getX()==aiTank.getX()&&getY()<aiTank.getY()){
                direction = Direction.UP;
            }
            if(getX()>aiTank.getX()&&getY()==aiTank.getY()){
                direction = Direction.RIGHT;
            }
            if(getX()<aiTank.getX()&&getY()==aiTank.getY()){
                direction = Direction.LEFT;
            }
        }
        Random rand = new Random();
        int randomX = rand.nextInt(100);
        switch (randomX) {
            case 1 : direction = Direction.RIGHT;
                break;
            case 2 : direction = Direction.LEFT;
                break;
            case 3 : direction = Direction.UP;
                break;
            case 4 : direction = Direction.DOWN;
                break;
            case 5 : openFire();
            default : direction = direction;
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
                int dx = tank.getX()-getX();
                int dy = tank.getY()-getY();
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
    public void collisionRocks(List<Rock> rocks) {
        // 实现AiTank与树丛的碰撞检测逻辑
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
    public void collisionTrees(List<Tree> trees) {
        // 实现AiTank与树丛的碰撞检测逻辑
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
        // 实现AiTank与水池的碰撞检测逻辑
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
                    int dx = aiTank.getX()-getX();
                    int dy = aiTank.getY()-getY();
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