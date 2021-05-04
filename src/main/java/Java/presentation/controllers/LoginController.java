
package Java.presentation.controllers;

import Java.CreditSystemController;
import Java.UserType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/* ------------------------------------------------------------------------------------------------------------------
Denne Controller bruges til at logge ind på en konto
------------------------------------------------------------------------------------------------------------------ */


public class LoginController implements Initializable {

    @Override
    public void initialize(URL location, ResourceBundle resources) {

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
        CreditSystemController.getInstance().setUserType(userType);
        CreditSystemController.getInstance().setRoot("Menu");
    }
}
