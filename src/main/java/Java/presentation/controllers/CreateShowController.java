package Java.presentation.controllers;

import Java.CreditSystemController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CreateShowController {
    @FXML
    protected TextField showTitle;
    @FXML
    protected TextArea showDescription;

    private static CreateShowController instance = new CreateShowController();

    private CreateShowController(){

    }

    public static CreateShowController getInstance(){
        return instance;
    }

    @FXML
    protected void handleAddShow(ActionEvent Event) throws IOException {
        CreditSystemController.getInstance().addShow(
                showTitle.getText(),
                showDescription.getText()
        );
        AddCreditController.getInstance().disposeCreateShow();
    }

}