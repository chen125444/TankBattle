package com.jr.tankbattle.scene;

import com.jr.tankbattle.Director;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class GameScene {
    @FXML
    private Canvas canvas = new Canvas(720, 720);
    private GraphicsContext graphicsContext = canvas.getGraphicsContext2D();

    //private KeyProcess keyProcess = new KeyProcess();
    //private Refresh refresh = new Refresh();
    //private boolean running = false;

    //private Background background = new Background();

    public GraphicsContext getGraphicsContext() {
        return graphicsContext;
    }

    public void setGraphicsContext(GraphicsContext graphicsContext) {
        this.graphicsContext = graphicsContext;
    }

    public void init(Stage stage) {
        AnchorPane root = new AnchorPane(canvas);
        stage.getScene().setRoot(root);
        //stage.getScene().setOnKeyReleased(keyProcess);
        //stage.getScene().setOnKeyPressed(keyProcess);
        //running = true;
        //self = new Tank(400, 500, Group.green, Direction.stop, Direction.up, this);
        //initSprite();
        //refresh.start();
    }
}
