package com.jr.tankbattle.controller;

import com.jr.tankbattle.Director;
import com.jr.tankbattle.util.Sound;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.scene.control.SplitMenuButton;

import java.util.Objects;

public class SoundScr {
    @FXML
    private Slider volumeChg;
    @FXML
    public void initialize() {
        // 初始化音量滑块
        volumeChg.setMin(0.0);
        volumeChg.setMax(1.0);
        volumeChg.setValue(0.5); // 设置默认音量

        // 将Slider的值改变事件与音量更新关联
        volumeChg.valueProperty().addListener((observable, oldValue, newValue) -> {
            Sound.getInstance().getBgmPlayer().setVolume(newValue.doubleValue());
        });
    }

    @FXML
    private SplitMenuButton bgmChg; //更改bgm功能
    @FXML
    private void handleBgmChange(ActionEvent event) {
        MenuItem selectedItem = (MenuItem) event.getSource();
        String bgmName = selectedItem.getText();
        if(Objects.equals(bgmName, "bgm1")){
            Sound.getInstance().BgmChg(0);
        }
        else if(Objects.equals(bgmName, "bgm2")){
            Sound.getInstance().BgmChg(1);
        }

        bgmChg.setText(bgmName);
        System.out.println("Selected BGM: " + bgmName);
    }

    @FXML
    public void Back(){
        System.out.println("back");
        Director.getInstance().toSetting();
    }
}
