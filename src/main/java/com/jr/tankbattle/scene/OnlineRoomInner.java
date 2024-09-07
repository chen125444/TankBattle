package com.jr.tankbattle.scene;

import com.jr.tankbattle.client.Client;
import com.jr.tankbattle.Director;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.util.List;

public class OnlineRoomInner {

    @FXML
    private Label lblRoomStatus;
    @FXML
    private ListView<String> lvPlayers;
    @FXML
    private Button btnReady;
    @FXML
    private Button btnStartGame;

    private Client client = new Client();
    private String roomID;

    @FXML
    public void initialize() {
        // Initialization code, if needed
//        refreshPlayerList();
//        updateRoomStatus();
    }

    public void setRoomId(String roomID) {
        this.roomID = roomID;
        // Update room status display
        lblRoomStatus.setText("Room Status: " + roomID);
        // Optionally: refresh player list or other initialization based on room ID
    }

    @FXML
//    public void refreshPlayerList() {
//        try {
//            List<String> playerList = client.getRoomPlayers(roomId);
//            lvPlayers.getItems().clear();
//            lvPlayers.getItems().addAll(playerList);
//            lblRoomStatus.setText("Room Status: " + (client.isRoomReady(roomId) ? "Ready" : "Waiting for players"));
//        } catch (Exception e) {
//            showAlert(Alert.AlertType.ERROR, "Error", "Unable to refresh player list: " + e.getMessage());
//        }
//    }
//
//    @FXML
//    public void markReady() {
//        try {
//            boolean success = client.markReady(roomId);
//            if (success) {
//                showAlert(Alert.AlertType.INFORMATION, "Information", "You are now marked as ready!");
//                updateRoomStatus();
//            } else {
//                showAlert(Alert.AlertType.ERROR, "Error", "Failed to mark ready!");
//            }
//        } catch (Exception e) {
//            showAlert(Alert.AlertType.ERROR, "Error", "Unable to mark ready: " + e.getMessage());
//        }
//    }
//
//    @FXML
//    public void startGame() {
//        try {
//            boolean success = client.startGame(roomId);
//            if (success) {
//                showAlert(Alert.AlertType.INFORMATION, "Information", "Game has started!");
//                Director.getInstance().toGameScreen();
//            } else {
//                showAlert(Alert.AlertType.ERROR, "Error", "Unable to start the game!");
//            }
//        } catch (Exception e) {
//            showAlert(Alert.AlertType.ERROR, "Error", "Unable to start the game: " + e.getMessage());
//        }
//    }

    private void updateRoomStatus() {
        // Update room status and player readiness
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
