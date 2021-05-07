package Java.Application.controllers;

import Java.Application.Credit;
import Java.Application.CreditSystemController;
import Java.Application.Job;
import Java.Application.Role;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class AddPersonController implements Initializable {

    @FXML
    protected ChoiceBox<Role> jobRole;
    @FXML
    protected TextField findPerson;
    @FXML
    protected AnchorPane characterNamePane;
    @FXML
    protected AnchorPane contentPane;

    protected TextField characterName = new TextField();
    private static ListView SearchList;
    private Credit personToCredit;


    public void handleJob(MouseEvent mouseEvent) {
        jobRole.getItems().clear();
        Role[] roles = Role.values();
        for (Role role : roles) {
            jobRole.getItems().add(role);
        }
        jobRole.show();
    }

    public void handleFindPerson(ActionEvent actionEvent) {
        searchPerson();
        setCharacterNameField();
    }

    public void handleAddPerson(ActionEvent actionEvent) {
        Job job;
        if (jobRole.getValue() == Role.SKUESPILLER) {
            job = new Job(personToCredit.getCreditID(), jobRole.getValue(), characterName.getText());
        } else {
            job = new Job(personToCredit.getCreditID(), jobRole.getValue());
        }
        CreditSystemController.addTempJob(job);
    }

    private void searchPerson() {
        String searchString = findPerson.getText().toLowerCase();
        ArrayList<Credit> creditList = new ArrayList<>();
        for (Credit person : CreditSystemController.getPersonList()) {
            if (person.getName().toLowerCase().contains(searchString)) {
                creditList.add(person);
            }
        }
        setContent(creditList);
    }

    public static void setContent(ArrayList<Credit> creditList) {
        ObservableList<Credit> observableResults = FXCollections.observableArrayList();
        observableResults.addAll(creditList);
        System.out.println(observableResults);
        SearchList.setItems(observableResults);
        SearchList.setVisible(true);
    }


    private void setCharacterNameField() {
        characterNamePane.getChildren().clear();
        if (jobRole.getValue() == Role.SKUESPILLER) {
            characterName.setPrefWidth(495);
            characterName.setPrefHeight(33);
            characterName.setPromptText("Role navn");
            characterNamePane.getChildren().add(characterName);
        }
    }

    private void handleClickedItem(MouseEvent event) {
        personToCredit = (Credit) SearchList.getSelectionModel().getSelectedItem();
        findPerson.setText(personToCredit.getName());
        SearchList.setVisible(false);
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {


        SearchList = new ListView();
        SearchList.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                handleClickedItem(event);
            }
        });
        SearchList.setId("SearchList");
        SearchList.setPrefHeight(337);
        SearchList.setPrefWidth(495);
        SearchList.setVisible(false);
        contentPane.getChildren().add(SearchList);
    }
}










