package Java;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    //---- Login Typer
    @FXML
    protected  void handleLoggingIn(ActionEvent Event) throws IOException {
        //Tilføj kode der faktisk logger ind på en konto
        loginManager(UserType.PERSON);
    }
    @FXML
    protected  void handleLoggingInProducer(ActionEvent Event) throws IOException{
        //Tilføj kode der faktisk logger ind på en konto
        loginManager(UserType.PRODUCER);
    }
    @FXML
    protected  void handleLoggingInModerator(ActionEvent Event) throws IOException{
        //Tilføj kode der faktisk logger ind på en konto
        loginManager(UserType.MODERATOR);
    }
    @FXML
    protected  void handleLoggingInAdmin(ActionEvent Event) throws IOException{
        //Tilføj kode der faktisk logger ind på en konto
        loginManager(UserType.ADMIN);
    }

    protected static void loginManager(UserType userType) throws IOException{
        CreditSystemController.setUserType(userType);
        CreditSystemController.setRoot("Dashboard");
    }


    @FXML
    protected void toFrontpage(MouseEvent Event) throws IOException {
        //denne bruges på billedet af TV 2
        //hver gang billedet klikkes ryges der til forsiden
        CreditSystemController.setRoot("Dashboard");
    }

}
