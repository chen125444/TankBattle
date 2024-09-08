/*
package com.jr.tankbattle.controller;

import com.jr.tankbattle.Director;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;

import java.util.Optional;

public class GameDlg extends Dialog {
    private static GameDlg instance = new GameDlg();

    public static GameDlg getInstance() {
        return instance;
    }

    ButtonType ensureButton;
    ButtonType cancelButton;
    VBox vBox;
    Label label;

    public GameDlg() {
        setTitle("TankBattle");
        Image icon = new Image(this.getClass().getResourceAsStream("/com/jr/tankbattle/img/logo.png"));

        vBox = new VBox(10);
        vBox.setPrefWidth(300);
        vBox.setPrefHeight(200);
        vBox.setAlignment(Pos.CENTER); // 设置居中对齐

        // 创建Label并设置文本大小和粗细
        label = new Label();
        label.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;"); // 设置字体大小和粗细

        vBox.getChildren().add(label);
        getDialogPane().setContent(vBox);
    }

    public void Show(String type) {

        // 创建两个按钮，分别代表确定和取消
        ensureButton = new ButtonType("开始游戏", ButtonBar.ButtonData.OK_DONE);
        cancelButton = new ButtonType("取消", ButtonBar.ButtonData.CANCEL_CLOSE);


        // 设置Dialog的按钮
        getDialogPane().getButtonTypes().clear(); // 清除之前的按钮
        getDialogPane().getButtonTypes().addAll(ensureButton, cancelButton);

        // 获取开始按钮并取消其默认行为
        Button ensureBtn = (Button) getDialogPane().lookupButton(ensureButton);
        ensureBtn.setDefaultButton(false);  // 取消默认按钮行为

        // 根据不同类型设置Label文本内容
        if ("single".equals(type)) {
            label.setText("你确定要开始单人模式吗？");
        } else if ("double".equals(type)) {
            label.setText("你确定要开始双人模式吗？");
        } else if ("match".equals(type)) {
            label.setText("你确定要开始联机模式吗？");
        }

        // 显示Dialog并等待用户响应
        Optional<ButtonType> result = showAndWait();

        if (result.isPresent() && result.get() == ensureButton) {
            if ("single".equals(type)) {
                Director.getInstance().toGameScene();
            } else if ("double".equals(type)) {
                Director.getInstance().toVsGameScene();
            } else if ("match".equals(type)) {
                Director.getInstance().toOnlineGameScene();
            }
        } else {
            hide();
            getDialogPane().getButtonTypes().clear();
        }
    }
}
*/

package com.jr.tankbattle.controller;

import com.jr.tankbattle.Director;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class GameDlg extends Dialog<ButtonType> {
    private static GameDlg instance = new GameDlg();

    public static GameDlg getInstance() {
        return instance;
    }

    VBox vBox;
    Label label;
    Button ensureBtn;
    Button cancelBtn;

    public GameDlg() {
        setTitle("TankBattle");
        Image icon = new Image(this.getClass().getResourceAsStream("/com/jr/tankbattle/img/logo.png"));
        Stage stage = (Stage) getDialogPane().getScene().getWindow();
        stage.getIcons().add(icon);

        vBox = new VBox(20); // 垂直排列，间距为 20
        vBox.setPrefWidth(300);
        vBox.setPrefHeight(200);
        vBox.setAlignment(Pos.CENTER); // 设置居中对齐

        // 设置背景图片并自适应铺满
        Image backgroundImage = new Image(this.getClass().getResourceAsStream("/com/jr/tankbattle/img/background.jpg"));
        BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, false, true);
        BackgroundImage background = new BackgroundImage(backgroundImage,
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER, backgroundSize);
        vBox.setBackground(new Background(background)); // 应用到 vBox


        // 创建Label并设置文本大小和粗细
        label = new Label();
        label.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;"); // 设置字体大小和粗细

        vBox.getChildren().add(label);
        getDialogPane().setContent(vBox);
    }

    public void Show(String type) {
        if(type=="single"||type=="double"||type=="match") {
            // 创建两个按钮
            ensureBtn = new Button("开始游戏");
            cancelBtn = new Button("取消");

            // 设置按钮大小
            ensureBtn.setMinWidth(100);
            cancelBtn.setMinWidth(100);

            // 设置按钮的居中对齐
            ensureBtn.setDefaultButton(false); // 取消默认行为
            VBox.setMargin(ensureBtn, new javafx.geometry.Insets(10, 0, 0, 0)); // 为按钮添加边距
            VBox.setMargin(cancelBtn, new javafx.geometry.Insets(10, 0, 0, 0));

            // 清除Dialog中的按钮，并将新的按钮添加到VBox
            vBox.getChildren().removeIf(node -> node instanceof Button); // 清除已有按钮
            vBox.getChildren().addAll(ensureBtn, cancelBtn); // 将按钮添加到VBox

            // 根据不同类型设置Label文本内容
            if ("single".equals(type)) {
                label.setText("你确定要开始单人模式吗？");
            } else if ("double".equals(type)) {
                label.setText("你确定要开始双人模式吗？");
            } else {
                label.setText("你确定要开始联机模式吗？");
            }

            // 设置按钮的事件
            ensureBtn.setOnAction(event -> {
                if ("single".equals(type)) {
                    Director.getInstance().toGameScene();
                } else if ("double".equals(type)) {
                    Director.getInstance().toVsGameScene();
                } else {
                    Director.getInstance().toOnlineRoom();
                }
                setResult(ButtonType.OK); // 手动设置结果并关闭对话框
                close(); // 关闭窗口
            });

            cancelBtn.setOnAction(event -> {
                setResult(ButtonType.CANCEL); // 设置取消结果
                close(); // 关闭窗口
            });
        }
        else if (type=="register") {
            // 创建两个按钮
            ensureBtn = new Button("确定");
            cancelBtn = new Button("返回");

            // 设置按钮大小
            ensureBtn.setMinWidth(100);
            cancelBtn.setMinWidth(100);

            // 设置按钮的居中对齐
            ensureBtn.setDefaultButton(false); // 取消默认行为
            VBox.setMargin(ensureBtn, new javafx.geometry.Insets(10, 0, 0, 0)); // 为按钮添加边距
            VBox.setMargin(cancelBtn, new javafx.geometry.Insets(10, 0, 0, 0));

            // 清除Dialog中的按钮，并将新的按钮添加到VBox
            vBox.getChildren().removeIf(node -> node instanceof Button); // 清除已有按钮
            vBox.getChildren().addAll(ensureBtn, cancelBtn); // 将按钮添加到VBox

            label.setText("注册成功，请重新登陆");

            // 设置按钮的事件
            ensureBtn.setOnAction(event -> {
                Director.getInstance().toLoginScr();
                setResult(ButtonType.OK); // 手动设置结果并关闭对话框
                close(); // 关闭窗口
            });

            cancelBtn.setOnAction(event -> {
                Director.getInstance().toStartScr();
                setResult(ButtonType.CANCEL); // 设置取消结果
                close(); // 关闭窗口
            });
        }
        else if (type == "loginEr") {
            // 创建两个按钮
            ensureBtn = new Button("确定");
            cancelBtn = new Button("返回");

            // 设置按钮大小
            ensureBtn.setMinWidth(100);
            cancelBtn.setMinWidth(100);

            // 设置按钮的居中对齐
            ensureBtn.setDefaultButton(false); // 取消默认行为
            VBox.setMargin(ensureBtn, new javafx.geometry.Insets(10, 0, 0, 0)); // 为按钮添加边距
            VBox.setMargin(cancelBtn, new javafx.geometry.Insets(10, 0, 0, 0));

            // 清除Dialog中的按钮，并将新的按钮添加到VBox
            vBox.getChildren().removeIf(node -> node instanceof Button); // 清除已有按钮
            vBox.getChildren().addAll(ensureBtn, cancelBtn); // 将按钮添加到VBox

            label.setText("登录失败，请重新登陆");

            // 设置按钮的事件
            ensureBtn.setOnAction(event -> {
                Director.getInstance().toLoginScr();
                setResult(ButtonType.OK); // 手动设置结果并关闭对话框
                close(); // 关闭窗口
            });

            cancelBtn.setOnAction(event -> {
                Director.getInstance().toStartScr();
                setResult(ButtonType.CANCEL); // 设置取消结果
                close(); // 关闭窗口
            });
        }

        // 显示Dialog并等待用户响应
        showAndWait();
    }
}

