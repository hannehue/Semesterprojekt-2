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
    protected Pane searchPane;
    @FXML
    protected Pane searchContent;
    @FXML
    protected TextField searchField;
    @FXML
    protected Label personLabel;
    @FXML
    protected AnchorPane personToApprove;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    protected void handleSearchEnter(ActionEvent Event) throws  IOException {

    }
    @FXML
    protected void handleSearch(ActionEvent Event) throws IOException, InterruptedException {
        //Når der bliver klikket på søg skal søge bar og knap rykkes op
        if (!searchContent.isVisible()) {
            TranslateTransition moveSearch= new TranslateTransition();
            moveSearch.setDuration(Duration.millis(500));
            moveSearch.setToY(-75);
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

        if (!searchField.getText().equals("")) {
            String temp = "";
            for (Credit person: CreditSystemController.getCreditList()) {
                if (person.getName().contains(searchField.getText())) {
                    temp += person.getName() + "\n";
                }
            } personLabel.setText(temp);
        }
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
