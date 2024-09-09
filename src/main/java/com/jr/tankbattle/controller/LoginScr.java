package com.jr.tankbattle.controller;

import com.jr.tankbattle.client.Client;
import com.jr.tankbattle.Director;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.io.IOException;

public class LoginScr {
    @FXML
    private TextField txtUid;
    @FXML
    private TextField txtPwd;

    private Client client = new Client();

    @FXML
    public void LLogin() {
        String username = txtUid.getText();
        String password = txtPwd.getText();

//        if (username.isEmpty() || password.isEmpty()) {
//            showAlert(Alert.AlertType.WARNING, "警告", "用户名或密码不能为空");
//            return;
//        }

        try {
//            boolean loginSuccess = client.login(username, password);
//            if (loginSuccess) {
//                Account.setInfo(username, password);
                Director.getInstance().toHomePage();
//            } else {
//                GameDlg.getInstance().Show("loginEr");//登录失败弹窗
//            }
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
}
