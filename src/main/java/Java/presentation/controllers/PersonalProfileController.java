package Java.presentation.controllers;

import Java.CreditSystemController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PersonalProfileController {

    private static PersonalProfileController instance = new PersonalProfileController();

    private PersonalProfileController(){
    }

    public static PersonalProfileController getInstance(){
        return instance;
    }

    //Credit Person
    @FXML
    protected  void handleEditProfile(ActionEvent Event) throws IOException {
        //Tilføj kode der henter brugerens informationer og sætter dem i textfields i stedet for labels
        CreditSystemController.getInstance().setRoot("CreditPersonProfile-edit", PersonalProfileController.getInstance());
    }
}
