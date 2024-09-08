package com.jr.tankbattle.controller;
import com.jr.tankbattle.Director;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

import java.io.IOException;

public class StartScr {
    @FXML
    public void Login() throws IOException {
        System.out.println("login");
        Director.getInstance().toLoginScr();
    }
    @FXML
    public void Register() throws IOException {
        System.out.println("register");
        Director.getInstance().toRegisterScr();
    }
    @FXML
    public void Exit(){
        System.out.println("exit");
        System.exit(0);
    }
}
