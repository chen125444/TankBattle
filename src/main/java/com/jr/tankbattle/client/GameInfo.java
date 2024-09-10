package com.jr.tankbattle.client;

import java.util.List;

public class GameInfo {

    public List<TankData> tanks;

    public List<TankData> getTanks() {
        return tanks;
    }

    public void setTanks(List<TankData> tanks) {
        this.tanks = tanks;
    }

}
