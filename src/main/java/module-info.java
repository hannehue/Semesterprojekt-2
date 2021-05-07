
module Java {
    requires javafx.controls;
    requires javafx.fxml;
    exports Java.Application.controllers;
    opens Java.Application.controllers to javafx.fxml;
    exports Java.Application;
    opens Java.Application to javafx.fxml;
    exports Java.Data;
    opens Java.Data to javafx.fxml;
}