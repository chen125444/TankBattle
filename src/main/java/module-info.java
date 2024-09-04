module com.jr.tankbattle {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;

    exports com.jr.tankbattle;
    opens com.jr.tankbattle to javafx.fxml;

    exports com.jr.tankbattle.controller;
    opens com.jr.tankbattle.controller to javafx.fxml;

    exports com.jr.tankbattle.entity;
    opens com.jr.tankbattle.entity to javafx.fxml;
}