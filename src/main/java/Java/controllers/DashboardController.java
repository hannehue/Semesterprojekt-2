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
    @FXML
    protected AnchorPane movieToApprove;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }





    /* ------------------------------------------------------------------------------------------------------------------
        Metoder
    ------------------------------------------------------------------------------------------------------------------ */
    @FXML
    public void handleReloadPerson(MouseEvent mouseEvent) {
        reloadPerson();
    }
    @FXML
    public void handleReloadMovie(MouseEvent mouseEvent) {
        reloadMovie();
    }

    protected EventHandler<ActionEvent> handleApprovePerson(int Event) {
        System.out.println("før");
        for (Credit personCredit: CreditSystemController.getCreditList()) {
            if (personCredit.getCreditID() == Event) {
                personCredit.setApproved(true);
            }
        }
        reloadPerson();
        System.out.println("efter" + Event);
        return null;
    }

    protected EventHandler<ActionEvent> handleApproveMovie(int Event) {
        System.out.println("før");
        for (Credit movieCredit: CreditSystemController.getMovieList()) {
            if (movieCredit.getCreditID() == Event) {
                movieCredit.setApproved(true);
            }
        }
        reloadMovie();
        System.out.println("efter" + Event);
        return null;
    }

    private void reloadPerson() {
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
                approveButton.setOnAction(actionEvent -> handleApprovePerson(finalButtonCounter));

                offset += 30;
            }
        }
    }

    private void reloadMovie() {
        movieToApprove.getChildren().clear();

        int offset = 20;
        for (Credit movieCredit: CreditSystemController.getMovieList()) {
            if (!movieCredit.isApproved()) {
                Pane Pane = new Pane();
                movieToApprove.getChildren().add(Pane);
                Pane.setLayoutY(offset);
                Pane.setId(String.valueOf(movieCredit.getCreditID()));

                Label Label = new Label("Name: " + movieCredit.getName());
                Label.setLayoutX(20);
                Pane.getChildren().add(Label);

                Button approveButton = new Button();
                approveButton.setText("Godkend");
                approveButton.setLayoutX(300);
                Pane.getChildren().add(approveButton);
                int finalButtonCounter = movieCredit.getCreditID();
                approveButton.setOnAction(actionEvent -> handleApproveMovie(finalButtonCounter));

                offset += 30;
            }
        }
    }
}
