module com.jr.tankbattle {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.jr.tankbattle to javafx.fxml;
    exports com.jr.tankbattle;

    exports com.jr.tankbattle.controller;
    opens com.jr.tankbattle.controller to javafx.fxml;
}