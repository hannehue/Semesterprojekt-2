package Java.presentation.controllers;

import Java.domain.ApplicationManager;
import Java.interfaces.ICredit;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class SearchController implements Initializable {
    private ListView<ICredit> SearchList;

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
        SearchList = new ListView<>();
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
        //Sett new cellFactory that display the prettyPrint and sets the cell item i an ICredit
        SearchList.setCellFactory(param -> new ListCell<ICredit>(){
            @Override
            protected void updateItem(ICredit credit, boolean empty) {
                super.updateItem(credit, empty);

                if (!empty){
                    setText(credit.buildView());
                }
            }
        });
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
