module Java {
    requires javafx.controls;
    requires javafx.fxml;
    opens Java to javafx.fxml;
    exports Java;
    exports Java.controllers;
    opens Java.controllers to javafx.fxml;
}