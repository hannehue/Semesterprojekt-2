package Java.controllers;

import Java.Credit;
import Java.Person;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class CreditViewController implements Initializable {

    @FXML
    Label nameLabel;
    @FXML
    Label personalInfoLabel;
    @FXML
    Label descriptionLabel;

    private static Credit currentCredit;

    public static <T extends Credit> void setCurrentCredit(T inputCredit) {
        currentCredit = inputCredit;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        assert currentCredit.isApproved();

        nameLabel.setText(currentCredit.getName());
        descriptionLabel.setText(currentCredit.getDescription());

        if (currentCredit.getClass() == Person.class){
            Person personCredit = (Person) currentCredit;
            personalInfoLabel.setText(personCredit.getPersonalInfo());
        }

    }
}
