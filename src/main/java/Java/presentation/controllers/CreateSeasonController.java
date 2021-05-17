package Java.presentation.controllers;

import Java.domain.ApplicationManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

import java.io.IOException;

public class CreateSeasonController {

    @FXML
    protected TextArea seasonDescription;
    private static CreateSeasonController instance = new CreateSeasonController();

    private CreateSeasonController() {
    }

    public static CreateSeasonController getInstance(){
        return instance;
    }

    @FXML
    protected void handleAddSeason(ActionEvent Event) throws IOException {
        AddCreditController.getInstance().addSeason(seasonDescription.getText());
        AddCreditController.getInstance().disposeCreateSeason();
    }
}
