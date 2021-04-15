package Java.controllers;

import Java.Credit;
import Java.CreditSystemController;
import Java.Season;
import Java.Show;
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
    protected TextArea showDescription;
    @FXML
    protected ChoiceBox choiceBoxShow;
    @FXML
    protected TextArea seasonDescription;
    @FXML
    protected ChoiceBox choiceBoxSeason;


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
    protected void handleSendEpisodeButton(ActionEvent Event) throws IOException {


    }
    @FXML
    protected void handleCreateSeason(ActionEvent Event) throws IOException {
        Scene scene = new Scene(FXMLLoader.load(CreditSystemController.class.getClassLoader().getResource("CreateSeason.fxml")));
        Stage stage = new Stage();
        stage.setTitle("Opret Sæsson");
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    protected void handleAddSeason(ActionEvent Event) throws IOException {
        System.out.println(" før" + choiceBoxShow.getItems().toString());
        CreditSystemController.addSeason(seasonDescription.getText(), choiceBoxShow.getSelectionModel().getSelectedItem().toString());
        System.out.println(choiceBoxShow.getSelectionModel().getSelectedItem().toString());

    }
    @FXML
    protected void handleAddPersonEpisode(ActionEvent Event) throws IOException {


    }
    @FXML
    protected void handleCreateEpisode(ActionEvent Event) throws IOException {
    }

    public void handleGetShows(MouseEvent mouseEvent) {
        for (Show e: CreditSystemController.getShowList()){
            choiceBoxShow.getItems().add(e.getName());
            if (e.getSeasons() != null) {
            for (Season s: e.getSeasons()) {
                choiceBoxSeason.getItems().add(s.getSeasonName());
            }
            }
        }
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
                Pane Pane = new Pane();
                movieToApprove.getChildren().add(Pane);
                Pane.setLayoutY(offset);
                Pane.setId(String.valueOf(movieCredit.getCreditID()));

                Label Label = new Label("Name: " + movieCredit.getName());
                Label.setLayoutX(20);
                Pane.getChildren().add(Label);

                Button approveButton = new Button();
                approveButton.setText("Godkend");
                approveButton.setLayoutX(300);
                Pane.getChildren().add(approveButton);
                int finalButtonCounter = movieCredit.getCreditID();
                approveButton.setOnAction(actionEvent -> handleApproveMovie(finalButtonCounter));

                offset += 30;
            }
        }
    }
}
