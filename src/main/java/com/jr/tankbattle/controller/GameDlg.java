package com.jr.tankbattle.controller;

import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.image.Image;

import javax.swing.*;
import java.awt.*;
import java.util.Optional;

import static javax.swing.text.StyleConstants.setIcon;

public class GameDlg extends Dialog {
    //private Dialog<ButtonType> dialog = new Dialog<>();

    public GameDlg(){
        setTitle("TankBattle");
        Image icon = new Image(this.getClass().getResourceAsStream("/com/jr/tankbattle/img/logo.png"));
        //setIcon(icon);
    }

    public void Show() {
        // 设置头部内容
        setHeaderText("你确定要开始游戏吗？");
        // 设置主体内容
        setContentText("点击确定开始游戏，否则游戏将不会开始。");

        // 创建两个按钮，分别代表确定和取消
        ButtonType startGameButton = new ButtonType("开始游戏", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButton = new ButtonType("取消", ButtonBar.ButtonData.CANCEL_CLOSE);

        // 设置Dialog的按钮
        getDialogPane().getButtonTypes().addAll(startGameButton, cancelButton);

        setResultConverter(dialogButton -> {
            if (dialogButton == startGameButton) {
                return startGameButton;
            }
            return cancelButton;
        });

        // 显示Dialog并等待用户响应
        Optional<ButtonType> result = showAndWait();

        if (result.get() == startGameButton) {
            // 用户点击了确定，可以添加开始游戏的逻辑
            System.out.println("开始游戏！");
        } else {
            // 用户点击了取消
            System.out.println("游戏未开始！");
        }
    }
}
