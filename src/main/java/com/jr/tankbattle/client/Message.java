package com.jr.tankbattle.client;

public class Message {
    public String type; // 消息类型，如 "register", "login", "createRoom", "joinRoom", "ready", "gameStatus"
    public String username; // 用户名
    public String password; // 密码
    public String status; // 状态信息
    public String message; // 额外的消息
    public String roomId; // 房间 ID
    public boolean isReady; // 玩家是否准备好
    public String tankId; // 用于存储坦克 ID
    public double x; // 用于存储位置 X 坐标
    public double y; // 用于存储位置 Y 坐标

    public Message() {}

    // 设置请求类型和用户名、密码
    public void setRegisterRequest(String username, String password) {
        this.type = "register";
        this.username = username;
        this.password = password;
    }

    public void setLoginRequest(String username, String password) {
        this.type = "login";
        this.username = username;
        this.password = password;
    }

    // 设置注销请求
    public void setLogoutRequest(String username) {
        this.type = "logout";
        this.username = username;
    }

    // 设置房间管理请求（如创建房间或加入房间）
    public void setRoomRequest(String type, String roomId) {
        this.type = type;
        this.roomId = roomId;
    }

    // 设置玩家准备状态请求
    public void setReadyStatusRequest(String roomId, boolean isReady) {
        this.type = "ready";
        this.roomId = roomId;
        this.isReady = isReady;
    }

    // 设置响应消息
    public void setResponse(String status, String message) {
        this.status = status;
        this.message = message;
    }

    // 设置位置更新
    public void setPositionUpdate(String tankId, double x, double y) {
        this.type = "updatePosition";
        this.tankId = tankId;
        this.x = x;
        this.y = y;
    }
}
