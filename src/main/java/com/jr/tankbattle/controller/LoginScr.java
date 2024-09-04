package com.jr.tankbattle.controller;

import com.jr.tankbattle.Director;
import com.jr.tankbattle.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import java.io.IOException;

public class LoginScr {
    String uid;
    String pwd;

    @FXML
    public void LLogin() throws IOException {
        System.out.println("llogin");
        Director.getInstance().toHomePage();
    }
    @FXML
    public void Back() throws IOException {
        System.out.println("back");
        Director.getInstance().init(Director.getInstance().getStage());
    }
}
