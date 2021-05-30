package Java.presentation.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;

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
        MenuController.getInstance().setContentPane("CreditPersonProfile-edit", PersonalProfileController.getInstance());
    }
}
