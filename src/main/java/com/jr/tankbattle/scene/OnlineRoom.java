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

public class OnlineRoom {

    @FXML
    private Label lblServerStatus;
    @FXML
    private Label lblRoomStatus;
    @FXML
    private ListView<String> lvPlayers;
    @FXML
    private TextField txtRoomId;

    private Client client = new Client();
    private String roomId;

    @FXML
    public void initialize() {
//        refreshPlayerList();
//        updateRoomStatus();
    }

    @FXML
    public void refreshPlayerList() {
        try {
            List<String> playerList = client.getOnlinePlayers();
            lvPlayers.getItems().clear();
            lvPlayers.getItems().addAll(playerList);
            lblServerStatus.setText("Server Status: Online");
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Unable to refresh player list: " + e.getMessage());
        }
    }

    @FXML
    public void joinRoom() {
        roomId = txtRoomId.getText();
        if (roomId.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Warning", "Room ID cannot be empty.");
            return;
        }

        try {
            boolean success = client.joinRoom(roomId);
            if (success) {
                showAlert(Alert.AlertType.INFORMATION, "Information", "Successfully joined the room!");
                Director.getInstance().toOnlineRoomInner(roomId); // Navigate to OnlineRoomInner
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to join the room!");
            }
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Unable to join the room: " + e.getMessage());
        }
    }

    @FXML
    public void createRoom() {
        roomId = txtRoomId.getText();
        if (roomId.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Warning", "Room ID cannot be empty.");
            return;
        }

        try {
            boolean success = client.createRoom(roomId);
            if (success) {
                showAlert(Alert.AlertType.INFORMATION, "Information", "Room created and you have joined the room!");
                Director.getInstance().toOnlineRoomInner(roomId); // Navigate to OnlineRoomInner
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to create the room!");
            }
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Unable to create the room: " + e.getMessage());
        }
    }

    @FXML
    public void backToHome() throws IOException {
        Director.getInstance().toHomePage();
    }

    private void updateRoomStatus() {
        // Update room status
        // For instance, fetch room status from the server
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
