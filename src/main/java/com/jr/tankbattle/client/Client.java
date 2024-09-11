package com.jr.tankbattle.client;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.jr.tankbattle.controller.Account;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Client {
    private static final String SERVER_ADDRESS = "47.121.217.200"; // 替换为服务器的 IP 地址
    private static final int SERVER_PORT = 9999; // 服务器的端口号
    private static final Gson gson = new Gson();

    // 注册请求
    public boolean register(String username, String password) throws Exception {
        Message message = new Message();
        message.setRegisterRequest(username, password);
        return sendMessage(message);
    }

    // 登录请求
    public boolean login(String username, String password) throws Exception {
        Message message = new Message();
        message.setLoginRequest(username, password);
        return sendMessage(message);
    }

    //登出
    public boolean logout(String username) throws Exception {
        Message message = new Message();
        message.setLogoutRequest(username);
        return sendMessage(message);
    }

    // 获取在线玩家列表
    public List<String> getOnlinePlayers() throws Exception {
        Message message = new Message();
        message.setRoomRequest("getOnlinePlayers", null); // 假设获取玩家列表的请求类型为 "getPlayers"
        String response = sendMessageAndGetResponse(message);
        Message responseMessage = gson.fromJson(response, Message.class);

        List<String> players = new ArrayList<>();
        if ("success".equals(responseMessage.status)) {
            // 解析玩家列表
            String[] playerArray = responseMessage.message.split(",");
            for (String player : playerArray) {
                players.add(player.trim());
            }
        }
        return players;
    }

    // 获取房间列表
    public List<String> getOnlineRooms() throws Exception {
        Message message = new Message();
        message.setRoomRequest("getOnlineRooms", null);  // 请求房间列表
        String response = sendMessageAndGetResponse(message);
        Message responseMessage = gson.fromJson(response, Message.class);

        List<String> rooms = new ArrayList<>();
        if ("success".equals(responseMessage.status)) {
            // 解析房间列表
            if (responseMessage.message != null && !responseMessage.message.trim().isEmpty()) {
                String[] roomArray = responseMessage.message.split(",");
                for (String room : roomArray) {
                    if (!room.trim().isEmpty()) {
                        rooms.add(room.trim());
                    }
                }
            }
        } else {
            throw new Exception("Error retrieving room list: " + responseMessage.message);
        }
        return rooms;
    }


    //获取房间玩家人数
    public int getRoomPlayerCount(String roomId) throws Exception {
        // 创建请求消息
        Message request = new Message();
        request.type = "getRoomPlayerCount";
        request.roomId = roomId;

        // 发送请求并获取响应
        String responseJson = sendMessageAndGetResponse(request); // 假设这个方法发送请求并获取响应
        Message response = gson.fromJson(responseJson, Message.class);

        if ("success".equals(response.status)) {
            return Integer.parseInt(response.message); // 将返回的字符串解析为 int
        } else {
//            throw new Exception("无法获取房间人数: " + response.message);
            return 0;
        }
    }


    // 创建房间
    public boolean createRoom(String roomId) throws Exception {
        Message message = new Message();
        message.username = Account.uid;
        message.setRoomRequest("createRoom", roomId);
        return sendMessage(message);
    }

    // 加入房间
    public boolean joinRoom(String roomId) throws Exception {
        Message message = new Message();
        message.username = Account.uid;
        message.setRoomRequest("joinRoom", roomId);
        return sendMessage(message);
    }

    // 获取房间内玩家列表
    public List<String> getRoomPlayerList(String roomId) throws Exception {
        Message message = new Message();
        message.setRoomRequest("getRoomPlayerList", roomId);  // Request type for room player list
        String response = sendMessageAndGetResponse(message);
        Message responseMessage = gson.fromJson(response, Message.class);

        List<String> players = new ArrayList<>();
        if ("success".equals(responseMessage.status)) {
            // Parse players list
            String[] playerArray = responseMessage.message.split(",");
            for (String player : playerArray) {
                players.add(player.trim());
            }
        } else {
            throw new Exception(responseMessage.message);
        }
        return players;
    }

    // 获取玩家准备状态
    public Map<String, Boolean> getPlayerReadyStatus(String roomId) throws Exception {
        Message message = new Message();
        message.setRoomRequest("getPlayerReadyStatus", roomId);  // Request type for player ready status
        String response = sendMessageAndGetResponse(message);
        Message responseMessage = gson.fromJson(response, Message.class);

        Map<String, Boolean> playerStatus = new HashMap<>();
        if ("success".equals(responseMessage.status)) {
            // Parse player status map
            JsonObject jsonObject = gson.fromJson(responseMessage.message, JsonObject.class);
            for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
                playerStatus.put(entry.getKey(), entry.getValue().getAsBoolean());
            }
        } else {
            throw new Exception(responseMessage.message);
        }
        return playerStatus;
    }

    // 标记玩家为准备状态
    public boolean markReady(String username, String roomId) throws Exception {
        Message message = new Message();
        message.setReadyStatusRequest(username, roomId, true);  // Request type for marking ready

        String response = sendMessageAndGetResponse(message);
        Message responseMessage = gson.fromJson(response, Message.class);

        return "success".equals(responseMessage.status);
    }

    // 开始游戏
    public boolean startGame(String roomId) throws Exception {
        Message message = new Message();
        message.setRoomRequest("startGame", roomId);  // Request type for starting game
        String response = sendMessageAndGetResponse(message);
        Message responseMessage = gson.fromJson(response, Message.class);

        return "success".equals(responseMessage.status);
    }

    // 离开房间
    public boolean leaveRoom(String roomId) throws Exception {
        Message message = new Message();
        message.username = Account.uid;
        message.setRoomRequest("leaveRoom", roomId);  // Request type for leaving room
        String response = sendMessageAndGetResponse(message);
        Message responseMessage = gson.fromJson(response, Message.class);

        return "success".equals(responseMessage.status);
    }

    // 发送消息并接收响应
    public boolean sendMessage(Message message) throws Exception {
        DatagramSocket socket = new DatagramSocket();
        InetAddress serverAddress = InetAddress.getByName(SERVER_ADDRESS);
        String jsonMessage = gson.toJson(message);
        byte[] sendData = jsonMessage.getBytes();
        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverAddress, SERVER_PORT);
        socket.send(sendPacket);

        // 接收响应
        byte[] receiveData = new byte[1024];
        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
        socket.receive(receivePacket);
        String jsonResponse = new String(receivePacket.getData(), 0, receivePacket.getLength());

        // 处理响应
        Message response = gson.fromJson(jsonResponse, Message.class);
        socket.close();

        // 根据响应状态返回结果
        return "success".equals(response.status);
    }

    // 发送消息并获取响应
    private String sendMessageAndGetResponse(Message message) throws Exception {
        DatagramSocket socket = new DatagramSocket();
        InetAddress serverAddress = InetAddress.getByName(SERVER_ADDRESS);
        String jsonMessage = gson.toJson(message);
        byte[] sendData = jsonMessage.getBytes();
        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverAddress, SERVER_PORT);
        socket.send(sendPacket);

        // 接收响应
        byte[] receiveData = new byte[1024];
        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
        socket.receive(receivePacket);
        String jsonResponse = new String(receivePacket.getData(), 0, receivePacket.getLength());

        socket.close();
        return jsonResponse;
    }

    /*---------------------------------------------*/
    public boolean sendPlayerTanksData(String data) throws Exception {
        // 序列化 playerTanks 为字符串
        String playerTanksData = data;

        // 创建消息对象
        Message message = new Message();
        message.type = "playerTanksData";
        message.message = playerTanksData;

        // 发送消息到服务器
        return sendMessage(message);
    }
    public String getPlayerTanksData() throws Exception {
        Message message = new Message();

        message.type = "getPlayerTanksData";

        String response = sendMessageAndGetResponse(message);
        Message responseMessage = gson.fromJson(response, Message.class);
        return responseMessage.message;
    }

    public boolean sendBulletsData(String data) throws Exception {
        String bulletsData = data;

        // 创建消息对象
        Message message = new Message();
        message.type = "bulletsData";
        message.message = bulletsData;

        // 发送消息到服务器
        return sendMessage(message);
    }

    public String getBulletsData() throws Exception {
        Message message = new Message();
        message.username = Account.uid;
        message.type = "getBulletsData";

        // 发送消息并获取响应
        String response = sendMessageAndGetResponse(message);

        // 将响应字符串转换为 Message 对象
        Message responseMessage = gson.fromJson(response, Message.class);

        // 检查 responseMessage 是否为 null
        if (responseMessage == null || responseMessage.message == null) {
            return ""; // 如果 responseMessage 或 message 为 null，则返回空字符串
        }

        return responseMessage.message;
    }


    /*---------------------------------------------*/

}
