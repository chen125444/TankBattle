package com.jr.tankbattle.util;

import com.jr.tankbattle.entity.AiTank;
import com.jr.tankbattle.entity.Rock;
import com.jr.tankbattle.entity.Tank;
import com.jr.tankbattle.entity.Tree;
import com.jr.tankbattle.scene.GameScene;
import javafx.scene.Scene;

import java.util.HashMap;
import java.util.Map;

public class MapData {
    public Map<Integer,TankMap> mapData;
    private TankMap map1,map2;
    public MapData(GameScene gameScene) {

        mapData = new HashMap<>(); // 初始化 mapData
        map1 = new TankMap();
        map2 = new TankMap();
        setMap1(map1,gameScene);
        //setMap2(map2);
        mapData.put(1, map1);
        mapData.put(2, map2);
    }
    public void setMap1(TankMap map1,GameScene gameScene) {
        AiTank aiTank1 = new AiTank(40,80,40,40,2, gameScene);
        map1.aitanks.add(aiTank1);
        AiTank aiTank2 = new AiTank(320,640,40,40,2, gameScene);
        map1.aitanks.add(aiTank2);
        AiTank aiTank3 = new AiTank(600,120,40,40,2, gameScene);
        map1.aitanks.add(aiTank3);
        AiTank aiTank4 = new AiTank(280,520,40,40,2, gameScene);
        map1.aitanks.add(aiTank4);
        AiTank aiTank5 = new AiTank(720,40,40,40,2, gameScene);
        map1.aitanks.add(aiTank5);
        Tree tree1 = new Tree(80,120,40,40,gameScene);
        map1.trees.add(tree1);
        Tree tree2 = new Tree(80,160,40,40,gameScene);
        map1.trees.add(tree2);
        Tree tree3 = new Tree(80,200,40,40,gameScene);
        map1.trees.add(tree3);
        Tree tree4 = new Tree(80,320,40,40,gameScene);
        map1.trees.add(tree4);
        Tree tree5 = new Tree(80,440,40,40,gameScene);
        map1.trees.add(tree5);
        Tree tree6 = new Tree(120,480,40,40,gameScene);
        map1.trees.add(tree6);
        Tree tree7 = new Tree(160,600,40,40,gameScene);
        map1.trees.add(tree7);
        Tree tree8 = new Tree(240,560,40,40,gameScene);
        map1.trees.add(tree8);
        Tree tree9 = new Tree(320,600,40,40,gameScene);
        map1.trees.add(tree9);
        Tree tree10 = new Tree(400,720,40,40,gameScene);
        map1.trees.add(tree10);
        Tree tree11 = new Tree(280,320,40,40,gameScene);
        map1.trees.add(tree11);
        Tree tree12 = new Tree(800,120,40,40,gameScene);
        map1.trees.add(tree12);
        Tree tree13 = new Tree(80,720,40,40,gameScene);
        map1.trees.add(tree13);
        Tree tree14 = new Tree(800,320,40,40,gameScene);
        map1.trees.add(tree14);
        Tree tree15 = new Tree(480,520,40,40,gameScene);
        map1.trees.add(tree15);
        Tree tree16 = new Tree(480,120,40,40,gameScene);
        map1.trees.add(tree16);
        Tree tree17 = new Tree(840,320,40,40,gameScene);
        map1.trees.add(tree17);
        Tree tree18 = new Tree(240,120,40,40,gameScene);
        map1.trees.add(tree18);
        Tree tree19 = new Tree(280,120,40,40,gameScene);
        map1.trees.add(tree19);
        Tree tree20 = new Tree(280,360,40,40,gameScene);
        map1.trees.add(tree20);
    }

}
