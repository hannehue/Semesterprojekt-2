package Java.presentation.controllers;

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

public class GUIController {

    private static GUIController instance = new GUIController();

    private GUIController() {

    }

    public static GUIController getInstance(){
        return instance;
    }

    //Admin
    @FXML
    protected void handleAddUserButtonAdmin(ActionEvent Event) throws IOException{
        CreditSystemController.getInstance().setRoot("Dashboard", DashboardController.getInstance());
    }
}
