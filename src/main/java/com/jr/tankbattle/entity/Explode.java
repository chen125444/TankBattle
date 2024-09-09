package com.jr.tankbattle.entity;

import com.jr.tankbattle.scene.GameScene;
import com.jr.tankbattle.scene.OnlineGameScene;
import com.jr.tankbattle.scene.VsGameScene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import static com.jr.tankbattle.controller.HomePage.status;

public class Explode extends AbstractObject {
    private int count = 0;
    private Image[] images = new Image[] {
            new Image(this.getClass().getResourceAsStream("/com/jr/tankbattle/img/blast1.gif")),
            new Image(this.getClass().getResourceAsStream("/com/jr/tankbattle/img/blast2.gif")),
            new Image(this.getClass().getResourceAsStream("/com/jr/tankbattle/img/blast3.gif")),
            new Image(this.getClass().getResourceAsStream("/com/jr/tankbattle/img/blast4.gif")),
            new Image(this.getClass().getResourceAsStream("/com/jr/tankbattle/img/blast5.gif")),
    };

    public Explode(int x, int y, GameScene gameScene) {
        super( x, y, 0, 0, gameScene);
    }
    public Explode(int x, int y, OnlineGameScene gameScene) {
        super( x, y, 0, 0, gameScene);
    }
    public Explode( int x, int y, VsGameScene vsGameScene) {
        super( x, y, 0, 0, vsGameScene);
    }

    public void draw(GraphicsContext graphicsContext) {
        if(status == 1){
            if(count >= images.length) {
                getGameScene().explodes.remove(this);
                return;
            }
        }
        if(status == 2){
            if(count >= images.length) {
                getVsGameScene().explodes.remove(this);
                return;
            }
        }
        if(status == 3){
            if(count >= images.length) {
                getOnlineGameScene().explodes.remove(this);
                return;
            }
        }
        Image image = images[count];
        double ex = getX() - image.getWidth()/2;
        double ey = getY() - image.getHeight()/2;
        graphicsContext.drawImage(image, ex, ey);
        count ++;
    }

    @Override
    public void move() {

    }

    @Override
    public boolean checkCollision(AbstractObject other) {
        return false;
    }
}
