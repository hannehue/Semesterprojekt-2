package Java.Application.controllers;

import Java.Application.CreditSystemController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CreateSeasonController implements Initializable {

    @FXML
    protected TextArea seasonDescription;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    protected void handleAddSeason(ActionEvent Event) throws IOException {
        System.out.println("trying to add season with showname: " + AddCreditController.getShowName());
        CreditSystemController.addSeason(seasonDescription.getText(), AddCreditController.getShowName());
        AddCreditController.disposeCreateSeason();
    }
}
