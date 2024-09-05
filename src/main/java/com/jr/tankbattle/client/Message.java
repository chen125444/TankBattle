package com.jr.tankbattle.client;

public class Message {
    public String type; // 消息类型，如 "register", "login", "updatePosition"
    public String username;
    public String password;
    public String status;
    public String message;
    public double x; // 用于存储位置 X 坐标
    public double y; // 用于存储位置 Y 坐标
    public String tankId; // 用于存储坦克 ID

    // 默认构造函数
    public Message() {}

    // 构造函数用于请求
    public Message(String type, String username, String password) {
        this.type = type;
        this.username = username;
        this.password = password;
    }

    // 构造函数用于响应
    public Message(String status, String message) {
        this.status = status;
        this.message = message;
    }

    // 构造函数用于更新位置
    public Message(String type, String tankId, double x, double y) {
        this.type = type;
        this.tankId = tankId;
        this.x = x;
        this.y = y;
    }
}

