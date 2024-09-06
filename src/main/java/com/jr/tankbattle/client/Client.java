package com.jr.tankbattle.client;

import com.google.gson.Gson;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Client {
    private static final String SERVER_ADDRESS = "47.121.217.200"; // 替换为服务器的 IP 地址
    private static final int SERVER_PORT = 9999; // 服务器的端口号
    private static final Gson gson = new Gson();

    // 注册请求
    public boolean register(String username, String password) throws Exception {
        Message message = new Message("register", username, password);
        return sendMessage(message);
    }

    // 登录请求
    public boolean login(String username, String password) throws Exception {
        Message message = new Message("login", username, password);
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
}
