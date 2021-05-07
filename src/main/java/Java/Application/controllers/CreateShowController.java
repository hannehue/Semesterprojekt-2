package Java.controllers;

import Java.Application.CreditSystemController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CreateShowController implements Initializable {
    @FXML
    protected TextField showTitle;
    @FXML
    protected TextArea showDescription;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    @FXML
    protected void handleAddShow(ActionEvent Event) throws IOException {
        CreditSystemController.addShow(
                showTitle.getText(),
                showDescription.getText()
        );
        AddCreditController.disposeCreateShow();
    }
}