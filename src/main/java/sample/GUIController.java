package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GUIController implements Initializable {

    @FXML
    protected void handleLogin(ActionEvent Event) throws IOException {
        //redirect til Login side
        Main.setRoot("Login");
    }

    //---- Login Typer
    @FXML
    protected  void handleLoggingIn(ActionEvent Event) throws IOException{
        //Tilføj kode der faktisk logger ind på en konto
        Main.setRoot("CreditPerson");
    }
    @FXML
    protected  void handleLoggingInProducer(ActionEvent Event) throws IOException{
        //Tilføj kode der faktisk logger ind på en konto
        Main.setRoot("Producer");
    }
    @FXML
    protected  void handleLoggingInModerator(ActionEvent Event) throws IOException{
        //Tilføj kode der faktisk logger ind på en konto
        Main.setRoot("Moderator");
    }
    @FXML
    protected  void handleLoggingInAdmin(ActionEvent Event) throws IOException{
        //Tilføj kode der faktisk logger ind på en konto
        Main.setRoot("Admin");
    }
    //-----

    // General features
    @FXML
    protected  void handleLogout(ActionEvent Event) throws IOException{
        //Tilføj kode der faktisk logger ud fra en konto
        Main.setRoot("GUI");
    }
    @FXML
    protected void ToFrontpage(MouseEvent Event) throws IOException {
        //denne bruges på billedet af TV 2
        //hver gang billedet klikkes ryges der til forsiden
        Main.setRoot("GUI");
    }

    //Credit Person
    @FXML
    protected  void handleMyProfile(ActionEvent Event) throws IOException{
        Main.setRoot("CreditPersonProfile");
    }
    @FXML
    protected  void handleEditProfile(ActionEvent Event) throws IOException{
        //Tilføj kode der henter brugerens informationer og sætter dem i textfields i stedet for labels
        Main.setRoot("CreditPersonProfile-edit");
    }
    @FXML
    protected  void handleSaveProfile(ActionEvent Event) throws IOException{
        //Tilføj kode der tager det information brugeren har indtastet i textfields og overskriv brugerens gamle informationer i json-fil
        Main.setRoot("CreditPersonProfile");
    }

    //Producer
    @FXML
    protected  void handleMyProfileProducer(ActionEvent Event) throws IOException{
        Main.setRoot("ProducerProfile");
    }
    @FXML
    protected  void handleEditProfileProducer(ActionEvent Event) throws IOException{
        Main.setRoot("ProducerProfile-edit");
    }
    @FXML
    protected  void handleAddCreditProducer(ActionEvent Event) throws IOException{
        Main.setRoot("ProducerAddCredit");
    }
    @FXML
    protected  void handleSaveProfileProducer(ActionEvent Event) throws IOException{
        Main.setRoot("ProducerProfile");
    }

    @FXML
    protected void handleAddCreditButton(ActionEvent Event) throws IOException {
        Scene scene = new Scene(FXMLLoader.load(Main.class.getClassLoader().getResource("AddPersonToCredit.fxml")));
                Stage stage = new Stage();
                stage.setTitle("Tilføj person");
                stage.setScene(scene);
                stage.show();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


    }
}
