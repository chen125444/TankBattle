package com.jr.tankbattle.controller;

import com.jr.tankbattle.Director;
import javafx.scene.control.*;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.FontWeight;

import javax.swing.*;
import java.awt.*;
import java.util.Optional;

import static java.awt.Font.*;
import static javax.swing.text.StyleConstants.setIcon;

public class GameDlg extends Dialog {
    private static GameDlg instance=new GameDlg();
    public static GameDlg getInstance() {
        return instance;
    }

    ButtonType ensureButton;
    ButtonType cancelButton;

    public GameDlg(){
        setTitle("TankBattle");
        Image icon = new Image(this.getClass().getResourceAsStream("/com/jr/tankbattle/img/logo.png"));
        //setIcon(icon);

        VBox vBox = new VBox(10);
        vBox.setPrefWidth(300); // 设置首选宽度
        vBox.setPrefHeight(50); // 设置首选高度
        // 创建一个Label来显示文本
        Label label = new Label("点击确定开始游戏，否则游戏将不会开始。");
        // 将Label添加到VBox中
        vBox.getChildren().add(label);
        getDialogPane().setContent(vBox);

        /* 创建DialogPane
        DialogPane dialogPane = getDialogPane();
        // 创建一个VBox来放置内容
        VBox vBox = new VBox(10);
        vBox.setPrefWidth(300);
        vBox.setPrefHeight(100);
        // 创建一个Label来显示文本
        Label label = new Label("点击确定开始游戏，否则游戏将不会开始。");
        // 将Label添加到VBox中
        vBox.getChildren().add(label);
        // 创建一个StackPane来设置背景图像
        StackPane stackPane = new StackPane();
        stackPane.getChildren().add(vBox); // 将VBox添加到StackPane中
        // 设置背景图像
        Image image = new Image(this.getClass().getResourceAsStream("/com/jr/tankbattle/img/logo.png")); // 替换为你的图片路径
        stackPane.setBackground(new Background(new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
        // 设置DialogPane的内容为StackPane
        getDialogPane().setContent(stackPane);*/

        // 设置自定义图标
        ImageView imageView = new ImageView(new Image(this.getClass().getResourceAsStream("/com/jr/tankbattle/img/logo.png")));
        imageView.setFitWidth(70); // 根据需要调整大小
        imageView.setFitHeight(60);
        getDialogPane().setGraphic(imageView);
    }

    public void Show(String type) {

            // 创建两个按钮，分别代表确定和取消
            ensureButton = new ButtonType("开始游戏", ButtonBar.ButtonData.OK_DONE);
            cancelButton = new ButtonType("取消", ButtonBar.ButtonData.CANCEL_CLOSE);

            // 设置Dialog的按钮
            getDialogPane().getButtonTypes().addAll(ensureButton, cancelButton);

            setResultConverter(dialogButton -> {
                if (dialogButton ==ensureButton) {
                    return ensureButton;
                }
                return cancelButton;
            });

            if (type == "single"||type=="double"||type=="match") {
                if(type=="single") {
                    // 设置头部内容
                    setHeaderText("你确定要开始单人模式吗？");
                    // 显示Dialog并等待用户响应
                    Optional<ButtonType> result = showAndWait();

                    if (result.get() == ensureButton) {
                        Director.getInstance().toGameScene();
                    }
                    else {
                        hide();
                        getDialogPane().getButtonTypes().clear();
                    }
                }
                else if (type=="double") {
                    // 设置头部内容
                    setHeaderText("你确定要开始双人模式吗？");
                    // 显示Dialog并等待用户响应
                    Optional<ButtonType> result = showAndWait();

                    if (result.get() == ensureButton) {
                        Director.getInstance().toVsGameScene();
                    }
                    else {
                        hide();
                        getDialogPane().getButtonTypes().clear();
                    }
                }
                else{
                    // 设置头部内容
                    setHeaderText("你确定要开始联机模式吗？");
                    // 显示Dialog并等待用户响应
                    Optional<ButtonType> result = showAndWait();

                    if (result.get() == ensureButton) {
                        Director.getInstance().toOnlineRoom();
                    }
                    else {
                        hide();
                        getDialogPane().getButtonTypes().clear();
                    }
                }
        }
    }
}
