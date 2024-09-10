package com.jr.tankbattle.scene;

import com.jr.tankbattle.client.Client;
import com.jr.tankbattle.Director;
import com.jr.tankbattle.controller.Account;
import com.jr.tankbattle.entity.Tank3;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.util.*;
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

    private ScheduledExecutorService autoStartScheduler;
    private boolean isGameStarting = false; // Prevent multiple starts

    //玩家都准备好了自动开始游戏
    private void startAutoStartTimer() {
        if (autoStartScheduler != null && !autoStartScheduler.isShutdown()) {
            autoStartScheduler.shutdownNow();
        }
        autoStartScheduler = Executors.newScheduledThreadPool(1);
        autoStartScheduler.schedule(() -> {
            Platform.runLater(() -> {
                if (isGameStarting) {
                    startGame();
                }
            });
        }, 3, TimeUnit.SECONDS); // Delay of 3 seconds before starting the game
    }

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
            boolean success = client.markReady(Account.uid, roomID);
            if (success) {
                showAlert(Alert.AlertType.INFORMATION, "Information", "You are now marked as ready!");
                // Log successful mark ready
                System.out.println("Player marked as ready successfully.");
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to mark ready!");
                // Log failure
                System.err.println("Failed to mark ready for player " + Account.uid);
            }
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Unable to mark ready: " + e.getMessage());
            // Log exception
            e.printStackTrace();
        }
    }


    @FXML
    public void startGame() {
        try {
            boolean success = client.startGame(roomID);
            if (success) {
                List<String> playerList = client.getRoomPlayerList(roomID);
                onClose();
                showAlert(Alert.AlertType.INFORMATION, "Information", "Game has started!");
                Director.getInstance().toOnlineGameScene(playerList);
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Unable to start the game!");
            }
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Unable to start the game: " + e.getMessage());
        e.printStackTrace();
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

                if (allReady && !isGameStarting) {
                    isGameStarting = true;
                    startAutoStartTimer(); // Start the auto start timer
                } else if (!allReady) {
                    isGameStarting = false; // Reset the flag if not all players are ready
                    if (autoStartScheduler != null && !autoStartScheduler.isShutdown()) {
                        autoStartScheduler.shutdownNow(); // Cancel the timer if not all players are ready
                    }
                }
            } catch (Exception e) {
                showAlert(Alert.AlertType.ERROR, "Error", "Unable to update room status: " + e.getMessage());
            }
        });
    }


    private Map<String, Tank3> assignTanksToPlayers(List<String> playerList) {
        Map<String, Tank3> playerTanks = new HashMap<>();
        List<Tank3.TankType> tankTypes = new ArrayList<>(List.of(Tank3.TankType.values()));
        Collections.shuffle(tankTypes); // 打乱坦克类型的顺序

        for (int i = 0; i < playerList.size(); i++) {
            String player = playerList.get(i);
            Tank3.TankType tankType = tankTypes.get(i); // 获取打乱后的坦克类型
            Tank3 tank = new Tank3();
            tank.setTankType(tankType);
            tank.setPlayerId(player);
            playerTanks.put(player, tank);
        }
        return playerTanks;
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
        if (autoStartScheduler != null && !autoStartScheduler.isShutdown()) {
            autoStartScheduler.shutdownNow(); // 停止自动开始的调度程序
        }
    }
}
