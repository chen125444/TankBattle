package com.jr.tankbattle.entity;

import com.jr.tankbattle.Director;
import com.jr.tankbattle.scene.GameScene;
import com.jr.tankbattle.scene.OnlineGameScene;
import com.jr.tankbattle.util.Direction;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static java.lang.Math.abs;
import static com.jr.tankbattle.controller.HomePage.status;

public class AiTank extends AbstractObject implements Runnable {
    private Direction direction = Direction.UP;
    private boolean moving = true;
    private boolean canFire = true;
    private boolean invincible = false;
    private boolean threadRunning = true;
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

    public AiTank(int x, int y, int width, int height, int speed, OnlineGameScene gameScene) {
        super(x, y, width, height, gameScene);
        super.setImage(new Image(this.getClass().getResourceAsStream("/com/jr/tankbattle/img/enemy3U.png")));
        this.speed = 2;
        this.width = 40;
        this.height = 40;
    }

    @Override
    public void run() {
        while (threadRunning) {
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

    @Override
    public void move() {

    }

    public void aiMove(Tank tank, List<AiTank> aiTanks) {
        if (!moving || directions.contains(direction)) {
            return;
        }
        if (edgeDetector()) {
            if (getX() < 40) {
                setX(40);
                direction = Direction.RIGHT;
            }
            if (getY() < 40) {
                setY(40);
                direction = Direction.DOWN;
            }
            if (getX() > 840) {
                setX(840);
                direction = Direction.LEFT;
            }
            if (getY() > 640) {
                setY(640);
                direction = Direction.RIGHT;
            }
        }
        //实现AI坦克的移动逻辑
        for (int i = 0; i < aiTanks.size(); i++) {
            AiTank aiTank = aiTanks.get(i);
            if (aiTank != this) {
                if (getX() - aiTank.getX() < 40 && getX() - aiTank.getX() > 0) {
                    setX(getX() + speed);
                }
                if (aiTank.getX() - getX() < 40 && aiTank.getX() - getX() > 0) {
                    setX(getX() - speed);
                }
                if (getY() - aiTank.getY() < 40 && getY() - aiTank.getY() > 0) {
                    setY(getY() + speed);
                }
                if (aiTank.getY() - getY() < 40 && aiTank.getY() - getY() > 0) {
                    setY(getY() - speed);
                }
            }
        }
        if (abs(getX() - tank.getX()) < 40 && getY() > tank.getY()) {
            if (abs(getY() - tank.getY()) > 80) direction = Direction.UP;
            else if (abs(getY() - tank.getY()) < 40) return;
            if (canFire) {
                openFire();
                canFire = false;
            }
        }
        if (abs(getX() - tank.getX()) < 40 && getY() < tank.getY()) {
            if (abs(getY() - tank.getY()) > 80) direction = Direction.DOWN;
            else if (abs(getY() - tank.getY()) < 40) return;
            if (canFire) {
                openFire();
                canFire = false;
            }
        }
        if (getX() > tank.getX() && abs(getY() - tank.getY()) < 40) {
            if (abs(getX() - tank.getX()) > 80) direction = Direction.LEFT;
            else if (abs(getX() - tank.getX()) < 40) return;
            if (canFire) {
                openFire();
                canFire = false;
            }
        }
        if (getX() < tank.getX() && abs(getY() - tank.getY()) < 40) {
            if (abs(getX() - tank.getX()) > 80) direction = Direction.RIGHT;
            else if (abs(getX() - tank.getX()) < 40) return;
            if (canFire) {
                openFire();
                canFire = false;
            }
        }
        Random random = new Random();
        int num = random.nextInt(200);
        switch (num) {
            case 0:
                direction = Direction.UP;
                break;
            case 1:
                direction = Direction.DOWN;
                break;
            case 2:
                direction = Direction.LEFT;
                break;
            case 3:
                direction = Direction.RIGHT;
                break;
            case 4:
                if(canFire){
                openFire();
                canFire = false;
                }
                 break;
            default:
                direction = direction;
                break;
        }
        switch (direction) {
            case UP -> setY(getY() - speed);
            case DOWN -> setY(getY() + speed);
            case RIGHT -> setX(getX() + speed);
            case LEFT -> setX(getX() - speed);
        }
    }

    //边界检测
    public boolean edgeDetector() {
        if ((getX() < 40 && direction == Direction.LEFT)
                || (getY() < 40 && direction == Direction.UP)
                || (getX() > 840 && direction == Direction.RIGHT)
                || (getY() > 640 && direction == Direction.DOWN)) {
            return true;
        }
        return false;
    }

    public void aiMove(Tank3 tank, List<AiTank> aiTanks) {
        if (!moving || directions.contains(direction)) {
            return;
        }
        if (edgeDetector()) {
            if (getX() < 40) {
                setX(40);
                direction = Direction.RIGHT;
            }
            if (getY() < 40) {
                setY(40);
                direction = Direction.DOWN;
            }
            if (getX() > 840) {
                setX(840);
                direction = Direction.LEFT;
            }
            if (getY() > 640) {
                setY(640);
                direction = Direction.RIGHT;
            }
        }
        //实现AI坦克的移动逻辑
        for (int i = 0; i < aiTanks.size(); i++) {
            AiTank aiTank = aiTanks.get(i);
            if (aiTank != this) {
                if (getX() - aiTank.getX() < 40 && getX() - aiTank.getX() > 0) {
                    setX(getX() + speed);
                }
                if (aiTank.getX() - getX() < 40 && aiTank.getX() - getX() > 0) {
                    setX(getX() - speed);
                }
                if (getY() - aiTank.getY() < 40 && getY() - aiTank.getY() > 0) {
                    setY(getY() + speed);
                }
                if (aiTank.getY() - getY() < 40 && aiTank.getY() - getY() > 0) {
                    setY(getY() - speed);
                }
            }
        }
        Random random = new Random();
        int num = random.nextInt(200);
        switch (num) {
            case 0:
                direction = Direction.UP;
                break;
            case 1:
                direction = Direction.DOWN;
                break;
            case 2:
                direction = Direction.LEFT;
                break;
            case 3:
                direction = Direction.RIGHT;
                break;
            case 4,5,6:
                openFire();break;
            default:
                direction = direction;
                break;
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
        if (status == 1) {
            switch (direction) {
                case UP -> getGameScene().getGraphicsContext().drawImage(super.getImage(), super.getX(), super.getY());
                case DOWN -> getGameScene().getGraphicsContext().drawImage(getDownImage(), super.getX(), super.getY());
                case LEFT -> getGameScene().getGraphicsContext().drawImage(getLeftImage(), super.getX(), super.getY());
                case RIGHT ->
                        getGameScene().getGraphicsContext().drawImage(getRightImage(), super.getX(), super.getY());
            }
        }
        if (status == 3) {
            switch (direction) {
                case UP ->
                        getOnlineGameScene().getGraphicsContext().drawImage(super.getImage(), super.getX(), super.getY());
                case DOWN ->
                        getOnlineGameScene().getGraphicsContext().drawImage(getDownImage(), super.getX(), super.getY());
                case LEFT ->
                        getOnlineGameScene().getGraphicsContext().drawImage(getLeftImage(), super.getX(), super.getY());
                case RIGHT ->
                        getOnlineGameScene().getGraphicsContext().drawImage(getRightImage(), super.getX(), super.getY());
            }
        }

    }

    @Override
    public boolean checkCollision(AbstractObject other) {
        // 实现AI坦克与其他对象的碰撞检测逻辑
        return getRectangle().intersects(other.getRectangle());
    }

    public void collisionTank(Tank tank) {
        // 实现Ai坦克与玩家坦克的碰撞检测逻辑
        if (checkCollision(tank)) {
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
        } else directions.remove(direction);
    }

    public void collisionTank(Tank3 tank) {
        // 实现Ai坦克与玩家坦克的碰撞检测逻辑
        if (checkCollision(tank)) {
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
        } else directions.remove(direction);
    }

    public void collisionRocks(List<Rock> rocks) {
        // 实现AiTank与树丛的碰撞检测逻辑
        for (int i = 0; i < rocks.size(); i++) {
            Rock rock = rocks.get(i);
            if (checkCollision(rock)) {
                directions.add(direction);
                int dx = rock.getX() - getX();
                int dy = rock.getY() - getY();
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

    public void collisionLandmines(List<Landmine> landmines) {
        // 实现Ai与地雷的碰撞检测逻辑
        for (int i = 0; i < landmines.size(); i++) {
            Landmine landmine = landmines.get(i);
            if (checkCollision(landmine)) {
                directions.add(direction);
                int dx = landmine.getX() - getX();
                int dy = landmine.getY() - getY();
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

    public void collisionTrees(List<Tree> trees) {
        // 实现AiTank与树丛的碰撞检测逻辑
        for (int i = 0; i < trees.size(); i++) {
            Tree tree = trees.get(i);
            if (checkCollision(tree)) {
                directions.add(direction);
                int dx = tree.getX() - getX();
                int dy = tree.getY() - getY();
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

    public void collisionSheild(List<Sheild> sheilds) {
        for (Sheild sheild : sheilds) {
            if (checkCollision(sheild) && !sheild.isMoving()) {
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

    public void collisionPools(List<Pool> pools) {
        // 实现AiTank与水池的碰撞检测逻辑
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

    public void collisionBullet(List<Bullet> bullets) {
        // 实现坦克与子弹的碰撞检测逻辑
        for (int i = 0; i < bullets.size(); i++) {
            Bullet bullet = bullets.get(i);
            if (checkCollision(bullet)) {
                if (!invincible) {
                    setAlive(false);
                }
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
                    int dx = aiTank.getX() - getX();
                    int dy = aiTank.getY() - getY();
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
        if(status == 3) {
            switch (direction) {
                case UP:
                    Bullet bullet0 = new Bullet(getX() + width / 2 - 5, getY() - 22, 22, 10, Direction.UP, 5, Director.onlineGameScene);
                    bullet0.draw();
                    getOnlineGameScene().bullets.put(bullet0.getId(), bullet0);
                    break;
                case DOWN:
                    Bullet bullet1 = new Bullet(getX() + width / 2 - 5, getY() + height, 22, 10, Direction.DOWN, 5, Director.onlineGameScene);
                    bullet1.draw();
                    getOnlineGameScene().bullets.put(bullet1.getId(), bullet1);
                    break;
                case LEFT:
                    Bullet bullet2 = new Bullet(getX() - 22, getY() + height / 2 - 5, 22, 10, Direction.LEFT, 5, Director.onlineGameScene);
                    bullet2.draw();
                    getOnlineGameScene().bullets.put(bullet2.getId(), bullet2);
                    break;
                case RIGHT:
                    Bullet bullet3 = new Bullet(getX() + width, getY() + height / 2 - 5, 22, 10, Direction.RIGHT, 5, Director.onlineGameScene);
                    bullet3.draw();
                    getOnlineGameScene().bullets.put(bullet3.getId(), bullet3);
            }
        }
    }

    public boolean isInvincible() {
        return invincible;
    }

    public void setThreadRunning(boolean threadRunning) {
        this.threadRunning = threadRunning;
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