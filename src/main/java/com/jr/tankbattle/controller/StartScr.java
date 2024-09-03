package com.jr.tankbattle.controller;

import javafx.fxml.FXML;

public class StartScr {
    @FXML
    public void Login(){
        System.out.println("login");
    }
    @FXML
    public void Register(){
        System.out.println("register");
    }
    @FXML
    public void Exit(){
        System.out.println("exit");
        System.exit(0);
    }
}
