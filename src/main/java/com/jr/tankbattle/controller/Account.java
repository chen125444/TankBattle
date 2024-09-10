package com.jr.tankbattle.controller;

import com.jr.tankbattle.Director;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

public class Account {
    //记录登录的账户信息
    public static String uid;
    public static String password;
    public ImageView backBtn;

    // FXML 注解字段
    @FXML
    private Label uidLabel;

    @FXML
    private Label passwordLabel;

    //保存账户信息 在登录注册时调用 记录一下
    public static void setInfo(String uid, String password) {
        Account.uid = uid;
        Account.password = password;
    }

    @FXML
    public void initialize() {
        // 在初始化时更新UI上的信息
        if (uidLabel != null && passwordLabel != null) {
            uidLabel.setText(uid != null ? uid : "无账号信息");
            passwordLabel.setText(password != null ? password : "无密码信息");
        }

        //按钮效果设置
        // 创建亮度调整效果和阴影效果
        colorAdjust = new ColorAdjust();
        shadow = new DropShadow();
        shadow.setColor(Color.GRAY);
        shadow.setRadius(10);

        // 将效果应用到多个按钮
        applyEffects(backBtn);
    }

    @FXML
    public void Back() {
        System.out.println("back");
        Director.getInstance().toSetting();
    }




    private DropShadow shadow;
    private ColorAdjust colorAdjust;

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
