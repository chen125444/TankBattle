package com.jr.tankbattle.controller;

import com.jr.tankbattle.Director;
import javafx.fxml.FXML;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

import java.io.IOException;

public class Setting {
    public ImageView accountBtn;
    public ImageView backBtn;
    public ImageView helpBtn;
    public ImageView exitBtn;
    public ImageView soundBtn;
    public ImageView mapBtn;

    @FXML
    public void Help(){
        System.out.println("help");
        Director.getInstance().toHelp();
    }
    @FXML
    public void Exit(){
        System.out.println("exit");
        System.exit(0);
    }
    @FXML
    public void Back() throws IOException {
        System.out.println("back");
        Director.getInstance().toHomePage();
    }
    @FXML
    public void Sound(){
        System.out.println("sound");
        Director.getInstance().toSoundSrc();
    }
    @FXML
    public void Account(){
        System.out.println("account");
        Director.getInstance().toAccount();
    }

    @FXML
    public void Map(){
        System.out.println("map");
        Director.getInstance().toMapScr();
    }



    private double originalWidth;
    private double originalHeight;
    private DropShadow shadow;
    private ColorAdjust colorAdjust;

    @FXML
    public void initialize() {
        // 创建亮度调整效果和阴影效果
        colorAdjust = new ColorAdjust();
        shadow = new DropShadow();
        shadow.setColor(Color.GRAY);
        shadow.setRadius(10);

        // 将效果应用到多个按钮
        applyEffects(accountBtn);
        applyEffects(backBtn);
        applyEffects(helpBtn);
        applyEffects(soundBtn);
        applyEffects(exitBtn);
        applyEffects(mapBtn);
    }

    // 抽象出的效果方法，可以应用到任何 ImageView 上
    private void applyEffects(ImageView button) {
        // 初始化参数
        button.setOnMouseEntered(event -> {
            colorAdjust.setBrightness(0.3);  // 增加亮度
            button.setEffect(shadow);        // 添加阴影
        });

        button.setOnMouseExited(event -> {
            colorAdjust.setBrightness(0);    // 还原亮度
            button.setEffect(null);          // 移除阴影
        });

        button.setOnMousePressed(event -> {
            button.setScaleX(0.95);  // 缩小到 95%
            button.setScaleY(0.95);  // 缩小到 95%
        });

        button.setOnMouseReleased(event -> {
            button.setScaleX(1.0);   // 还原到原始大小
            button.setScaleY(1.0);   // 还原到原始大小
        });
    }

}
