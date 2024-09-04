package com.jr.tankbattle.controller;

import com.jr.tankbattle.Director;
import javafx.fxml.FXML;

import java.io.IOException;

public class Setting {
    @FXML
    public void Help(){
        System.out.println("help");
    }
    @FXML
    public void Exit(){
        System.out.println("exit");
        System.exit(0);
    }
    @FXML
    public void Back() throws IOException {
        System.out.println("back");
        Director.getInstance().toHomePage();
    }
    @FXML
    public void Volume(){
        System.out.println("volume");
    }
    @FXML
    public void Account(){
        System.out.println("account");
        Director.getInstance().toAccount();
    }
}
