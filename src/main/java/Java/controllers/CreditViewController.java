package Java.controllers;

import Java.Credit;
import Java.Person;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ResourceBundle;

public class CreditViewController implements Initializable {

    @FXML
    GridPane gridPane;
    @FXML
    Label nameLabel;
    @FXML
    Label descriptionLabel;

    private static Credit currentCredit;

    public static void setCurrentCredit(Credit inputCredit) {
        currentCredit = inputCredit;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        assert currentCredit.isApproved();

        nameLabel.setText(currentCredit.getName());
        descriptionLabel.setText(currentCredit.getDescription());

        if (currentCredit.getClass() == Person.class){
            Person personCredit = (Person) currentCredit;
            Label personInfoLabel = new Label();
            Label personInfo = new Label();

            personInfoLabel.setText("Personal info");
            personInfoLabel.setStyle("-fx-font-weight: bold");
            personInfo.setText(personCredit.getPersonalInfo());

            Insets padding = new Insets(10, 10, 10, 10);
            personInfoLabel.setPadding(padding);
            personInfo.setPadding(padding);

            int rowCount = gridPane.getRowCount();
            gridPane.add(personInfoLabel, 0,  rowCount);
            gridPane.add(personInfo, 1, rowCount);

        }
    }
}
