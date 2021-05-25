package Java.presentation.controllers;

import Java.data.DatabaseLoaderFacade;
import Java.domain.ApplicationManager;
import Java.domain.data.Credit;
import Java.domain.services.MovieManager;
import Java.domain.services.PersonManager;
import Java.domain.services.ShowManager;
import Java.interfaces.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;

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
    protected ListView thisview;

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




    private class CustomCell<I> extends ListCell<ICredit>{
        private Button actionBtn;
        private Button approveBtn;
        private Label name;
        private GridPane pane;

        public CustomCell(){
            super();
            setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    System.out.println("hello");
                }
            });
            actionBtn = new Button("edit");
            actionBtn.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    System.out.println("Action: "+getItem());
                }
            });
            approveBtn = new Button("Approve");
            approveBtn.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    System.out.println(getItem().getCreditID() + " + " + getItem().getName());
                    ApplicationManager.getInstance().approveCredit(getItem().getCreditID(), thisview.getItems());
                    thisview.getItems().clear();

                    setContent(movieObservableList);
                    setContent(personObservableList);
                    setContent(showObservableList);
                }
            });
            name = new Label();
            pane = new GridPane();
            ColumnConstraints col1 = new ColumnConstraints();
            col1.setPercentWidth(90);
            pane.getColumnConstraints().addAll(col1);
            pane.add(name, 0,0);
            pane.add(actionBtn,1,0);
            if (ApprovalBox.getSelectionModel().getSelectedItem().toString().equals("Unapproved")) {
                pane.add(approveBtn, 1, 0);
            }
            setText(null);
        }
        @Override
        public void updateItem(ICredit credit, boolean empty){
            super.updateItem(credit, empty);
            if (!empty){
                name.setText(credit.buildView());
                setGraphic(pane);
            } else {
                setGraphic(null);
            }
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ApprovalBox.getSelectionModel().selectFirst();
        FilterAllButton.setToggleGroup(toggleGroup);
        FilterPersonButton.setToggleGroup(toggleGroup);
        FilterMovieButton.setToggleGroup(toggleGroup);
        FilterShowButton.setToggleGroup(toggleGroup);
        FilterAllButton.selectedProperty().set(true);

        DatabaseLoaderFacade database = DatabaseLoaderFacade.getInstance();
        try {
            database.getAllUnApprovedCredits();
        }
        catch (SQLException e){
            e.printStackTrace();
        }

        movieObservableList = MovieManager.getInstance().getMovies();
        personObservableList = PersonManager.getInstance().getPersonList();
        showObservableList = ShowManager.getInstance().getShowList();

        thisview.setCellFactory(param -> new CustomCell<ICredit>(){
        });
        thisview.setItems(ApplicationManager.getInstance().search(""));
    }



    public void changeApprovalType(ActionEvent actionEvent) {
        if (ApprovalBox.getSelectionModel().getSelectedItem().toString().equals("Unapproved")){
            //fjern liste content
            thisview.getItems().clear();
            switch (toggleGroup.getSelectedToggle().toString()){
                case "Persons": setContent(personObservableList); break;
                case "Movies": setContent(movieObservableList); break;
                case "Shows": setContent(showObservableList); break;
                default:
                    setContent(personObservableList);
                    setContent(movieObservableList);
                    setContent(showObservableList);
                    break;
            }
        }
        if (ApprovalBox.getSelectionModel().getSelectedItem().toString().equals("Approved")){
            switch (toggleGroup.getSelectedToggle().toString()){
                case "Persons": thisview.setItems(ApplicationManager.getInstance().search(searchString, "persons")); break;
                case "Movies": thisview.setItems(ApplicationManager.getInstance().search(searchString, "movie")); break;
                case "Shows": thisview.setItems(ApplicationManager.getInstance().search(searchString, "shows")); break;
                default:
                    thisview.setItems(ApplicationManager.getInstance().search(searchString, "persons"));
                    thisview.setItems(ApplicationManager.getInstance().search(searchString, "movie"));
                    thisview.setItems(ApplicationManager.getInstance().search(searchString, "shows"));
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
        thisview.setItems(listToSet);
    }


    public void handleFilterMovies(ActionEvent actionEvent){
        if (ApprovalBox.getSelectionModel().getSelectedItem().toString().equals("Approved")) {
            thisview.getItems().clear();
            thisview.setItems(ApplicationManager.getInstance().search(searchString, "movie"));
        }
    }
    public void handleFilterShows(ActionEvent actionEvent){
        if (ApprovalBox.getSelectionModel().getSelectedItem().toString().equals("Approved")) {
            thisview.getItems().clear();
            thisview.setItems(ApplicationManager.getInstance().search("", "shows"));
        }
    }
    public void handleFilterPersons(ActionEvent actionEvent){
        if (ApprovalBox.getSelectionModel().getSelectedItem().toString().equals("Approved")) {
            thisview.getItems().clear();
            thisview.setItems(ApplicationManager.getInstance().search(searchString, "persons"));
        }
    }
    public void handleFilterAll(ActionEvent actionEvent){
        if (ApprovalBox.getSelectionModel().getSelectedItem().toString().equals("Approved")) {
            thisview.getItems().clear();
            thisview.setItems(ApplicationManager.getInstance().search(""));
        }
    }
}
