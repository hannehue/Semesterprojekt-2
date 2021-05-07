
module Java {
    requires javafx.controls;
    requires javafx.fxml;
    exports Java.Presentation.controllers;
    opens Java.Presentation.controllers to javafx.fxml;
    exports Java.Presentation;
    opens Java.Presentation to javafx.fxml;
    exports Java.Data;
    opens Java.Data to javafx.fxml;
}