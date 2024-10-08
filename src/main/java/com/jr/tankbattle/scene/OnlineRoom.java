package com.jr.tankbattle.scene;

import com.jr.tankbattle.client.Client;
import com.jr.tankbattle.Director;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class OnlineRoom {

    @FXML
    private Label lblServerStatus;
    @FXML
    private ListView<String> lvPlayers;
    @FXML
    private ListView<String> lvRooms;
    @FXML
    private TextField txtRoomId;

    private Client client = new Client();
    private String roomId;
    private ScheduledExecutorService scheduler;

    @FXML
    public void initialize() {
        scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(this::refreshPlayerList, 0, 5, TimeUnit.SECONDS); // 更新玩家列表
        scheduler.scheduleAtFixedRate(this::refreshRoomList, 0, 5, TimeUnit.SECONDS); // 更新房间列表
    }

    @FXML
    public void refreshPlayerList() {
        try {
            List<String> playerList = client.getOnlinePlayers();

            javafx.application.Platform.runLater(() -> {
                lvPlayers.getItems().clear();
                lvPlayers.getItems().addAll(playerList);

                lblServerStatus.setText("服务器状态: 在线");
            });
        } catch (Exception e) {
            javafx.application.Platform.runLater(() -> showAlert(Alert.AlertType.ERROR, "错误", "无法刷新玩家列表: " + e.getMessage()));
        }
    }

    @FXML
    public void refreshRoomList() {
        try {
            List<String> roomIds = client.getOnlineRooms(); // 获取所有房间 ID
            List<String> roomDisplayInfo = new ArrayList<>();

            if (roomIds.isEmpty()) {
                roomDisplayInfo.add("当前没有可用的房间"); // 显示房间列表为空的提示
            } else {
                for (String roomId : roomIds) {
                    int playerCount = client.getRoomPlayerCount(roomId); // 获取房间的玩家人数
                    roomDisplayInfo.add(roomId + "\t\t\t\t\t\t" + playerCount + "/4"); // 格式化显示房间 ID 和人数
                }
            }

            javafx.application.Platform.runLater(() -> {
                lvRooms.getItems().clear();
                lvRooms.getItems().addAll(roomDisplayInfo);
            });
        } catch (Exception e) {
            javafx.application.Platform.runLater(() -> showAlert(Alert.AlertType.ERROR, "错误", "无法刷新房间列表: " + e.getMessage()));
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
                onClose();
                showAlert(Alert.AlertType.INFORMATION, "信息", "房间创建成功！");
                Director.getInstance().toOnlineRoomInner(roomId);
            } else {
                showAlert(Alert.AlertType.ERROR, "错误", "创建房间失败！");
            }
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "错误", "无法创建房间: " + e.getMessage());
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
                onClose();
                showAlert(Alert.AlertType.INFORMATION, "信息", "成功加入房间！");
                Director.getInstance().toOnlineRoomInner(roomId);
            } else {
                showAlert(Alert.AlertType.ERROR, "错误", "加入房间失败！");
            }
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "错误", "无法加入房间: " + e.getMessage());
        }
    }

    @FXML
    public void backToHome() throws IOException {
        onClose();
        Director.getInstance().toHomePage();
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    public void onClose() {
        if (scheduler != null && !scheduler.isShutdown()) {
            scheduler.shutdown();
        }
    }
}
