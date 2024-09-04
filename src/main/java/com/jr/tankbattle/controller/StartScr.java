package com.jr.tankbattle.controller;
import com.jr.tankbattle.Director;
import javafx.fxml.FXML;

import java.io.IOException;


public class StartScr {
    @FXML
    public void Login() throws IOException {
        System.out.println("login");
        Director.getInstance().toLoginScr();
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
