package Java.presentation.controllers;

import Java.domain.ApplicationManager;
import Java.interfaces.ISeason;
import Java.interfaces.IShow;
import Java.presentation.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class AddCreditController {
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
    protected TextArea personsToCreditM;
    @FXML
    protected TextArea personsToCreditE;

    private Stage createShow;
    private Stage createSeason;

    private String showName;
    private String seasonName;

    private static AddCreditController instance = new AddCreditController();

    private AddCreditController() {
    }

    public static AddCreditController getInstance() {
        return instance;
    }

    public void setShowName(String showname) {
        showName = showname;
    }

    public String getShowName() {
        return showName;
    }

    public void setSeasonName(String seasonname){
        seasonName = seasonname;
    }

    public String getSeasonName() {
        return seasonName;
    }

    public void handleGetShows(MouseEvent mouseEvent) {
        choiceBoxShow.getItems().clear();
        choiceBoxSeason.getItems().clear();
        for (IShow show : ApplicationManager.getInstance().getShowList()) {
            choiceBoxShow.getItems().add(show.getName());
        }
        choiceBoxShow.show();
    }

    @FXML
    protected void handleSendEpisodeButton(ActionEvent Event) throws IOException {
        System.out.println("trying to add episode to" + showName + " . " + seasonName);
        int id = ApplicationManager.getInstance().nextId();
        ApplicationManager.getInstance().addEpisode(episodeTitle.getText(), Integer.parseInt(episodeLength.getText()), showName, seasonName, id);
        reloadNextEpisode();
        ApplicationManager.getInstance().addJob(id);
    }


    @FXML
    protected void handleSendMovieButton(ActionEvent Event) throws IOException{
        int id = ApplicationManager.getInstance().nextId();
        ApplicationManager.getInstance().addMovie(
                movieTitle.getText(),
                movieDescription.getText(),
                null,
                id,
                Integer.parseInt(movieLength.getText())
                );
        ApplicationManager.getInstance().addJob(id);
    }
    @FXML
    protected void handleSendPersonButton(ActionEvent Event) throws IOException{
        ApplicationManager.getInstance().addPerson(
                personName.getText(),
                null,
                personPhone.getText(),
                personEmail.getText());
    }

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

    @FXML
    protected void handleCreateSeason(ActionEvent Event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ApplicationManager.class.getClassLoader().getResource("CreateSeason.fxml"));
        loader.setController(CreateSeasonController.getInstance());
        Scene scene = new Scene(loader.load());
        createSeason = new Stage();
        createSeason.setTitle("Opret Sæsson til " + showName);
        createSeason.setScene(scene);
        createSeason.show();
    }

    @FXML
    public void handleSetShows(ActionEvent actionEvent) {
        if (!choiceBoxShow.getSelectionModel().isEmpty()) {
            showName = choiceBoxShow.getValue().toString();
        }
        choiceBoxShow.setValue(showName);
    }

    @FXML
    public void handleSetSeason(ActionEvent actionEvent) {
        if (!choiceBoxSeason.getSelectionModel().isEmpty()) {
            seasonName = choiceBoxSeason.getValue().toString();
            System.out.println("setSeason: " + seasonName);
        }
        choiceBoxSeason.setValue(seasonName);
    }

    public void handleGetSeason(MouseEvent Event) {
        choiceBoxSeason.getItems().clear();
        if (showName != null) {
            for (IShow show : ApplicationManager.getInstance().getShowList()) {
                if (show.getName() == showName) {
                    if (show.getSeasons() != null) {
                        for (ISeason season : show.getSeasons()) {
                            choiceBoxSeason.getItems().add(season.getName());
                        }
                    }
                }
            }
        }
        System.out.println("Showing choicebox wiht conttens " + choiceBoxSeason.getItems());
        choiceBoxSeason.show();
    }


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

    public void disposeCreateShow() {
        createShow.close();
    }

    public void disposeCreateSeason() {
        createSeason.close();
    }

    private void reloadNextEpisode() {
        episodeId.setText(ApplicationManager.getInstance().getNextEpisode(showName, seasonName));
    }

    public void handleReloadPersonToMovie(ActionEvent actionEvent) {
        personsToCreditM.setText(ApplicationManager.getInstance().tempListToString());
        personsToCreditE.setText(ApplicationManager.getInstance().tempListToString());
        System.out.println(ApplicationManager.getInstance().tempListToString());
    }

    public void handleRemovePersons(ActionEvent actionEvent) {
        ApplicationManager.getInstance().deleteTempList();
        personsToCreditM.setText(ApplicationManager.getInstance().tempListToString());
        personsToCreditE.setText(ApplicationManager.getInstance().tempListToString());
    }

}
