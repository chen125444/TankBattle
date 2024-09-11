package com.jr.tankbattle.controller;

import com.jr.tankbattle.Director;
import com.jr.tankbattle.util.MapData;
import javafx.fxml.FXML;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

import java.util.Objects;

public class MapScr {
    private static MapScr instance = new MapScr();
    public static MapScr getInstance() {
        return instance;
    }

    public static int id =1;
    @FXML
    public ImageView hoverImage;
    @FXML
    public ImageView map1Btn;
    @FXML
    public ImageView map2Btn;
    @FXML
    public ImageView map3Btn;
    @FXML
    public ImageView map4Btn;
    @FXML
    public ImageView backBtn;
    @FXML
    public ImageView mapImage;


    @FXML
    public void initialize() {
        MapChg(1);

        // 创建亮度调整效果和阴影效果
        colorAdjust = new ColorAdjust();
        shadow = new DropShadow();
        shadow.setColor(Color.GRAY);
        shadow.setRadius(10);

        // 将效果应用到多个按钮
        applyEffects(map1Btn, "/com/jr/tankbattle/img/tankbackground3.png");
        applyEffects(map2Btn, "/com/jr/tankbattle/img/tankbackground4.png");
        applyEffects(map3Btn, "/com/jr/tankbattle/img/tankbackground8.jpg");
        applyEffects(map4Btn, "/com/jr/tankbattle/img/bkg.png");
        applyEffects(backBtn);
        System.out.println(id);
    }

    private void showHoverImage(String imagePath) {
        hoverImage.setImage(new Image(getClass().getResource(imagePath).toExternalForm()));
        hoverImage.setVisible(true);
    }

    private void hideHoverImage() {
        hoverImage.setVisible(false);
    }

    private DropShadow shadow;
    private ColorAdjust colorAdjust;


    private void applyEffects(ImageView button,String hoverImagePath) {
        // 初始化参数
        button.setOnMouseEntered(event -> {
            showHoverImage(hoverImagePath); //鼠标悬停图片显示
            colorAdjust.setBrightness(0.3);  // 增加亮度
            button.setEffect(shadow);        // 添加阴影
            System.out.println(id);
        });

        button.setOnMouseExited(event -> {
            hideHoverImage();
            colorAdjust.setBrightness(0);    // 还原亮度
            button.setEffect(null);          // 移除阴影
            System.out.println(id);
        });

        button.setOnMousePressed(event -> {
            button.setScaleX(0.95);  // 缩小到 95%
            button.setScaleY(0.95);  // 缩小到 95%
            System.out.println(id);
        });

        button.setOnMouseReleased(event -> {
            button.setScaleX(1.0);   // 还原到原始大小
            button.setScaleY(1.0);   // 还原到原始大小
            System.out.println(id);
        });
    }

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

    @FXML
    public void Back(){
        System.out.println(id);
        Director.getInstance().toSetting();
    }
    @FXML
    public void Map1(){
        MapChg(1);
        id = 1;
    }
    @FXML
    public void Map2(){
        MapChg(2);
        id = 2;
    }
    @FXML
    public void Map3(){
        MapChg(3);
        id = 3;
    }
    @FXML
    public void Map4(){
        MapChg(4);
        id = 4;
    }

    public void MapChg(int num){
        if(num==1){
            mapImage.setImage(new Image(getClass().getResource("/com/jr/tankbattle/img/tankbackground3.png").toExternalForm()));
        }
        else if(num==2){
            mapImage.setImage(new Image(getClass().getResource("/com/jr/tankbattle/img/tankbackground4.png").toExternalForm()));
        }
        else if(num==3){
            mapImage.setImage(new Image(getClass().getResource("/com/jr/tankbattle/img/tankbackground8.jpg").toExternalForm()));
        }
        else{
            mapImage.setImage(new Image(getClass().getResource("/com/jr/tankbattle/img/bkg.png").toExternalForm()));
        }
    }
    public int getId(){
        return id;
    }
}
