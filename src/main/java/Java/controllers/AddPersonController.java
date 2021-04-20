package Java.controllers;

import Java.*;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddPersonController implements Initializable{
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
    
    @FXML
    protected ChoiceBox choiceBoxJob;
    @FXML
    protected TextField personName;
    @FXML
    protected Pane jobPane;
    @FXML
    protected Label creditTo;


    public void handleChoiceBoxJob(MouseEvent mouseEvent) {
            choiceBoxJob.getItems().clear();
            choiceBoxJob.getItems().add("Skuespiller");
            choiceBoxJob.getItems().add("Produktionshold");
            choiceBoxJob.show();


    }

    @FXML
    private void handleSetJob(ActionEvent Event) {
        switch (choiceBoxJob.getValue().toString()) {
            case "Skuespiller":
                handleAddCast();
                break;
            case "Produktionshold":
                handleAddTeam();
        }
    }


    private void handleAddCast() {


    }

    private void handleAddTeam() {
        jobPane.getChildren().clear();
        ChoiceBox<Role> roleChoiceBox = new ChoiceBox<>();
        Role[] roles = Role.values();
        for (Role role: roles) {
            roleChoiceBox.getItems().add(role);
        }
        jobPane.getChildren().add(roleChoiceBox);
        roleChoiceBox.setPrefWidth(300);
        addTeam();
    }

    private void addTeam() {

    }


}
