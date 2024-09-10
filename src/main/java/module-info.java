module com.jr.tankbattle {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires javafx.graphics;
    requires com.google.gson;

    opens com.jr.tankbattle to javafx.fxml, com.google.gson;
    exports com.jr.tankbattle;

    opens com.jr.tankbattle.controller to javafx.fxml;
    exports com.jr.tankbattle.controller;

    opens com.jr.tankbattle.entity to com.google.gson, javafx.fxml;
    exports com.jr.tankbattle.entity;

    opens com.jr.tankbattle.util to javafx.fxml;
    exports com.jr.tankbattle.util;

    opens com.jr.tankbattle.scene to javafx.fxml;
    exports com.jr.tankbattle.scene;

    opens com.jr.tankbattle.client to javafx.fxml;
    exports com.jr.tankbattle.client;
}
