package com.jr.tankbattle.scene;

import com.jr.tankbattle.client.Client;
import com.jr.tankbattle.Director;
import com.jr.tankbattle.controller.Account;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class OnlineRoomInner {

    @FXML
    private Label lblRoomStatus;
    @FXML
    private ListView<String> lvPlayers;
    @FXML
    private Button btnReady;
    @FXML
    private Button btnStartGame;
    @FXML
    private Button btnLeaveRoom;


    private Client client = new Client();
    private String roomID;
    private ScheduledExecutorService scheduler;

    @FXML
    public void initialize() {
        // Initialization code, if needed
        scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(() -> Platform.runLater(this::refreshPlayerList), 0, 5, TimeUnit.SECONDS); // Refresh every 5 seconds
        scheduler.scheduleAtFixedRate(() -> Platform.runLater(this::updateRoomStatus), 0, 5, TimeUnit.SECONDS); // Refresh every 5 seconds
    }


    public void setRoomId(String roomID) {
        this.roomID = roomID;
        // Update room status display
        lblRoomStatus.setText("Room Status: " + roomID);
        // Initial refresh of player list and room status
        refreshPlayerList();
        updateRoomStatus();
    }

    @FXML
    public void refreshPlayerList() {
        Platform.runLater(() -> {
            try {
                List<String> playerList = client.getRoomPlayerList(roomID);
                Map<String, Boolean> playerStatus = client.getPlayerReadyStatus(roomID);
                lvPlayers.getItems().clear();

                for (String player : playerList) {
                    String status = playerStatus.getOrDefault(player, false) ? "Ready" : "Not Ready";
                    lvPlayers.getItems().add(player + " - " + status);
                }
            } catch (Exception e) {
                showAlert(Alert.AlertType.ERROR, "Error", "Unable to refresh player list: " + e.getMessage());
            }
        });
    }

    @FXML
    public void markReady() {
        try {
            boolean success = client.markReady(Account.uid, roomID); // Assuming Account.uid gives the current user ID
            if (success) {
                showAlert(Alert.AlertType.INFORMATION, "Information", "You are now marked as ready!");
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to mark ready!");
            }
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Unable to mark ready: " + e.getMessage());
        }
    }

    @FXML
    public void startGame() {
        try {
            boolean success = client.startGame(roomID);
            if (success) {
                showAlert(Alert.AlertType.INFORMATION, "Information", "Game has started!");
                Director.getInstance().toOnlineGameScene();
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Unable to start the game!");
            }
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Unable to start the game: " + e.getMessage());
        }
    }

    @FXML
    public void leaveRoom() {
        try {
            boolean success = client.leaveRoom(roomID);
            if (success) {
                onClose();
                showAlert(Alert.AlertType.INFORMATION, "Information", "leave!");
                Director.getInstance().toOnlineRoom();
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Unable to leave the game!");
            }
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Unable to leave the game: " + e.getMessage());
        }
    }

    private void updateRoomStatus() {
        Platform.runLater(() -> {
            try {
                Map<String, Boolean> playerStatus = client.getPlayerReadyStatus(roomID);
                boolean allReady = true;
                for (Boolean ready : playerStatus.values()) {
                    if (!ready) {
                        allReady = false;
                        break;
                    }
                }
                lblRoomStatus.setText("Room Status: " + (allReady ? "Ready" : "Waiting for players"));
            } catch (Exception e) {
                showAlert(Alert.AlertType.ERROR, "Error", "Unable to update room status: " + e.getMessage());
            }
        });
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
