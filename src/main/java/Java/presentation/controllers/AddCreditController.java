package Java.presentation.controllers;

import Java.data.DatabaseLoaderFacade;
import Java.domain.ApplicationManager;
import Java.domain.data.Category;
import Java.domain.services.*;
import Java.interfaces.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.security.Key;
import java.util.Date;
import java.util.ResourceBundle;

public class AddCreditController implements Initializable {
    @FXML
    protected ChoiceBox choiceBoxSeason;
    @FXML
    protected ChoiceBox choiceBoxCategory;
    @FXML
    protected ChoiceBox choiceBoxCategoryMovie;
    @FXML
    protected TextField personName;
    @FXML
    protected TextField personEmail;
    @FXML
    protected TextField personPhone;
    @FXML
    protected TextField personDescription;
    @FXML
    protected TextField movieTitle;
    @FXML
    protected TextField movieDescription;
    @FXML
    protected TextField movieLength;
    @FXML
    protected TextField episodeId;
    @FXML
    protected TextField episodeTitle;
    @FXML
    protected TextField episodeLength;
    @FXML
    protected Pane moviePeoplePane;
    @FXML
    protected Pane episodePeoplePane;
    @FXML
    protected Pane showSearchPane;
    @FXML
    protected TextField showSearch;

    private ListView PersonList;
    private ListView episodePersonList;
    private ListView showSearchList;

    private ObservableList<IShow> showList;

    private Stage createShow;
    private Stage createSeason;

    private IShow currentShow;

    private static AddCreditController instance = new AddCreditController();

    private AddCreditController() {
    }

    public static AddCreditController getInstance() {
        return instance;
    }


    /**
     *
     * @param Event mouseevent from FXML
     *
     *      Populates the season choicebox with the seasons from the selected show
     */
    public void handleGetSeason(MouseEvent Event) {
        choiceBoxSeason.getItems().clear();
        ICredit selectedCredit = (ICredit)  showSearchList.getSelectionModel().getSelectedItem();
        if ( selectedCredit  != null) {
            IShow show = ShowManager.getInstance().getShowById(selectedCredit.getCreditID());
            if (show != null) {
                for (Integer seasonId : show.getSeasons()) {
                    ISeason season = SeasonManager.getInstance().getSeasonById(seasonId);
                    choiceBoxSeason.getItems().add(season);
                }
            }
        }
        System.out.println("Showing choicebox wiht conttens " + choiceBoxSeason.getItems());
        choiceBoxSeason.show();
    }
    /**
     * Submits the episode of the show selected.
     *
     * @param Event
     * @throws IOException
     */
    @FXML
    protected void handleSendEpisodeButton(ActionEvent Event) throws IOException {
        IEpisode episode = EpisodeManager.getInstance().addEpisode(
            ((ISeason) choiceBoxSeason.getValue()),
            Integer.parseInt(episodeLength.getText()),
            episodeTitle.getText(),
            (Category) choiceBoxCategory.getValue()
        );
        JobManager.getInstance().addJob(episode.getProductionID());
    }

    /**
     * Create a new movie with the supplied values
     *
     * @param Event
     * @throws IOException
     */
    @FXML
    protected void handleSendMovieButton(ActionEvent Event) throws IOException{
        IMovie movie = MovieManager.getInstance().addMovie(
                movieTitle.getText(),
                movieDescription.getText(),
                new Category[]{(Category) choiceBoxCategoryMovie.getValue()},
                Integer.parseInt(movieLength.getText()),
                new Date()
                );
        JobManager.getInstance().addJob(movie.getIDMap().get("productionID"));
    }

    /**
     * Add a new person with values from the "Add Person" text field.
     *
     * @param Event
     * @throws IOException
     */
    @FXML
    protected void handleSendPersonButton(ActionEvent Event) throws IOException{
        PersonManager.getInstance().addPerson(
                personName.getText(),
                personDescription.getText(), //description
                personPhone.getText(),
                "", //personalinfo
                personEmail.getText());
    }

    /**
     *  Open a window for creating a show
     *
     * @param Event
     * @throws IOException
     */
    @FXML
    protected void handleCreateShow(ActionEvent Event) throws IOException {
        // Opens new window for adding person
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ApplicationManager.class.getClassLoader().getResource("CreateShow.fxml"));
        loader.setController(CreateShowController.getInstance());
        Scene scene = new Scene(loader.load());
        createShow = new Stage();
        createShow.setTitle("Opret Serie");
        createShow.setScene(scene);
        createShow.show();
    }

    public void disposeCreateShow() {
        createShow.close();
    }

    /**
     * Open window for creating a season for a show.
     *
     * @param Event from ActionEvent
     * @throws IOException
     *
     */
    @FXML
    protected void handleCreateSeason(ActionEvent Event) throws IOException {
        ICredit selectedCredit = (ICredit)  showSearchList.getSelectionModel().getSelectedItem();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ApplicationManager.class.getClassLoader().getResource("CreateSeason.fxml"));
        loader.setController(CreateSeasonController.getInstance());
        Scene scene = new Scene(loader.load());
        createSeason = new Stage();
        createSeason.setTitle("Opret Sæson til " + selectedCredit.toString());
        createSeason.setScene(scene);
        createSeason.show();
    }

    /**
     * disposes the createseason window
     */
    public void disposeCreateSeason() {
        createSeason.close();
    }


    protected void addSeason(String description) {
        getInstance().choiceBoxSeason.setValue(
                SeasonManager.getInstance().addSeason(description, ((IShow) showSearchList.getSelectionModel().getSelectedItem()))
        );
    }

    /**
     * Sets the name of the next episode.
     *
     * @param Event
     * @throws IOException
     */
    @FXML
    protected void handleGetEpisodeName(ActionEvent Event) throws IOException {
        reloadNextEpisode();
    }

    @FXML
    protected void handleAddCreditButton(ActionEvent Event) throws IOException {
        // Opens new window for adding person
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ApplicationManager.class.getClassLoader().getResource("AddPersonToCredit.fxml"));
        loader.setController(AddPersonController.getInstance());
        Scene scene = new Scene(loader.load());
        Stage stage = new Stage();
        stage.setTitle("Tilføj person");
        stage.setScene(scene);
        stage.show();
    }



    private void reloadNextEpisode() {
        episodeId.setText(EpisodeManager.getInstance().getNextEpisode(((ISeason) choiceBoxSeason.getValue()).getCreditID()));
    }

    public void handleRemovePersons(ActionEvent actionEvent) {
        JobManager.getInstance().clearTempJobs();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        PersonList = new ListView();
        PersonList.setPrefHeight(200);
        PersonList.setPrefWidth(493);
        moviePeoplePane.getChildren().add(PersonList);
        ObservableList<IJob> observableResults = JobManager.getInstance().getTempList();
        PersonList.setItems(observableResults);


        episodePersonList = new ListView();
        episodePersonList.setPrefHeight(200);
        episodePersonList.setPrefWidth(493);
        episodePeoplePane.getChildren().add(PersonList);
        ObservableList<IJob> episodeList = JobManager.getInstance().getTempList();
        PersonList.setItems(episodeList);

        showSearchList = new ListView();
        showSearchList.setPrefHeight(104);
        showSearchList.setPrefWidth(252);
        showSearchPane.getChildren().add(showSearchList);
        showList = FXCollections.observableArrayList();
        showSearchList.setItems(showList);

        EventHandler<KeyEvent> ev = new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (showSearch.getText().length() > 3){
                    showSearchList.getItems().clear();
                    ApplicationManager.getInstance().searchShow(showSearch.getText(), showList);
                }
            }
        };
        showSearch.setOnKeyPressed(ev);

        choiceBoxCategory.getItems().addAll(Category.values());
        choiceBoxCategoryMovie.getItems().addAll(Category.values());
    }
}
