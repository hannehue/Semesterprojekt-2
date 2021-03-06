package Java.presentation.controllers;

import Java.domain.data.Role;
import Java.domain.services.JobManager;
import Java.domain.services.PersonManager;
import Java.interfaces.ICredit;
import Java.interfaces.IJob;
import Java.interfaces.IPerson;
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
    private ListView SearchList;
    private IPerson personToCredit;
    private static AddPersonController instance = new AddPersonController();

    private AddPersonController() {
    }

    public static AddPersonController getInstance() {
        return instance;
    }

    public void handleJob(MouseEvent mouseEvent) {
        jobRole.getItems().clear();
        Role[] roles = Role.values();
        for (Role role : roles) {
            jobRole.getItems().add(role);
        }
        jobRole.show();
    }

    public void handleFindPerson(ActionEvent actionEvent) {
        ObservableList<ICredit> searchList = FXCollections.observableArrayList();
                searchList.addAll(PersonManager.getInstance().searchPerson(findPerson.getText().toLowerCase()));
        setContent(searchList);
        setCharacterNameField();
    }

    public void handleAddPerson(ActionEvent actionEvent) {
        IJob job;
        if (jobRole.getValue() == Role.SKUESPILLER) {
            JobManager.getInstance().addTempJob(personToCredit.getPersonID(), jobRole.getValue(), characterName.getText(), 0);
        } else {
            JobManager.getInstance().addTempJob(personToCredit.getPersonID(), jobRole.getValue(), 0);
        }
        findPerson.clear();
        characterName.clear();
        characterNamePane.getChildren().clear();
    }

    /**
     * Sets the content of the SearchList
     *
     * @param creditList
     */
    private void setContent(ObservableList<ICredit> creditList) {
        ObservableList<ICredit> observableResults = FXCollections.observableArrayList();
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
        personToCredit = (IPerson) SearchList.getSelectionModel().getSelectedItem();
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










