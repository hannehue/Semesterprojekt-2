package Java.controllers;

import Java.Credit;
import Java.CreditSystemController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class SearchController implements Initializable {
    @FXML
    protected ListView SearchList;

    private static String searchString;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<Credit> observableResults = FXCollections.observableArrayList();
        observableResults.addAll(search(searchString));
        SearchList.setItems(observableResults);
    }

    public static void setSearchString(String searchFieldString) {
        searchString = searchFieldString;
    }

    private ArrayList<Credit> search(String search){
        String searchStringChecked = search.toLowerCase();
        ArrayList<Credit> creditList = new ArrayList<>();
        for(Credit person : CreditSystemController.getCreditList()) {
            if (person.getName().toLowerCase().contains(searchStringChecked) && person.isApproved()){
                creditList.add(person);
            }
        }
        return creditList;
    }
}
