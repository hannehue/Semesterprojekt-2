package Java.presentation.controllers;

import Java.data.DatabaseLoaderFacade;
import Java.domain.ApplicationManager;
import Java.domain.objectMapping.CustomCellFactory;
import Java.domain.services.MovieManager;
import Java.domain.services.PersonManager;
import Java.domain.services.ShowManager;
import Java.interfaces.*;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.sql.SQLException;
import java.util.*;

public class CreditOverlookController implements Initializable {

    private static CreditOverlookController instance = new CreditOverlookController();
    private CreditOverlookController() { }
    public static CreditOverlookController getInstance() {
        return instance;
    }

    @FXML
    protected ListView listView;

    @FXML
    protected RadioButton FilterMovieButton;
    @FXML
    protected RadioButton FilterShowButton;
    @FXML
    protected RadioButton FilterPersonButton;
    @FXML
    protected RadioButton FilterAllButton;

    @FXML
    protected ComboBox ApprovalBox;


    ToggleGroup toggleGroup = new ToggleGroup();
    String searchString = "";

    private ObservableList<IPerson> personObservableList;
    private ObservableList<IMovie> movieObservableList;
    private ObservableList<IShow> showObservableList;
    private ObservableList<ISeason> seasonObservableList;
    private ObservableList<IEpisode> episodeObservableList;

    private ObservableList<String> emptyList;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ApprovalBox.getSelectionModel().selectFirst();
        FilterAllButton.setToggleGroup(toggleGroup);
        FilterPersonButton.setToggleGroup(toggleGroup);
        FilterMovieButton.setToggleGroup(toggleGroup);
        FilterShowButton.setToggleGroup(toggleGroup);
        FilterAllButton.selectedProperty().set(true);

        //hent alle unapproved krediteringer
        try {
            DatabaseLoaderFacade.getInstance().getAllUnApprovedCredits();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        //sæt dem i lister
        movieObservableList = MovieManager.getInstance().getMovies();
        personObservableList = PersonManager.getInstance().getPersonList();
        showObservableList = ShowManager.getInstance().getShowList();

        //hent ting
        ObservableList<ICredit> observableList = ApplicationManager.getInstance().search("");
        //tilføj listener
        observableList.addListener(new ListChangeListener<ICredit>() {
            @Override
            public void onChanged(Change<? extends ICredit> change) {
                System.out.println("something changed");
            }
        });
        //sætter hver celle til at bruge CustomCell
        listView.setCellFactory(new CustomCellFactory());
        //gør listen redigerings mulig
        listView.setEditable(true);
        //sætter ting ind
        listView.setItems(observableList);
    }



    public void changeApprovalType(ActionEvent actionEvent) {
        if (ApprovalBox.getSelectionModel().getSelectedItem().toString().equals("Unapproved")){
            //fjern liste content
            listView.getItems().clear();
            switch (toggleGroup.getSelectedToggle().toString()) {
                case "Persons" -> setContent(personObservableList);
                case "Movies" -> setContent(movieObservableList);
                case "Shows" -> setContent(showObservableList);
                case "All" -> setContent(ApplicationManager.getInstance().search(searchString, ""));
                default -> {
                    setContent(personObservableList);
                    setContent(movieObservableList);
                    setContent(showObservableList);
                }
            }
        }
        if (ApprovalBox.getSelectionModel().getSelectedItem().toString().equals("Approved")){
            listView.getItems().clear();
            switch (toggleGroup.getSelectedToggle().toString()){
                case "Persons": listView.setItems(ApplicationManager.getInstance().search(searchString, "persons")); break;
                case "Movies": listView.setItems(ApplicationManager.getInstance().search(searchString, "movie")); break;
                case "Shows": listView.setItems(ApplicationManager.getInstance().search(searchString, "shows")); break;
                default:
                    listView.setItems(ApplicationManager.getInstance().search(searchString, ""));
                    break;
            }
        }
    }

    public void setContent (ObservableList<? extends ICredit> creditList){
        ObservableList listToSet = FXCollections.observableArrayList();
        for (ICredit credit : creditList){
            if (!credit.isApproved()){
                listToSet.add(credit);
            }
        }
        listView.setItems(listToSet);
    }


    public void handleFilterMovies(ActionEvent actionEvent){
        if (ApprovalBox.getSelectionModel().getSelectedItem().toString().equals("Approved")) {
            listView.getItems().clear();
            listView.setItems(ApplicationManager.getInstance().search(searchString, "movie"));
        } else if (ApprovalBox.getSelectionModel().getSelectedItem().toString().equals("Unapproved")){
            setContent(movieObservableList);
        }
    }
    public void handleFilterShows(ActionEvent actionEvent){
        if (ApprovalBox.getSelectionModel().getSelectedItem().toString().equals("Approved")) {
            listView.getItems().clear();
            listView.setItems(ApplicationManager.getInstance().search("", "shows"));
        } else if (ApprovalBox.getSelectionModel().getSelectedItem().toString().equals("Unapproved")){
            setContent(showObservableList);
        }
    }
    public void handleFilterPersons(ActionEvent actionEvent){
        if (ApprovalBox.getSelectionModel().getSelectedItem().toString().equals("Approved")) {
            listView.getItems().clear();
            listView.setItems(ApplicationManager.getInstance().search(searchString, "persons"));
        } else if (ApprovalBox.getSelectionModel().getSelectedItem().toString().equals("Unapproved")){
            setContent(personObservableList);
        }
    }
    public void handleFilterAll(ActionEvent actionEvent){
        if (ApprovalBox.getSelectionModel().getSelectedItem().toString().equals("Approved")) {
            listView.getItems().clear();
            listView.setItems(ApplicationManager.getInstance().search(""));
        }
    }
}
