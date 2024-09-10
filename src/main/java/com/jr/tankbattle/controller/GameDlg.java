package com.jr.tankbattle.controller;

import com.jr.tankbattle.Director;
import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

public class GameDlg extends Dialog<ButtonType> {
    private static GameDlg instance = new GameDlg();

    public static GameDlg getInstance() {
        return instance;
    }

    VBox vBox;
    Label label;
    Button ensureBtn=new Button();
    Button cancelBtn=new Button();
    private boolean flag = false;
    public GameDlg() {
        setTitle("TankBattle");
        Image icon = new Image(this.getClass().getResourceAsStream("/com/jr/tankbattle/img/logo1.png"));
        Stage stage = (Stage) getDialogPane().getScene().getWindow();
        stage.getIcons().add(icon);

        // 设置DialogPane背景颜色
        DialogPane dialogPane =getDialogPane();
        dialogPane.setBackground(new Background(new BackgroundFill(
                Color.web("#ffffff"), CornerRadii.EMPTY, Insets.EMPTY)));


        // 设置按钮大小
        ensureBtn.setMinWidth(80);
        ensureBtn.setMinHeight(30);
        cancelBtn.setMinWidth(80);
        cancelBtn.setMinHeight(30);

        // 设置按钮颜色
        ensureBtn.setStyle("-fx-background-color: #cd9d62; " + // 背景颜色
                "-fx-text-fill: white; " + // 字体颜色
                "-fx-border-color: #a47c47; " + // 边框颜色
                "-fx-border-width: 2px;"); // 边框宽度

        cancelBtn.setStyle("-fx-background-color: #cd9d62; " + // 背景颜色
                "-fx-text-fill: white; " + // 字体颜色
                "-fx-border-color: #a47c47; " + // 边框颜色
                "-fx-border-width: 2px;"); // 边框宽度

        // 设置按钮的居中对齐
        ensureBtn.setDefaultButton(false); // 取消默认行为
        VBox.setMargin(ensureBtn, new javafx.geometry.Insets(10, 0, 0, 0)); // 为按钮添加边距
        VBox.setMargin(cancelBtn, new javafx.geometry.Insets(0, 0, 0, 0));

        vBox = new VBox(10); // 垂直排列，间距为 20
        vBox.setPrefWidth(300);
        vBox.setPrefHeight(200);
        vBox.setAlignment(Pos.CENTER); // 设置居中对齐

        // 设置背景图片并自适应铺满
        //Image backgroundImage = new Image(getClass().getResource("/com/jr/tankbattle/img/ui板2.png").toExternalForm());
        Image backgroundImage = new Image(getClass().getResource("/com/jr/tankbattle/img/ui板5.png").toExternalForm());
        BackgroundSize backgroundSize = new BackgroundSize(1, 1, true, true, false, false);

        BackgroundImage background = new BackgroundImage(backgroundImage,
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER, backgroundSize);

        vBox.setBackground(new Background(background)); // 应用到 vBox

        if (backgroundImage.isError()) {
            System.out.println("Background image failed to load");
        } else {
            System.out.println("Background image loaded successfully");
        }



        // 创建Label并设置文本大小和粗细
        label = new Label();
        // 设置字体大小、粗细、颜色和字体类型
        label.setStyle("-fx-font-size: 18px; " +
                "-fx-font-weight: bold; " +
                "-fx-text-fill: #ffffff; " + // 深金黄色字体颜色
                "-fx-font-family: 'Arial';"); // 设置字体类型为 Arial
        VBox.setMargin(label, new javafx.geometry.Insets(20, 0, 0, 0));

        vBox.getChildren().add(label);
        getDialogPane().setContent(vBox);
    }

    public void Show(String type) {
        if(type=="single"||type=="double"||type=="match") { //游戏模式弹窗
            ensureBtn.setText("开始游戏");
            cancelBtn.setText("取消");

            // 清除Dialog中的按钮，并将新的按钮添加到VBox
            vBox.getChildren().removeIf(node -> node instanceof Button); // 清除已有按钮
            vBox.getChildren().addAll(ensureBtn, cancelBtn); // 将按钮添加到VBox

            // 根据不同类型设置Label文本内容
            if ("single".equals(type)) {
                label.setText("你确定要开始“单人模式”吗？");
            } else if ("double".equals(type)) {
                label.setText("你确定要开始“双人模式”吗？");
            } else {
                label.setText("你确定要开始“联机模式”吗？");
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


        else if (type=="register") { //登陆成功弹窗
            ensureBtn.setText("确定");
            cancelBtn.setText("返回");

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


        else if (type == "loginEr") { //登录错误弹窗
            ensureBtn.setText("确定");
            cancelBtn.setText("返回");

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


        else if (type == "registerEr") { //登录错误弹窗
            ensureBtn.setText("确定");
            cancelBtn.setText("返回");

            // 清除Dialog中的按钮，并将新的按钮添加到VBox
            vBox.getChildren().removeIf(node -> node instanceof Button); // 清除已有按钮
            vBox.getChildren().addAll(ensureBtn, cancelBtn); // 将按钮添加到VBox

            label.setText("注册失败，请重新注册");

            // 设置按钮的事件
            ensureBtn.setOnAction(event -> {
                Director.getInstance().toRegisterScr();
                setResult(ButtonType.OK); // 手动设置结果并关闭对话框
                close(); // 关闭窗口
            });

            cancelBtn.setOnAction(event -> {
                Director.getInstance().toStartScr();
                setResult(ButtonType.CANCEL); // 设置取消结果
                close(); // 关闭窗口
            });
        }


        else if(type=="loginBlank"||type=="registerBlank") { //输入为空弹窗
            ensureBtn.setText("确定");
            cancelBtn.setText("返回");

            // 清除Dialog中的按钮，并将新的按钮添加到VBox
            vBox.getChildren().removeIf(node -> node instanceof Button); // 清除已有按钮
            vBox.getChildren().addAll(ensureBtn, cancelBtn); // 将按钮添加到VBox

            label.setText("用户名或密码不能为空！");

            // 设置按钮的事件
            ensureBtn.setOnAction(event -> {
                if(type=="loginBlank") {
                    Director.getInstance().toLoginScr();
                }
                else{
                    Director.getInstance().toRegisterScr();
                }
                setResult(ButtonType.OK); // 手动设置结果并关闭对话框
                close(); // 关闭窗口
            });

            cancelBtn.setOnAction(event -> {
                Director.getInstance().toStartScr();
                setResult(ButtonType.CANCEL); // 设置取消结果
                close(); // 关闭窗口
            });
        }


        else if(type=="pause"){ //游戏暂停
            ensureBtn.setText("继续游戏");
            cancelBtn.setText("结束游戏");

            // 清除Dialog中的按钮，并将新的按钮添加到VBox
            vBox.getChildren().removeIf(node -> node instanceof Button); // 清除已有按钮
            vBox.getChildren().addAll(ensureBtn, cancelBtn); // 将按钮添加到VBox

            label.setText("游戏已暂停");

            // 设置按钮的事件
            ensureBtn.setOnAction(event -> {
                //Director.getInstance().toRegisterScr();
                setResult(ButtonType.OK); // 手动设置结果并关闭对话框\
                flag = true ;
                close(); // 关闭窗口
            });

            cancelBtn.setOnAction(event -> {
                Director.getInstance().toHomePage();
                setResult(ButtonType.CANCEL); // 设置取消结果
                close(); // 关闭窗口
            });
        }


        else if(type=="gameLoseSingle"){ //单人模式游戏失败
            ensureBtn.setText("重新开始");
            cancelBtn.setText("退出游戏");

            // 清除Dialog中的按钮，并将新的按钮添加到VBox
            vBox.getChildren().removeIf(node -> node instanceof Button); // 清除已有按钮
            vBox.getChildren().addAll(ensureBtn, cancelBtn); // 将按钮添加到VBox

            label.setText("作战失败,游戏结束！");

            // 设置按钮的事件
            ensureBtn.setOnAction(event -> {
                Director.getInstance().toGameScene();
                setResult(ButtonType.OK); // 手动设置结果并关闭对话框
                close(); // 关闭窗口
            });

            cancelBtn.setOnAction(event -> {
                Director.getInstance().toHomePage();
                setResult(ButtonType.CANCEL); // 设置取消结果
                close(); // 关闭窗口
            });
        }


        else if(type=="gameWinSingle"){ //单人模式游戏胜利
            ensureBtn.setText("再来一局");
            cancelBtn.setText("返回");

            // 清除Dialog中的按钮，并将新的按钮添加到VBox
            vBox.getChildren().removeIf(node -> node instanceof Button); // 清除已有按钮
            vBox.getChildren().addAll(ensureBtn, cancelBtn); // 将按钮添加到VBox

            label.setText("恭喜你，游戏胜利！");

            // 设置按钮的事件
            ensureBtn.setOnAction(event -> {
                Director.getInstance().toGameScene();
                setResult(ButtonType.OK); // 手动设置结果并关闭对话框
                close(); // 关闭窗口
            });

            cancelBtn.setOnAction(event -> {
                Director.getInstance().toHomePage();
                setResult(ButtonType.CANCEL); // 设置取消结果
                close(); // 关闭窗口
            });
        }


        else if(type=="gameOverDouble"){ //双人模式游戏结束
            ensureBtn.setText("重新开始");
            cancelBtn.setText("退出游戏");

            // 清除Dialog中的按钮，并将新的按钮添加到VBox
            vBox.getChildren().removeIf(node -> node instanceof Button); // 清除已有按钮
            vBox.getChildren().addAll(ensureBtn, cancelBtn); // 将按钮添加到VBox
            label.setText("游戏结束！");

            // 设置按钮的事件
            ensureBtn.setOnAction(event -> {
                //Director.getInstance().toRegisterScr();
                setResult(ButtonType.OK); // 手动设置结果并关闭对话框
                close(); // 关闭窗口
            });

            cancelBtn.setOnAction(event -> {
                Director.getInstance().toHomePage();
                setResult(ButtonType.CANCEL); // 设置取消结果
                close(); // 关闭窗口
            });
        }

        // 显示Dialog并等待用户响应
        showAndWait();
    }
    public boolean isFlag(){
        return flag;
    }
}

