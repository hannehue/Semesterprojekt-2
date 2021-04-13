package Java.controllers;

import Java.Credit;
import Java.CreditSystemController;
import Java.Person;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/* ------------------------------------------------------------------------------------------------------------------
Denne Controller alt funktionalitet på de forskellige sider
------------------------------------------------------------------------------------------------------------------ */

public class DashboardController implements Initializable {
    @FXML
    protected AnchorPane personToApprove;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }





    /* ------------------------------------------------------------------------------------------------------------------
        Metoder
    ------------------------------------------------------------------------------------------------------------------ */
    @FXML
    public void handleReloadPerson(MouseEvent mouseEvent) {
        reload();
    }

    protected EventHandler<ActionEvent> handleApproveButton(int Event) {
        System.out.println("før");
        for (Credit personCredit: CreditSystemController.getCreditList()) {
            if (personCredit.getCreditID() == Event) {
                CreditSystemController.getCreditList().get(Event).setApproved(true);
            }
        }
        reload();
        System.out.println("efter" + Event);
        return null;
    }


    private void reload() {
        personToApprove.getChildren().clear();

        int offset = 20;
        for (Credit personCredit: CreditSystemController.getCreditList()) {
            if (!personCredit.isApproved()) {
                Pane personPane = new Pane();
                personToApprove.getChildren().add(personPane);
                personPane.setLayoutY(offset);
                personPane.setId(String.valueOf(personCredit.getCreditID()));

                Label personLabel = new Label("Name: " + personCredit.getName());
                personLabel.setLayoutX(20);
                personPane.getChildren().add(personLabel);


                Button approveButton = new Button();
                approveButton.setText("Godkend");
                approveButton.setLayoutX(300);
                personPane.getChildren().add(approveButton);
                int finalButtonCounter = personCredit.getCreditID();
                approveButton.setOnAction(actionEvent -> handleApproveButton(finalButtonCounter));

                offset += 30;
            }
        }
    }
}
