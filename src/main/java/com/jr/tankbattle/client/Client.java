package com.jr.tankbattle.client;

import com.google.gson.Gson;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

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
        message.setRoomRequest("getPlayers", null); // 假设获取玩家列表的请求类型为 "getPlayers"
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

    // 创建房间
    public boolean createRoom(String roomId) throws Exception {
        Message message = new Message();
        message.setRoomRequest("createRoom", roomId);
        return sendMessage(message);
    }

    // 加入房间
    public boolean joinRoom(String roomId) throws Exception {
        Message message = new Message();
        message.setRoomRequest("joinRoom", roomId);
        return sendMessage(message);
    }

    // 设置玩家准备状态
    public boolean setReadyStatus(String roomId, boolean isReady) throws Exception {
        Message message = new Message();
        message.setReadyStatusRequest(roomId, isReady);
        return sendMessage(message);
    }

    // 启动游戏
    public boolean startGame() throws Exception {
        Message message = new Message();
        message.setRoomRequest("startGame", null); // 启动游戏的请求类型假设为 "startGame"
        return sendMessage(message);
    }

    // 发送消息并接收响应
    private boolean sendMessage(Message message) throws Exception {
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
}
