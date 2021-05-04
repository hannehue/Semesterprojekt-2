package Java.presentation.controllers;

import Java.Credit;
import Java.CreditSystemController;
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
import java.util.ResourceBundle;


public class SearchController implements Initializable {
    private ListView SearchList;

    @FXML
    protected Pane searchPane;

    private static SearchController searchController = new SearchController();

    private SearchController() {

    }

    public static SearchController getInstance() {
        return searchController;
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
        ObservableList<Credit> observableResults = FXCollections.observableArrayList();
        observableResults.addAll(search(MenuController.getInstance().getSearchString()));
        System.out.println(observableResults);
        SearchList.setItems(observableResults);
    }


    private ArrayList<Credit> search(String getsearchString){
        String searchStringChecked = getsearchString.toLowerCase();
        ArrayList<Credit> creditList = new ArrayList<>();
        for(Credit person : CreditSystemController.getInstance().getPersonList()) {
            if (person != null && person.getName().toLowerCase().contains(searchStringChecked) && person.isApproved()){
                creditList.add(person);
            }
        }
        return creditList;
    }

    public void handleClickedItem(MouseEvent mouseEvent) {
        Credit item = (Credit) SearchList.getSelectionModel().getSelectedItem();
        System.out.println("clicked" + item);
        CreditViewController.getInstance().setCurrentCredit(item);
        try {
            MenuController.getInstance().setContentPane("CreditView.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
