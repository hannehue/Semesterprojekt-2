package Java.presentation.controllers;

import Java.domain.ApplicationManager;
import Java.domain.UserType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;

/* ------------------------------------------------------------------------------------------------------------------
Denne Controller bruges til at logge ind på en konto
------------------------------------------------------------------------------------------------------------------ */


public class LoginController {

    private static LoginController instance = new LoginController();

    private LoginController() {
    }

    public static LoginController getInstance() {
        return instance;
    }



    //---- Login Typer
    @FXML
    protected  void handleLoggingIn(ActionEvent Event) throws IOException {
        loginManager(UserType.PERSON);
    }
    @FXML
    protected  void handleLoggingInProducer(ActionEvent Event) throws IOException{
        loginManager(UserType.PRODUCER);
    }
    @FXML
    protected  void handleLoggingInModerator(ActionEvent Event) throws IOException{
        loginManager(UserType.MODERATOR);
    }
    @FXML
    protected  void handleLoggingInAdmin(ActionEvent Event) throws IOException{
        loginManager(UserType.ADMIN);
    }

    private void loginManager(UserType userType) throws IOException{
        ApplicationManager.getInstance().setUserType(userType);
        MenuController.getInstance().clearContentPane();
    }
}
