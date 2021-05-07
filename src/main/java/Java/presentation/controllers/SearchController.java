package Java.presentation.controllers;

import Java.presentation.CreditSystemController;
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
import java.util.ResourceBundle;


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
        ObservableList<ICredit> observableResults = FXCollections.observableArrayList();
        observableResults.addAll(search(MenuController.getInstance().getSearchString()));
        System.out.println(observableResults);
        SearchList.setItems(observableResults);
    }


    private ArrayList<ICredit> search(String getsearchString){
        String searchStringChecked = getsearchString.toLowerCase();
        ArrayList<ICredit> creditList = new ArrayList<>();
        for(ICredit person : CreditSystemController.getInstance().getPersonList()) {
            if (person != null && person.getName().toLowerCase().contains(searchStringChecked) && person.isApproved()){
                creditList.add(person);
            }
        }
        return creditList;
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
