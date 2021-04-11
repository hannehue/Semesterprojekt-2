package Java;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {

    @FXML
    protected MenuItem approveCredit;
    @FXML
    protected MenuItem addCredits;
    @FXML
    protected MenuItem addUser;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        approveCredit.setVisible(CreditSystemController.getUserType().getApproveCredit());
        addCredits.setVisible(CreditSystemController.getUserType().getAddCredit());
        addUser.setVisible(CreditSystemController.getUserType().getAddUser());
    }

    @FXML
    protected void toFrontpage(MouseEvent Event) throws IOException {
        //denne bruges på billedet af TV 2
        //hver gang billedet klikkes ryges der til forsiden
        CreditSystemController.setRoot("GUI");
    }

    @FXML
    protected  void handleLogout(ActionEvent Event) throws IOException{
        //Tilføj kode der faktisk logger ud fra en konto
        CreditSystemController.setRoot("GUI");
    }

    @FXML
    protected  void handleAddCredits(ActionEvent Event) throws IOException{
        CreditSystemController.setRoot("AdminAddCredits");
    }
    @FXML
    protected  void handleApproveCredits(ActionEvent Event) throws IOException{
        CreditSystemController.setRoot("AdminApproveCredits");
    }
    @FXML
    protected  void handleAddUser(ActionEvent Event) throws IOException{
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
    protected void handleSearchEnter(ActionEvent Event) throws  IOException {
        }

}
