module Java {
    requires javafx.controls;
    requires javafx.fxml;
    opens Java to javafx.fxml;
    exports Java;
    exports Java.presentation.controllers;
    opens Java.presentation.controllers to javafx.fxml;
    exports Java.persistencelayer;
    opens Java.persistencelayer to javafx.fxml;
}