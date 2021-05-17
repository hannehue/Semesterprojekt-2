package Java.presentation.controllers;

import Java.domain.ApplicationManager;
import Java.domain.services.*;
import Java.interfaces.IJob;
import Java.interfaces.ISeason;
import Java.interfaces.IShow;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddCreditController implements Initializable {
    @FXML
    protected ChoiceBox choiceBoxShow;
    @FXML
    protected ChoiceBox choiceBoxSeason;
    @FXML
    protected TextField personName;
    @FXML
    protected TextField personEmail;
    @FXML
    protected TextField personPhone;
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

    private ListView PersonList;
    private ListView episodePersonList;

    private Stage createShow;
    private Stage createSeason;

    private static AddCreditController instance = new AddCreditController();

    private AddCreditController() {
    }

    public static AddCreditController getInstance() {
        return instance;
    }

    /**
     * Displays the choices in the choicebox show
     *
     * @param mouseEvent
     */
    public void handleGetShows(MouseEvent mouseEvent) {
        choiceBoxShow.getItems().clear();
        choiceBoxSeason.getItems().clear();
        for (IShow show : ShowManager.getInstance().getAllShows()) {
            choiceBoxShow.getItems().add(show);
        }
        choiceBoxShow.show();
    }

    /**
     *
     * @param Event mouseevent from FXML
     *
     *      Populates the season choicebox with the seasons from the selected show
     */
    public void handleGetSeason(MouseEvent Event) {
        choiceBoxSeason.getItems().clear();
        if (choiceBoxShow.getValue() != null) {
            IShow show = ShowManager.getInstance().searchShowName(choiceBoxShow.getValue().toString());
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
        int id = ApplicationManager.getInstance().nextId();
        EpisodeManager.getInstance().addEpisode(
                episodeTitle.getText(),
                Integer.parseInt(episodeLength.getText()),
                ((ISeason) choiceBoxSeason.getValue()).getCreditID(),
                id);
        JobManager.getInstance().addJob(id);
    }

    /**
     * Create a new movie with the supplied values
     *
     * @param Event
     * @throws IOException
     */
    @FXML
    protected void handleSendMovieButton(ActionEvent Event) throws IOException{
        int id = ApplicationManager.getInstance().nextId();
        MovieManager.getInstance().addMovie(
                movieTitle.getText(),
                movieDescription.getText(),
                null,
                id,
                Integer.parseInt(movieLength.getText())
                );
        JobManager.getInstance().addJob(id);
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
                null,
                personPhone.getText(),
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
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ApplicationManager.class.getClassLoader().getResource("CreateSeason.fxml"));
        loader.setController(CreateSeasonController.getInstance());
        Scene scene = new Scene(loader.load());
        createSeason = new Stage();
        createSeason.setTitle("Opret Sæson til " + choiceBoxShow.getValue().toString());
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
        SeasonManager.getInstance().addSeason(description, ((IShow) choiceBoxShow.getValue()).getCreditID());
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

    }
}
