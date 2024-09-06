package com.jr.tankbattle.controller;

import com.jr.tankbattle.Director;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;

import java.util.Objects;

public class HomePage {
    //游戏模式
    @FXML
    public void Single(){
        System.out.println("single mode");
        Director.getInstance().toGameScene();
    }
    @FXML
    public void Double(){
        System.out.println("double mode");
        Director.getInstance().toVsGameScene();
    }
    @FXML
    public void Match(){
        System.out.println("match mode");
        Director.getInstance().toOnlineGameScene();
    }
    //设置功能
    @FXML
    public void Setting(){
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
