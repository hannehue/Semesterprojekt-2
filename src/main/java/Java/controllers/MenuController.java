/*
package Java.controllers;

import Java.Credit;
import Java.CreditSystemController;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


*/
/* ------------------------------------------------------------------------------------------------------------------
Denne Controller bruges til Menuen
------------------------------------------------------------------------------------------------------------------ *//*


public class MenuController implements Initializable {
    @FXML
    protected Pane searchPane;
    @FXML
    protected Pane searchContent;
    @FXML
    protected TextField searchField;
    @FXML
    protected MenuItem approveCredit;
    @FXML
    protected MenuItem addCredits;
    @FXML
    protected MenuItem addUser;
    @FXML
    protected SplitMenuButton menuButton;
    @FXML
    protected Button loginButton;
    @FXML
    protected Label personLabel;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (CreditSystemController.getSearchFieldPlaceholder().equalsIgnoreCase("")){
            CreditSystemController.setSearchFieldPlaceholder("Søg");
            searchField.setPromptText(CreditSystemController.getSearchFieldPlaceholder());
        } else {
            searchField.setPromptText(CreditSystemController.getSearchFieldPlaceholder());
        }
        if (CreditSystemController.getUserType() != null) {
            menuButton.setVisible(true);
            approveCredit.setVisible(CreditSystemController.getUserType().getApproveCredit());
            addCredits.setVisible(CreditSystemController.getUserType().getAddCredit());
            addUser.setVisible(CreditSystemController.getUserType().getAddUser());
            menuButton.setText(CreditSystemController.getUserType().toString());
        } else {
            loginButton.setVisible(true);
        }
    }
    @FXML
    protected void handleSearch(ActionEvent Event) throws IOException, InterruptedException {
        //Når der bliver klikket på søg skal søge bar og knap rykkes op
        if (!searchField.getText().equalsIgnoreCase("")) {
            searchField.setPromptText(searchField.getText());
            CreditSystemController.setSearchFieldPlaceholder(searchField.getText());
            SearchController.setSearchString(searchField.getText());
            CreditSystemController.setRoot("SearchResult");
        } else {
            searchField.setPromptText("Indtast noget");
        }
    }

    @FXML
    protected  void handleAddCredits(ActionEvent Event) throws IOException {
        CreditSystemController.setRoot("AddCredits");
    }
    @FXML
    protected  void handleApproveCredits(ActionEvent Event) throws IOException{
        CreditSystemController.setRoot("ApproveCredits");
    }
    @FXML
    protected  void handleAddUser(ActionEvent Event) throws IOException{
        CreditSystemController.setRoot("AddUsers");
    }
    @FXML
    protected  void handleUserRightsAdmin(ActionEvent Event) throws IOException{
        CreditSystemController.setRoot("UserRights");
    }
    @FXML
    protected  void handleAddUserButtonAdmin(ActionEvent Event) throws IOException{
        CreditSystemController.setRoot("Dashboard");
    }
    @FXML
    protected void handleLogin(ActionEvent Event) throws  IOException{
        CreditSystemController.setRoot("Login");
    }
    @FXML
    protected  void handleLogout(ActionEvent Event) throws IOException{
        //Tilføj kode der faktisk logger ud fra en konto
        CreditSystemController.setUserType(null);
        CreditSystemController.setRoot("Dashboard");
    }
    @FXML
    protected void toFrontpage(MouseEvent Event) throws IOException {
        //denne bruges på billedet af TV 2
        //hver gang billedet klikkes ryges der til forsiden
        System.out.println("go to frontsf");
        CreditSystemController.setRoot("Dashboard");
    }

    public void handlePersonalProfile(ActionEvent event) throws IOException {
        CreditSystemController.setRoot("PersonalProfile");
    }
}
*/
