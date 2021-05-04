package Java.presentation.controllers;

import Java.Credit;
import Java.CreditSystemController;
import Java.Job;
import Java.Person;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections.*;

public class CreditViewController implements Initializable {

    @FXML
    GridPane gridPane;
    @FXML
    Label nameLabel;
    @FXML
    Label descriptionLabel;
    private static CreditViewController creditViewController = new CreditViewController();

    private Credit currentCredit;

    public static CreditViewController getInstance() {
        return creditViewController;
    }

    public void setCurrentCredit(Credit inputCredit) {
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
            addToGrid(gridPane, new Node[] {personInfoLabel, personInfo});

            ListView jobView = new ListView();
            Label jobLabel = new Label();
            jobLabel.setText("Jobs");
            jobLabel.setStyle("-fx-font-weight: bold");
            jobLabel.setPadding(padding);

            ObservableList<Job> observableJobs = FXCollections.observableArrayList();
            observableJobs.addAll(personCredit.getJobs());
            jobView.setItems(observableJobs);

            addToGrid(gridPane, new Node[] {jobLabel, jobView});
        }
    }

    private <Node> void addToGrid(GridPane grid, Node[] objlist){
        assert objlist.length == 2;
        int rowCount = gridPane.getRowCount();
        gridPane.add((javafx.scene.Node) objlist[0], 0,  rowCount);
        gridPane.add((javafx.scene.Node) objlist[1], 1, rowCount);

    }
}
