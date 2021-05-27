package Java.presentation.controllers;

import Java.data.DatabaseLoaderFacade;
import Java.domain.ApplicationManager;
import Java.domain.services.MovieManager;
import Java.domain.services.PersonManager;
import Java.domain.services.ShowManager;
import Java.interfaces.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.sql.ResultSet;
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
        private GridPane movieRolePane;
        private TextField itemName;
        private TextArea itemDescription;
        private Label itemJobs;
        private ComboBox jobRoles;
        private ComboBox movieList;
        private TextField characterName;
        private Button Godkend;

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
                    startEdit();
                }
            });
            approveBtn = new Button("Approve");
            approveBtn.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    System.out.println(getItem().getCreditID() + " + " + getItem().getName());
                    ApplicationManager.getInstance().approveCredit(getItem().getCreditID(), thisview.getItems());
                    thisview.getItems().remove(getItem());
                }
            });

            name = new Label();
            pane = new GridPane();


            itemName = new TextField();
            itemDescription = new TextArea();
            itemJobs = new Label();
            Godkend = new Button();
            Godkend.setText("Godkend");
            Godkend.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    //Kode til at gemme et nyt job
                    System.out.println("NOT IMPLEMENTED YET");

                    //reload edit
                }
            });


            ColumnConstraints col1 = new ColumnConstraints();
            col1.setPercentWidth(85);
            pane.getColumnConstraints().addAll(col1);
            pane.add(name, 0,0);
            pane.add(actionBtn,2,0);
            if (ApprovalBox.getSelectionModel().getSelectedItem().toString().equals("Unapproved")) {
                pane.add(approveBtn, 3, 0);
            }
            if (ApprovalBox.getSelectionModel().getSelectedItem().toString().equals("Approved") & pane.getChildren().contains(approveBtn)){
                pane.getChildren().removeAll(approveBtn);
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

        @Override
        public void startEdit(){
            super.startEdit();
            //Hvis det er en person
            if (PersonManager.getInstance().searchPerson(getItem().getName()) != null){
                for (IPerson person: PersonManager.getInstance().searchPerson(getItem().getName())) {
                    itemName.setPromptText(person.getName());
                    itemDescription.setPromptText(person.getDescription());
                    for (IJob iJob : person.getJobs()){
                        if (iJob.getCharacterName() != null){
                            itemJobs.setText(itemJobs.getText() + iJob.getRole() + "Spiller" + iJob.getCharacterName() + " på " + iJob.getProductionID() + "\n");
                        } else {
                            itemJobs.setText(itemJobs.getText() + iJob.getRole() + " på " + iJob.getProductionID() + "\n");
                        }
                    }
                    movieRolePane = new GridPane();
                    jobRoles = new ComboBox();
                    movieList = new ComboBox();
                    ResultSet jobRoleResultSet = null;
                    ObservableList<IMovie> MovieList = null;


                    try {
                        jobRoleResultSet = DatabaseLoaderFacade.getInstance().getJobRoles();
                        MovieList = MovieManager.getInstance().searchMovie("");
                        while (jobRoleResultSet.next()){
                            //set texten
                            jobRoles.getItems().add(jobRoleResultSet.getString(2));
                            //set id
                            jobRoles.idProperty().set(jobRoleResultSet.getString(1));
                        }
                        for (IMovie iMovie: MovieList) {
                            movieList.getItems().add(iMovie.getName());
                            movieList.idProperty().set(String.valueOf(iMovie.getProductionID()));
                        }

                    } catch (SQLException sqlException) {
                        sqlException.printStackTrace();
                    }
                }
            }
            //hvis det er en film
            if (MovieManager.getInstance().searchMovie(getItem().getName()) != null){

            }

            characterName = new TextField();
            characterName.setVisible(false);

            jobRoles.valueProperty().addListener(new ChangeListener() {
                @Override
                public void changed(ObservableValue observableValue, Object o, Object t1) {
                    if (jobRoles.getSelectionModel().getSelectedItem().toString().equals("Skuespiller")){
                        characterName.setVisible(true);
                    }
                }
            });

            itemName.setText(null);
            itemDescription.setText(null);

            pane.add(itemName,0,0);
            pane.add(itemDescription, 0,1);
            movieRolePane.add(jobRoles,0,0);
            movieRolePane.add(movieList,1,0);
            movieRolePane.add(characterName,0,1);
            movieRolePane.add(Godkend, 1,1);
            pane.add(movieRolePane,0,2);
            pane.add(itemJobs,0,3);

            pane.getChildren().remove(name);

            setGraphic(pane);
        }

        @Override
        public void commitEdit(ICredit credit) {
            super.commitEdit(credit);

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


        try {
            DatabaseLoaderFacade.getInstance().getAllUnApprovedCredits();
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
            thisview.setCellFactory(param -> new CustomCell<ICredit>(){
            });
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
            thisview.getItems().clear();
            thisview.setCellFactory(param -> new CustomCell<ICredit>(){
            });
            switch (toggleGroup.getSelectedToggle().toString()){
                case "Persons": thisview.setItems(ApplicationManager.getInstance().search(searchString, "persons")); break;
                case "Movies": thisview.setItems(ApplicationManager.getInstance().search(searchString, "movie")); break;
                case "Shows": thisview.setItems(ApplicationManager.getInstance().search(searchString, "shows")); break;
                default:
                    thisview.setItems(ApplicationManager.getInstance().search(searchString, ""));
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
        } else if (ApprovalBox.getSelectionModel().getSelectedItem().toString().equals("Unapproved")){
            setContent(movieObservableList);
        }
    }
    public void handleFilterShows(ActionEvent actionEvent){
        if (ApprovalBox.getSelectionModel().getSelectedItem().toString().equals("Approved")) {
            thisview.getItems().clear();
            thisview.setItems(ApplicationManager.getInstance().search("", "shows"));
        } else if (ApprovalBox.getSelectionModel().getSelectedItem().toString().equals("Unapproved")){
            setContent(showObservableList);
        }
    }
    public void handleFilterPersons(ActionEvent actionEvent){
        if (ApprovalBox.getSelectionModel().getSelectedItem().toString().equals("Approved")) {
            thisview.getItems().clear();
            thisview.setItems(ApplicationManager.getInstance().search(searchString, "persons"));
        } else if (ApprovalBox.getSelectionModel().getSelectedItem().toString().equals("Unapproved")){
            setContent(personObservableList);
        }
    }
    public void handleFilterAll(ActionEvent actionEvent){
        if (ApprovalBox.getSelectionModel().getSelectedItem().toString().equals("Approved")) {
            thisview.getItems().clear();
            thisview.setItems(ApplicationManager.getInstance().search(""));
        }
    }
}
