package Java.presentation.controllers;

import Java.domain.services.ShowManager;
import Java.interfaces.IShow;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;

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
        IShow show = ShowManager.getInstance().addShow(
                showTitle.getText(),
                showDescription.getText()
        );
        //AddCreditController.getInstance().choiceBoxShow.setValue(show);
        AddCreditController.getInstance().disposeCreateShow();
    }

}