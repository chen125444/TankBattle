package com.jr.tankbattle.controller;

import com.jr.tankbattle.Director;
import javafx.fxml.FXML;

public class Account {
    public void UidChg(){

    }
    public void PwdChg(){

    }

    @FXML
    public void Back(){
        System.out.println("back");
        Director.getInstance().toSetting();
    }
}
