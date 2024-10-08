package com.jr.tankbattle.controller;

import com.jr.tankbattle.client.Client;
import com.jr.tankbattle.Director;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

import java.io.IOException;

public class LoginScr {
    public ImageView loginBtn;
    public ImageView backBtn;
    @FXML
    private TextField txtUid;
    @FXML
    private TextField txtPwd;

    private Client client = new Client();

   @FXML
    public void LLogin() {
        String username = txtUid.getText();
        String password = txtPwd.getText();

        if (username.isEmpty() || password.isEmpty()) {
            GameDlg.getInstance().Show("loginBlank");
            return;
        }

        try {
           boolean loginSuccess = client.login(username, password);
           if (loginSuccess) {
               Account.setInfo(username, password);
               Director.getInstance().toHomePage();
           } else {
                GameDlg.getInstance().Show("loginEr");//登录失败弹窗
            }
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "错误", "登录失败: " + e.getMessage());
        }
    }


    @FXML
    public void Back() throws IOException {
        Director.getInstance().toStartScr();
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    private DropShadow shadow;
    private ColorAdjust colorAdjust;

    @FXML
    public void initialize() {
        // 创建亮度调整效果和阴影效果
        colorAdjust = new ColorAdjust();
        shadow = new DropShadow();
        shadow.setColor(Color.GRAY);
        shadow.setRadius(10);

        // 将效果应用到多个按钮
        applyEffects(loginBtn);
        applyEffects(backBtn);
    }

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
