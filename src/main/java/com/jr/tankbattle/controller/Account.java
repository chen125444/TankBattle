package com.jr.tankbattle.controller;

import com.jr.tankbattle.Director;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class Account {
    //记录登录的账户信息
    public static String uid;
    public static String password;

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
    }

    @FXML
    public void Back() {
        System.out.println("back");
        Director.getInstance().toSetting();
    }
}
