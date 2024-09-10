package com.jr.tankbattle.controller;

import com.jr.tankbattle.Director;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

import java.util.Objects;

public class HomePage {
    public static int status = 0;
    public ImageView matchBtn;
    public ImageView singleBtn;
    public ImageView doubleBtn;
    public ImageView settingBtn;

    //游戏模式
    @FXML
    public void Single(){
        status = 1;
        System.out.println("single mode");
        GameDlg.getInstance().Show("single");
    }
    @FXML
    public void Double(){
        status = 2;
        System.out.println("double mode");
        GameDlg.getInstance().Show("double");
    }
    @FXML
    public void Match(){
        status = 3;
        System.out.println("match mode");
        GameDlg.getInstance().Show("match");
    }
    //设置功能
    @FXML
    public void Setting(){
        status = 4;
        System.out.println("setting");
        Director.getInstance().toSetting();
    }
    //商城功能
    @FXML
    public void Store(){
        System.out.println("store");
    }
    //装备功能
    @FXML
    public void Equip(){
        System.out.println("equip");
    }


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
        applyEffects(singleBtn);
        applyEffects(doubleBtn);
        applyEffects(matchBtn);
        applyEffects(settingBtn);
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
