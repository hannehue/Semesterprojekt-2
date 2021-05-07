package Java.Application.controllers;

import Java.Application.*;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ResourceBundle;

import static javafx.collections.FXCollections.*;

public class CreditViewController implements Initializable {

    @FXML
    GridPane gridPane;
    @FXML
    Label nameLabel;
    @FXML
    Label descriptionLabel;

    private static ICredit currentCredit;

    public static void setCurrentCredit(ICredit inputCredit) {
        currentCredit = inputCredit;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        assert currentCredit.isApproved();

        nameLabel.setText(currentCredit.getName());
        descriptionLabel.setText(currentCredit.getDescription());

        if (currentCredit instanceof IPerson){
            IPerson personCredit = (Person) currentCredit;

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

            ObservableList<Job> observableJobs = observableArrayList();
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
