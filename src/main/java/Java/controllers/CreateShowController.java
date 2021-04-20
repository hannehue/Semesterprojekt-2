package Java.controllers;

import Java.CreditSystemController;
import Java.Season;
import Java.Show;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

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