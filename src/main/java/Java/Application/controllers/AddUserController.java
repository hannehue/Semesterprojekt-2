package Java.Application.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;

public class AddUserController {

    private static AddUserController instance = new AddUserController();

    private AddUserController() {

    }

    public static AddUserController getInstance(){
        return instance;
    }

    @FXML
    protected void handleAddUserButton(ActionEvent Event) throws IOException{
    }
}
