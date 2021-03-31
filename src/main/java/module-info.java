module Java {
    requires javafx.controls;
    requires javafx.fxml;
    opens Java to javafx.fxml;
    exports Java;
}