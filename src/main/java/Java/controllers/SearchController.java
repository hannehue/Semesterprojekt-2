package Java.controllers;

import Java.Credit;
import Java.CreditSystemController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class SearchController implements Initializable {
    @FXML
    protected static ListView SearchList;

    private static String searchString;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public static void setSearchContent(){
        ObservableList<Credit> observableResults = FXCollections.observableArrayList();
        observableResults.addAll(search("p"));
        SearchList.setItems(observableResults);
    }


    public static void setSearchString(String searchFieldString) {
        searchString = searchFieldString;
    }

    private static ArrayList<Credit> search(String getsearchString){
        String searchStringChecked = getsearchString.toLowerCase();
        ArrayList<Credit> creditList = new ArrayList<>();
        for(Credit person : CreditSystemController.getPersonList()) {
            if (person.getName().toLowerCase().contains(searchStringChecked) && person.isApproved()){
                creditList.add(person);
            }
        }
        return creditList;
    }

    public void handleClickedItem(MouseEvent mouseEvent) {
        Credit item = (Credit) SearchList.getSelectionModel().getSelectedItem();
        System.out.println("clicked" + item);
        CreditViewController.setCurrentCredit(item);
        try {
            CreditSystemController.setRoot("CreditView");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
