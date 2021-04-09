package Java;

import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
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
    protected void handleLogin(ActionEvent Event) throws IOException {
        //redirect til Login side
        CreditSystemController.setRoot("Login");
    }

    //---- Login Typer
    @FXML
    protected  void handleLoggingIn(ActionEvent Event) throws IOException{
        //Tilføj kode der faktisk logger ind på en konto
        CreditSystemController.setRoot("CreditPerson");
    }
    @FXML
    protected  void handleLoggingInProducer(ActionEvent Event) throws IOException{
        //Tilføj kode der faktisk logger ind på en konto
        CreditSystemController.setRoot("Producer");
    }
    @FXML
    protected  void handleLoggingInModerator(ActionEvent Event) throws IOException{
        //Tilføj kode der faktisk logger ind på en konto
        CreditSystemController.setRoot("Moderator");
    }
    @FXML
    protected  void handleLoggingInAdmin(ActionEvent Event) throws IOException{
        //Tilføj kode der faktisk logger ind på en konto
        CreditSystemController.setRoot("Admin");
    }
    //-----

    // General features
    @FXML
    protected  void handleLogout(ActionEvent Event) throws IOException{
        //Tilføj kode der faktisk logger ud fra en konto
        CreditSystemController.setRoot("GUI");
    }
    @FXML
    protected void ToFrontpage(MouseEvent Event) throws IOException {
        //denne bruges på billedet af TV 2
        //hver gang billedet klikkes ryges der til forsiden
        CreditSystemController.setRoot("GUI");


    }


    @FXML
    protected void handleSendPersonButton(ActionEvent Event) throws IOException{
        Person person = new Person(
                personName.getText(),
                null,
                1,
                false,
                null,
                1,
                personPhone.getText(),
                null,
                personEmail.getText());
        CreditSystemController.personList.add(person);
        System.out.println(person.getName());
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
        CreditSystemController.setRoot("GUI");
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
        CreditSystemController.setRoot("GUI");
    }



    @FXML
    protected void handleSearch(ActionEvent Event) throws IOException, InterruptedException {
        //Når der bliver klikket på søg skal søge bar og knap rykkes op
        if (!searchContent.isVisible()) {
            TranslateTransition moveSearch= new TranslateTransition();
            moveSearch.setDuration(Duration.millis(500));
            moveSearch.setToY(-120);
            moveSearch.setNode(searchPane);
            moveSearch.play();
            //Sætter søge boxen til at være i fronten så den kan interaggeres med
            searchPane.setViewOrder(-1.0);
            moveSearch.setOnFinished(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    searchContent.setVisible(true);
                }
            }); }

        if (searchField.getText() != "") {
            for (Credit person: CreditSystemController.personList) {
                if (person.getName().contains(searchField.getText())) {
                    personLabel.setText(person.getName());
                }
            }
        }




    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


    }
}
