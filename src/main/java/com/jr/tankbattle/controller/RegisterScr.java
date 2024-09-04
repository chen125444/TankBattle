package com.jr.tankbattle.controller;

import com.jr.tankbattle.Director;
import javafx.fxml.FXML;

import java.io.IOException;

public class RegisterScr {
    String uid;
    String pwd;

    @FXML
    public void RRegister() throws IOException {
        System.out.println("rregister");
        Director.getInstance().toHomePage();
    }
    @FXML
    public void Back() throws IOException {
        System.out.println("back");
        Director.getInstance().init(Director.getInstance().getStage());
    }
}
