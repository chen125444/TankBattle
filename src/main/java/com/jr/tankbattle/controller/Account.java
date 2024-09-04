package com.jr.tankbattle.controller;

import com.jr.tankbattle.Director;

public class Account {
    public void UidChg(){

    }
    public void PwdChg(){

    }
    public void Back(){
        System.out.println("back");
        Director.getInstance().toSetting();
    }
}
