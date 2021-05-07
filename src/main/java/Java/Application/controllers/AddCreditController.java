package Java.Application.controllers;

import Java.Application.CreditSystemController;
import Java.Application.IShow;
import Java.Application.Season;
import Java.Application.Show;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
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
    protected TextArea personsToCreditM;
    @FXML
    protected TextArea personsToCreditE;

    private static Stage createShow;
    private static Stage createSeason;

    private static String showName;
    private static String seasonName;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }





    public static void setShowName(String showname) {
        showName = showname;
    }

    public static String getShowName() {
        return showName;
    }

    public static void setSeasonName(String seasonname){
        seasonName = seasonname;
    }

    public static String getSeasonName() {
        return seasonName;
    }

    public void handleGetShows(MouseEvent mouseEvent) {
        choiceBoxShow.getItems().clear();
        choiceBoxSeason.getItems().clear();
        for (IShow show : CreditSystemController.getShowList()) {
            choiceBoxShow.getItems().add(show.getName());
        }
        choiceBoxShow.show();
    }

    @FXML
    protected void handleSendEpisodeButton(ActionEvent Event) throws IOException {
        System.out.println("trying to add episode to" + showName + " . " + seasonName);
        int id = CreditSystemController.nextId();
        CreditSystemController.addEpisode(episodeTitle.getText(), Integer.parseInt(episodeLength.getText()), showName, seasonName, id);
        reloadNextEpisode();
        CreditSystemController.addJob(id);
    }


    @FXML
    protected void handleSendMovieButton(ActionEvent Event) throws IOException{
        int id = CreditSystemController.nextId();
        CreditSystemController.addMovie(
                movieTitle.getText(),
                movieDescription.getText(),
                null,
                id,
                Integer.parseInt(movieLength.getText())
                );
        CreditSystemController.addJob(id);
    }
    @FXML
    protected void handleSendPersonButton(ActionEvent Event) throws IOException{
        CreditSystemController.addPerson(
                personName.getText(),
                null,
                personPhone.getText(),
                personEmail.getText());
    }

    @FXML
    protected void handleCreateShow(ActionEvent Event) throws IOException {
        // Opens new window for adding person
        Scene scene = new Scene(FXMLLoader.load(CreditSystemController.class.getClassLoader().getResource("CreateShow.fxml")));
        createShow = new Stage();
        createShow.setTitle("Opret Serie");
        createShow.setScene(scene);
        createShow.show();
    }

    @FXML
    protected void handleCreateSeason(ActionEvent Event) throws IOException {
        Scene scene = new Scene(FXMLLoader.load(CreditSystemController.class.getClassLoader().getResource("CreateSeason.fxml")));
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
            for (IShow show : CreditSystemController.getShowList()) {
                if (show.getName() == showName) {
                    if (show.getSeasons() != null) {
                        for (Season season : show.getSeasons()) {
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
        Scene scene = new Scene(FXMLLoader.load(CreditSystemController.class.getClassLoader().getResource("AddPersonToCredit.fxml")));
        Stage stage = new Stage();
        stage.setTitle("Tilføj person");
        stage.setScene(scene);
        stage.show();
    }

    public static void disposeCreateShow() {
        createShow.close();
    }

    public static void disposeCreateSeason() {
        createSeason.close();
    }

    private void reloadNextEpisode() {
        episodeId.setText(CreditSystemController.getNextEpisode(showName, seasonName));
    }

    public void handleReloadPersonToMovie(ActionEvent actionEvent) {
        personsToCreditM.setText(CreditSystemController.tempListToString());
        personsToCreditE.setText(CreditSystemController.tempListToString());
        System.out.println(CreditSystemController.tempListToString());
    }

    public void handleRemovePersons(ActionEvent actionEvent) {
        CreditSystemController.deleteTempList();
        personsToCreditM.setText(CreditSystemController.tempListToString());
        personsToCreditE.setText(CreditSystemController.tempListToString());
    }
}
