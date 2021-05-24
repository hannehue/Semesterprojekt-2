package Java.presentation.controllers;

import Java.domain.ApplicationManager;
import Java.domain.services.MovieManager;
import Java.domain.services.PersonManager;
import Java.interfaces.ICredit;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.stream.Collectors;


public class SearchController implements Initializable {
    private ListView SearchList;

    @FXML
    protected Pane searchPane;

    private static SearchController instance = new SearchController();

    private SearchController() {
    }

    public static SearchController getInstance() {
        return instance;
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
        SearchList.setLayoutX(282);
        SearchList.setLayoutY(127);
        SearchList.setPrefHeight(353);
        SearchList.setPrefWidth(424);
        searchPane.getChildren().add(SearchList);
    }

    public void setContent() {
        String searchString = MenuController.getInstance().getSearchString();
        SearchList.setItems(ApplicationManager.getInstance().search(searchString));
    }

    public void handleClickedItem(MouseEvent mouseEvent) {
        try {
            MenuController.getInstance().setContentPane("CreditView.fxml", CreditViewController.getInstance());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ICredit getCurrentCredit(){
        return (ICredit) SearchList.getSelectionModel().getSelectedItem();
    }
}
