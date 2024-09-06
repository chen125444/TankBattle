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
        volumeChg.setValue(Sound.getInstance().getBgmPlayer().getVolume()); // 设置默认音量
        // 将Slider的值改变事件与音量更新关联
        volumeChg.valueProperty().addListener((observable, oldValue, newValue) -> {
            Sound.getInstance().getBgmPlayer().setVolume(newValue.doubleValue());

            Sound.getInstance().setVVolume(newValue.doubleValue());
        });
        System.out.println(Sound.getInstance().getBgmPlayer().getVolume());
    }

    @FXML
    private SplitMenuButton bgmChg; //更改bgm功能

    public void UpdateBgmText(){ //重新载入时更新bgm名字
        if(Sound.getInstance().getBgmName()!=null){
            bgmChg.setText(Sound.getInstance().getBgmName());
        }
    }

    @FXML
    private void handleBgmChange(ActionEvent event) {
        MenuItem selectedItem = (MenuItem) event.getSource();
        String bgmName = selectedItem.getText();

        if(Objects.equals(bgmName, "bgm1")){
            Sound.getInstance().setBgmName("bgm1");
            Sound.getInstance().BgmChg(0);
        }
        else if(Objects.equals(bgmName, "bgm2")){
            Sound.getInstance().setBgmName("bgm2");
            Sound.getInstance().BgmChg(1);
        }
        else if(Objects.equals(bgmName, "bgm3")){
            Sound.getInstance().setBgmName("bgm3");
            Sound.getInstance().BgmChg(2);
        }
        else if(Objects.equals(bgmName, "bgm4")){
            Sound.getInstance().setBgmName("bgm4");
            Sound.getInstance().BgmChg(3);
        }
        else if(Objects.equals(bgmName, "bgm5")){
            Sound.getInstance().setBgmName("bgm5");
            Sound.getInstance().BgmChg(4);
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
