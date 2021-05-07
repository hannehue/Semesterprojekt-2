
module Java {
    requires javafx.controls;
    requires javafx.fxml;
    exports Java.presentation.controllers;
    opens Java.presentation.controllers to javafx.fxml;
    exports Java.presentation;
    opens Java.presentation to javafx.fxml;
    exports Java.data;
    opens Java.data to javafx.fxml;
    exports Java.interfaces;
    opens Java.interfaces to javafx.fxml;
    exports Java.domain;
    opens Java.domain to javafx.fxml;
}