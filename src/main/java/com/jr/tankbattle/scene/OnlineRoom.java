package com.jr.tankbattle.scene;

import com.jr.tankbattle.client.Client;
import com.jr.tankbattle.Director;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class OnlineRoom {

    @FXML
    private Label lblServerStatus;
    @FXML
    private Label lblRoomStatus;
    @FXML
    private Label lblPlayerCount; // 显示玩家数量的标签
    @FXML
    private ListView<String> lvPlayers;
    @FXML
    private TextField txtRoomId;

    private Client client = new Client();
    private String roomId;
    private ScheduledExecutorService scheduler;

    @FXML
    public void initialize() {
        // 初始化定时任务
        scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(this::refreshPlayerList, 0, 5, TimeUnit.SECONDS); // 每5秒更新一次
    }

    @FXML
    public void refreshPlayerList() {
        try {
            List<String> playerList = client.getOnlinePlayers();
            javafx.application.Platform.runLater(() -> {
                lvPlayers.getItems().clear();
                lvPlayers.getItems().addAll(playerList);
                lblPlayerCount.setText("玩家数量: " + playerList.size()); // 更新玩家数量
                lblServerStatus.setText("服务器状态: 在线");
            });
        } catch (Exception e) {
            javafx.application.Platform.runLater(() -> showAlert(Alert.AlertType.ERROR, "错误", "无法刷新玩家列表: " + e.getMessage()));
        }
    }

    @FXML
    public void joinRoom() {
        roomId = txtRoomId.getText();
        if (roomId.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "警告", "房间ID不能为空。");
            return;
        }

        try {
            boolean success = client.joinRoom(roomId);
            if (success) {
                showAlert(Alert.AlertType.INFORMATION, "信息", "成功加入房间！");
                Director.getInstance().toOnlineRoomInner(roomId); // 导航到 OnlineRoomInner
            } else {
                showAlert(Alert.AlertType.ERROR, "错误", "加入房间失败！");
            }
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "错误", "无法加入房间: " + e.getMessage());
        }
    }

    @FXML
    public void createRoom() {
        roomId = txtRoomId.getText();
        if (roomId.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "警告", "房间ID不能为空。");
            return;
        }

        try {
            boolean success = client.createRoom(roomId);
            if (success) {
                showAlert(Alert.AlertType.INFORMATION, "信息", "房间创建成功并已加入房间！");
                Director.getInstance().toOnlineRoomInner(roomId); // 导航到 OnlineRoomInner
            } else {
                showAlert(Alert.AlertType.ERROR, "错误", "创建房间失败！");
            }
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "错误", "无法创建房间: " + e.getMessage());
        }
    }

    @FXML
    public void backToHome() throws IOException {
        Director.getInstance().toHomePage();
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
