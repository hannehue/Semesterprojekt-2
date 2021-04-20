package Java.controllers;

import Java.*;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

/* ------------------------------------------------------------------------------------------------------------------
Denne Controller alt funktionalitet på de forskellige sider
------------------------------------------------------------------------------------------------------------------ */

public class DashboardController implements Initializable {
    @FXML
    protected AnchorPane personToApprove;
    @FXML
    protected AnchorPane movieToApprove;
    @FXML
    protected AnchorPane programToApprove;
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
    protected TextField showTitle;
    @FXML
    protected TextField episodeTitle;
    @FXML
    protected TextField episodeLength;
    @FXML
    protected TextField episodeId;
    @FXML
    protected TextArea showDescription;
    @FXML
    protected TextArea seasonDescription;
    @FXML
    protected ChoiceBox choiceBoxShow;
    @FXML
    protected ChoiceBox choiceBoxSeason;



    protected static String showName;
    protected static String seasonName;
    protected HashMap<String, Person> castMovie;
    protected HashMap<Role, ArrayList<Person>> productionTeamMovie;

    public void setCastMovie(HashMap<String, Person> castMovie) {
        this.castMovie = castMovie;
    }

    public void setProductionTeamMovie(HashMap<Role, ArrayList<Person>> productionTeamMovie) {
        this.productionTeamMovie = productionTeamMovie;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

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
    @FXML
    protected void handleSendMovieButton(ActionEvent Event) throws IOException{
        CreditSystemController.addMovie(
                movieTitle.getText(),
                movieDescription.getText(),
                Integer.parseInt(movieLength.getText()));
    }
    @FXML
    protected void handleSendPersonButton(ActionEvent Event) throws IOException{
        CreditSystemController.addPerson(
                personName.getText(),
                null,
                personPhone.getText(),
                personEmail.getText());
    }

    // Programmer
    @FXML
    protected void handleCreateShow(ActionEvent Event) throws IOException {
        // Opens new window for adding person
        Scene scene = new Scene(FXMLLoader.load(CreditSystemController.class.getClassLoader().getResource("CreateShow.fxml")));
        Stage stage = new Stage();
        stage.setTitle("Opret Serie");
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    protected void handleAddShow(ActionEvent Event) throws IOException {
        CreditSystemController.addShow(
                showTitle.getText(),
                showDescription.getText()
        );
    }

    @FXML
    protected void handleCreateSeason(ActionEvent Event) throws IOException {
        Scene scene = new Scene(FXMLLoader.load(CreditSystemController.class.getClassLoader().getResource("CreateSeason.fxml")));
        Stage stage = new Stage();
        stage.setTitle("Opret Sæsson til " + showName);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    protected void handleAddSeason(ActionEvent Event) throws IOException {
        System.out.println("trying to add season with showname: " + showName);
        CreditSystemController.addSeason(seasonDescription.getText(), showName);
    }


    @FXML
    protected void handleAddPersonEpisode(ActionEvent Event) throws IOException {

    }
    @FXML
    protected void handleGetEpisodeName(ActionEvent Event) throws IOException {
        reloadNextEpisode();
    }


    @FXML
    public void handleSetShows(ActionEvent actionEvent) {
        if (!choiceBoxShow.getSelectionModel().isEmpty()){
            showName = choiceBoxShow.getValue().toString();
        }
        choiceBoxShow.setValue(showName);
    }

    public void handleGetShows(MouseEvent mouseEvent) {
        choiceBoxShow.getItems().clear();
        choiceBoxSeason.getItems().clear();
        for (Show show: CreditSystemController.getShowList()){
            choiceBoxShow.getItems().add(show.getName());
        }
        choiceBoxShow.show();
    }

    @FXML
    public void handleSetSeason(ActionEvent actionEvent) {
        if (! choiceBoxSeason.getSelectionModel().isEmpty()){
            seasonName = choiceBoxSeason.getValue().toString();
            System.out.println("setSeason: " + seasonName);
        }
        choiceBoxSeason.setValue(seasonName);
    }

    public void handleGetSeason(MouseEvent Event) {
        choiceBoxSeason.getItems().clear();
        if (showName != null) {
            for (Show show: CreditSystemController.getShowList()) {
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
    protected void handleSendEpisodeButton(ActionEvent Event) throws IOException {
        System.out.println("trying to add episode to" + showName + " . " + seasonName);
        CreditSystemController.addEpisode(episodeTitle.getText(), Integer.parseInt(episodeLength.getText()), showName, seasonName );
        reloadNextEpisode();
    }







    /* ------------------------------------------------------------------------------------------------------------------
        Metoder
    ------------------------------------------------------------------------------------------------------------------ */
    @FXML
    public void handleReloadPerson(MouseEvent mouseEvent) {
        reloadPerson();
    }
    @FXML
    public void handleReloadMovie(MouseEvent mouseEvent) {
        reloadMovie();
    }
    @FXML
    public void handleReloadProgram(MouseEvent mouseEvent) {
        reloadProgram();
    }

    protected EventHandler<ActionEvent> handleApprovePerson(int Event) {
        System.out.println("før");
        for (Credit personCredit: CreditSystemController.getPersonList()) {
            if (personCredit.getCreditID() == Event) {
                personCredit.setApproved(true);
            }
        }
        reloadPerson();
        System.out.println("efter" + Event);
        return null;
    }

    protected EventHandler<ActionEvent> handleApproveMovie(int Event) {
        System.out.println("før");
        for (Credit movieCredit: CreditSystemController.getMovieList()) {
            if (movieCredit.getCreditID() == Event) {
                movieCredit.setApproved(true);
            }
        }
        reloadMovie();
        System.out.println("efter" + Event);
        return null;
    }

    protected EventHandler<ActionEvent> handleApproveShow(int Event) {
        for (Show showCredit: CreditSystemController.getShowList()) {
            if (showCredit.getCreditID() == Event) {
                showCredit.setApproved(true);
            }
        }
        reloadProgram();
        return null;
    }

    protected EventHandler<ActionEvent> handleApproveSeason(int showId, int season) {
        for (Show showCredit: CreditSystemController.getShowList()) {
            if (showCredit.getCreditID() == showId) {
                for (Season s: showCredit.getSeasons()) {
                    if (s.getCreditID() == season) {
                        s.setApproved(true);
                    }
                }
            }
        }
        reloadProgram();
        return null;
    }

    protected EventHandler<ActionEvent> handleApproveEpisode(int showId, int season, int episode) {
        for (Show sh: CreditSystemController.getShowList()) {
            if (sh.getCreditID() == showId) {
                for (Season s: sh.getSeasons()) {
                    if (s.getCreditID() == season) {
                        for (Episode e: s.getEpisodes()) {
                            if (e.getCreditID() == episode) {
                                e.setApproved(true);
                            }
                        }
                    }
                }
            }
        }
        reloadProgram();
        return null;

    }




    private void reloadPerson() {
        personToApprove.getChildren().clear();

        int offset = 20;
        for (Credit personCredit: CreditSystemController.getPersonList()) {
            if (!personCredit.isApproved()) {
                Pane personPane = new Pane();
                personToApprove.getChildren().add(personPane);
                personPane.setLayoutY(offset);
                personPane.setId(String.valueOf(personCredit.getCreditID()));

                Label personLabel = new Label("Name: " + personCredit.getName());
                personLabel.setLayoutX(20);
                personPane.getChildren().add(personLabel);


                Button approveButton = new Button();
                approveButton.setText("Godkend");
                approveButton.setLayoutX(300);
                personPane.getChildren().add(approveButton);
                int finalButtonCounter = personCredit.getCreditID();
                approveButton.setOnAction(actionEvent -> handleApprovePerson(finalButtonCounter));

                offset += 30;
            }
        }
    }

    private void reloadMovie() {
        movieToApprove.getChildren().clear();

        int offset = 20;
        for (Credit movieCredit: CreditSystemController.getMovieList()) {
            if (!movieCredit.isApproved()) {
                Pane pane = new Pane();
                movieToApprove.getChildren().add(pane);
                pane.setLayoutY(offset);
                pane.setId(String.valueOf(movieCredit.getCreditID()));

                Label label = new Label("Name: " + movieCredit.getName());
                label.setLayoutX(20);
                pane.getChildren().add(label);

                Button approveButton = new Button();
                approveButton.setText("Godkend");
                approveButton.setLayoutX(300);
                pane.getChildren().add(approveButton);
                int finalButtonCounter = movieCredit.getCreditID();
                approveButton.setOnAction(actionEvent -> handleApproveMovie(finalButtonCounter));

                offset += 30;
            }
        }
    }

    private void reloadProgram() {
        programToApprove.getChildren().clear();;

        int offset = 20;
        for (Show show: CreditSystemController.getShowList()) {
            if (!show.isApproved()) {
                Pane pane = new Pane();
                programToApprove.getChildren().add(pane);
                pane.setLayoutY(offset);

                Label label = new Label("Title: " + show.getName());
                label.setLayoutX(20);
                pane.getChildren().add(label);

                Button approveButton = new Button();
                approveButton.setText("Godkend");
                approveButton.setLayoutX(300);
                pane.getChildren().add(approveButton);
                int finalButtonCounter = show.getCreditID();
                approveButton.setOnAction(actionEvent -> handleApproveShow(finalButtonCounter));

                offset += 30;
            } else if (true) {
                for (Season season: show.getSeasons()) {
                    if (!season.isApproved()) {
                        Pane pane = new Pane();
                        programToApprove.getChildren().add(pane);
                        pane.setLayoutY(offset);

                        Label label = new Label("Title: " + show.getName() + " - Sæsson: " + season.getName());
                        label.setLayoutX(20);
                        pane.getChildren().add(label);

                        Button approveButton = new Button();
                        approveButton.setText("Godkend");
                        approveButton.setLayoutX(300);
                        pane.getChildren().add(approveButton);
                        int showID = show.getCreditID();
                        int finalButtonCounter = season.getCreditID();
                        approveButton.setOnAction(actionEvent -> handleApproveSeason(showID, finalButtonCounter));

                        offset += 30;
                    } else if (true) {
                        for (Episode episode: season.getEpisodes()) {
                            if (!episode.isApproved()) {
                                Pane pane = new Pane();
                                programToApprove.getChildren().add(pane);
                                pane.setLayoutY(offset);

                                Label label = new Label("Title: " + show.getName() + " - Episode: " + episode.getName());
                                label.setLayoutX(20);
                                pane.getChildren().add(label);

                                Button approveButton = new Button();
                                approveButton.setText("Godkend");
                                approveButton.setLayoutX(300);
                                pane.getChildren().add(approveButton);
                                int showID = show.getCreditID();
                                int seasonId = season.getCreditID();
                                int finalButtonCounter = episode.getCreditID();
                                approveButton.setOnAction(actionEvent -> handleApproveEpisode(showID, seasonId, finalButtonCounter));

                                offset += 30;
                            }
                        }
                    }
                }
            }
        }
    }
}
