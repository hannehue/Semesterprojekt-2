package Java;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


/* ------------------------------------------------------------------------------------------------------------------
Denne Controller bruges til Menuen
------------------------------------------------------------------------------------------------------------------ */

public class MenuController implements Initializable {
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
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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
        CreditSystemController.setRoot("GUI");
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
        CreditSystemController.setRoot("Dashboard");
    }

    public void handlePersonalProfile(ActionEvent event) throws IOException {
        CreditSystemController.setRoot("PersonalProfile");
    }
}
