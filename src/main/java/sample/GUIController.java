package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GUIController implements Initializable {

    @FXML
    protected void handleLogin(ActionEvent Event) throws IOException {
        //redirect til Login side
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
        //Går til profilen af den person der er logget ind
        Main.setRoot("Profile");
    }
    @FXML
    protected  void handleEditProfile(ActionEvent Event) throws IOException{
        //Tilføj kode der henter brugerens informationer og sætter dem i textfields i stedet for labels
        Main.setRoot("Profile-edit");
    }
    @FXML
    protected  void handleSaveProfile(ActionEvent Event) throws IOException{
        //Tilføj kode der tager det information brugeren har indtastet i textfields og overskriv brugerens gamle informationer i json-fil
        Main.setRoot("Profile");
    }

    @FXML
    protected void ToFrontpage(MouseEvent Event) throws IOException {
        //denne bruges på billedet af TV 2
        //hver gang billedet klikkes ryges der til forsiden
        Main.setRoot("GUI");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


    }
}
