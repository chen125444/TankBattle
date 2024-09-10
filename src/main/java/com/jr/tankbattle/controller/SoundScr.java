package com.jr.tankbattle.controller;

import com.jr.tankbattle.Director;
import com.jr.tankbattle.util.Sound;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

import java.util.Objects;

public class SoundScr {

    public ImageView backBtn;
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


    private double originalWidth;
    private double originalHeight;
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
