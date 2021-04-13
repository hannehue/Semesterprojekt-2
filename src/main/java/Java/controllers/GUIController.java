package Java.controllers;

import Java.Credit;
import Java.CreditSystemController;
import Java.Person;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GUIController implements Initializable {
    @FXML
    protected TextField personName;
    @FXML
    protected TextField personEmail;
    @FXML
    protected TextField personPhone;
    @FXML
    protected AnchorPane personToApprove;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
    // General features
    @FXML
    protected  void handleLogout(ActionEvent Event) throws IOException{
        //Tilføj kode der faktisk logger ud fra en konto
        CreditSystemController.setRoot("Dashboard");
    }

    @FXML
    protected void handleSendPersonButton(ActionEvent Event) throws IOException{
        CreditSystemController.addPerson(
                personName.getText(),
                null,
                personPhone.getText(),
                personEmail.getText());
    }
    @FXML
    protected void handleAddCreditButton(ActionEvent Event) throws IOException {
        // Opens new window for adding person
        Scene scene = new Scene(FXMLLoader.load(CreditSystemController.class.getClassLoader().getResource("AddPersonToCredit.fxml")));
        Stage stage = new Stage();
        stage.setTitle("Tilføj person");
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    protected  void handleSendProgramButton(ActionEvent Event) throws IOException{
        CreditSystemController.setRoot("Dashboard");
    } // Tilføj kode der sende til fil

    //Credit Person
    @FXML
    protected  void handleEditProfile(ActionEvent Event) throws IOException{
        //Tilføj kode der henter brugerens informationer og sætter dem i textfields i stedet for labels
        CreditSystemController.setRoot("CreditPersonProfile-edit");
    }

    //Producer


    // Moderator
    @FXML
    protected void handleApproveCreditsModerator(ActionEvent Event) throws IOException{
        CreditSystemController.setRoot("ModeratorApproveCredits");
    }
    @FXML
    protected  void handleAddCreditsModerator(ActionEvent Event) throws IOException{
        CreditSystemController.setRoot("ModeratorAddCredits");
    }


    //Admin
    @FXML
    protected  void handleAddUserButtonAdmin(ActionEvent Event) throws IOException{
        CreditSystemController.setRoot("Dashboard");
    }

    private void reload() {
        System.out.println("in dashboardController");
    }

}
