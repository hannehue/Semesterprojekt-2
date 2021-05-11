package Java.presentation.controllers;

import Java.interfaces.ICredit;
import Java.interfaces.IJob;
import Java.interfaces.IPerson;
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
import java.util.ResourceBundle;

public class CreditViewController implements Initializable {

    @FXML
    GridPane gridPane;
    @FXML
    Label nameLabel;
    @FXML
    Label descriptionLabel;
    private static CreditViewController instance = new CreditViewController();

    private ICredit currentCredit;

    private CreditViewController() {
    }

    public static CreditViewController getInstance() {
        return instance;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        currentCredit = SearchController.getInstance().getCurrentCredit();
        assert currentCredit.isApproved();

        nameLabel.setText(currentCredit.getName());
        descriptionLabel.setText(currentCredit.getDescription());

        if (currentCredit instanceof IPerson){
            IPerson personCredit = (IPerson) currentCredit;

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

            ObservableList<IJob> observableJobs = FXCollections.observableArrayList();
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
