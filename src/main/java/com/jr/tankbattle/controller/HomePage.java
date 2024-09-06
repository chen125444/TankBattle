package com.jr.tankbattle.controller;

import com.jr.tankbattle.Director;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;

import java.util.Objects;

public class HomePage {
    public static int status = 0;
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
}
