package Java.presentation.controllers;

import Java.CreditSystemController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CreateSeasonController {

    @FXML
    protected TextArea seasonDescription;
    private static CreateSeasonController instance;

    public CreateSeasonController() {
        if(instance != null) throw new UnsupportedOperationException("More than one instance cannot be creates");
        instance = this;
    }

    public static CreateSeasonController getInstance(){
        return instance;
    }

    @FXML
    protected void handleAddSeason(ActionEvent Event) throws IOException {
        System.out.println("trying to add season with showname: " + AddCreditController.getInstance().getShowName());
        CreditSystemController.getInstance().addSeason(seasonDescription.getText(), AddCreditController.getInstance().getShowName());
        AddCreditController.getInstance().disposeCreateSeason();
    }
}
