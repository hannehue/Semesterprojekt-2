package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

public class GUIController implements Initializable {

    @FXML
    protected void handleLogin(ActionEvent Event) throws IOException {
        Main.setRoot("Login");
    }
    @FXML
    protected  void handleLoggingIn(ActionEvent Event) throws IOException{
        //Tilføj kode der faktisk logger ind på en konto
        Main.setRoot("LoggedIn");
    }
    @FXML
    protected  void handleLogout(ActionEvent Event) throws IOException{
        //Tilføj kode der faktisk logger ud fra en konto
        Main.setRoot("GUI");
    }
    @FXML
    protected  void handleMyProfile(ActionEvent Event) throws IOException{
        //Tilføj kode der faktisk logger ud fra en konto
        Main.setRoot("Profile");
    }

    @FXML
    protected void ToFrontpage(MouseEvent Event) throws IOException {
        Main.setRoot("GUI");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


    }
}
