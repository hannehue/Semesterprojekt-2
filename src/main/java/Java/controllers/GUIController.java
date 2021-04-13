package Java.controllers;

import Java.CreditSystemController;
import Java.Person;
import javafx.animation.TranslateTransition;
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
import javafx.util.Duration;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GUIController implements Initializable {
    @FXML
    protected Pane searchPane;
    @FXML
    protected Pane searchContent;
    @FXML
    protected TextField personName;
    @FXML
    protected TextField personEmail;
    @FXML
    protected TextField personPhone;
    @FXML
    protected TextField searchField;
    @FXML
    protected Label personLabel;
    @FXML
    protected Label showLabel;
    @FXML
    protected Accordion approveAcc;
    @FXML
    protected AnchorPane personToApprove;

    // General features
    @FXML
    protected  void handleLogout(ActionEvent Event) throws IOException{
        //Tilføj kode der faktisk logger ud fra en konto
        CreditSystemController.setRoot("Dashboard");
    }

    @FXML
    protected void handleSendPersonButton(ActionEvent Event) throws IOException{
        CreditSystemController.addPerson(personName.getText(), null, personPhone.getText(), personEmail.getText());
    } // Tilføj kode der sende til fil
    @FXML
    protected void handleAddCreditButton(ActionEvent Event) throws IOException {
        Scene scene = new Scene(FXMLLoader.load(CreditSystemController.class.getClassLoader().getResource("AddPersonToCredit.fxml")));
        Stage stage = new Stage();
        stage.setTitle("Tilføj person");
        stage.setScene(scene);
        stage.show();
    } // Opens new window for adding person
    @FXML
    protected  void handleSendProgramButton(ActionEvent Event) throws IOException{
        CreditSystemController.setRoot("Dashboard");
    } // Tilføj kode der sende til fil


    //Credit Person
    @FXML
    protected  void handleMyProfile(ActionEvent Event) throws IOException{
        CreditSystemController.setRoot("CreditPersonProfile");
    }
    @FXML
    protected  void handleEditProfile(ActionEvent Event) throws IOException{
        //Tilføj kode der henter brugerens informationer og sætter dem i textfields i stedet for labels
        CreditSystemController.setRoot("CreditPersonProfile-edit");
    }
    @FXML
    protected  void handleSaveProfile(ActionEvent Event) throws IOException{
        //Tilføj kode der tager det information brugeren har indtastet i textfields og overskriv brugerens gamle informationer i json-fil
        CreditSystemController.setRoot("CreditPersonProfile");
    }

    //Producer
    @FXML
    protected  void handleMyProfileProducer(ActionEvent Event) throws IOException{
        CreditSystemController.setRoot("ProducerProfile");
    }
    @FXML
    protected  void handleEditProfileProducer(ActionEvent Event) throws IOException{
        CreditSystemController.setRoot("ProducerProfile-edit");
    }
    @FXML
    protected  void handleAddCreditProducer(ActionEvent Event) throws IOException{
        CreditSystemController.setRoot("ProducerAddCredit");
    }
    @FXML
    protected  void handleSaveProfileProducer(ActionEvent Event) throws IOException{
        CreditSystemController.setRoot("ProducerProfile");
    } // tilføj kode der redigere profil
    @FXML
    protected  void handleAddPersonToProgram(ActionEvent Event) throws IOException{
        CreditSystemController.setRoot("ProducerAddCredit");
    } // Tilføj kode der tilføjere person til tekstfelt

    // Moderator
    @FXML
    protected void handleApproveCreditsModerator(ActionEvent Event) throws IOException{
        CreditSystemController.setRoot("ModeratorApproveCredits");
    }
    @FXML
    protected  void handleAddCreditsModerator(ActionEvent Event) throws IOException{
        CreditSystemController.setRoot("ModeratorAddCredits");
    }
    @FXML
    public void hanldeReload(MouseEvent mouseEvent) {
        reload();
    }
    protected EventHandler<ActionEvent> handleApproveButton(int Event) {
        System.out.println("før");
        CreditSystemController.getApprovedPersonList().add(CreditSystemController.getUnApprovedPersonList().get(Event));
        CreditSystemController.getUnApprovedPersonList().remove(Event);
        reload();
        System.out.println("efter" + Event);
        return null;
    }

    //Admin
    @FXML
    protected  void handleAddCreditsAdmin(ActionEvent Event) throws IOException{
        CreditSystemController.setRoot("AdminAddCredits");
    }
    @FXML
    protected  void handleApproveCreditsAdmin(ActionEvent Event) throws IOException{
        CreditSystemController.setRoot("AdminApproveCredits");
    }
    @FXML
    protected  void handleAddUserAdmin(ActionEvent Event) throws IOException{
        CreditSystemController.setRoot("AdminAddUsers");
    }
    @FXML
    protected  void handleUserRightsAdmin(ActionEvent Event) throws IOException{
        CreditSystemController.setRoot("AdminUserRights");
    }
    @FXML
    protected  void handleAddUserButtonAdmin(ActionEvent Event) throws IOException{
        CreditSystemController.setRoot("Dashboard");
    }
    @FXML
    protected void handleSearchEnter(ActionEvent Event) throws  IOException {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    private void reload() {
        personToApprove.getChildren().clear();

        int offset = 20;
        int buttonCounter = 0;
        for (Person person: CreditSystemController.getUnApprovedPersonList()) {
            Pane personPane = new Pane();
            personToApprove.getChildren().add(personPane);
            personPane.setLayoutY(offset);
            personPane.setId(String.valueOf(buttonCounter));

            Label personLabel = new Label("Name: " + person.getName());
            personLabel.setLayoutX(20);
            personPane.getChildren().add(personLabel);


            Button approveButton = new Button();
            approveButton.setText("Godkend");
            approveButton.setLayoutX(300);
            personPane.getChildren().add(approveButton);
            int finalButtonCounter = buttonCounter;
            approveButton.setOnAction(actionEvent -> handleApproveButton(finalButtonCounter));

            buttonCounter ++;

            offset += 30;


        }
    }

}
