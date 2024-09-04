package com.jr.tankbattle.controller;

import com.jr.tankbattle.Director;
import com.jr.tankbattle.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import java.io.IOException;

public class LoginScr {
    String uid;
    String pwd;

    public void LLogin() {
        System.out.println("llogin");
    }
    public void Back() throws IOException {
        Director.getInstance().toStartScr();
        System.out.println("back");
    }

}
