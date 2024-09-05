package com.jr.tankbattle.controller;

import com.jr.tankbattle.client.Client;
import com.jr.tankbattle.Director;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.io.IOException;

public class RegisterScr {
    @FXML
    private TextField txtUid;
    @FXML
    private TextField txtPwd;

    private Client client = new Client();

    @FXML
    public void RRegister() {
        String username = txtUid.getText();
        String password = txtPwd.getText();

//        if (username.isEmpty() || password.isEmpty()) {
//            showAlert(Alert.AlertType.WARNING, "警告", "用户名或密码不能为空");
//            return;
//        }

        try {
//            client.register(username, password);
            // 根据注册结果决定跳转
            Director.getInstance().toHomePage();
        } catch (Exception e) {
//            showAlert(Alert.AlertType.ERROR, "错误", "注册失败: " + e.getMessage());
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
}
