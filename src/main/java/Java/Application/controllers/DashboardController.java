package Java.controllers;

import Java.Application.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
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
    protected AnchorPane programToApprove;

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
        for (Show show: CreditSystemController.getShowList()) {
            if (show.getCreditID() == showId) {
                for (Season s: show.getSeasons()) {
                    if (s.getCreditID() == season) {
                        s.setApproved(true);
                    }
                    show.setAllSeasonApproved(true);
                    if (!show.isApproved() || !s.isAllEpisodesApproved()) {
                        show.setAllSeasonApproved(false);
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
                            s.setAllEpisodeApproved(true);
                            if (!e.isApproved()) {
                                s.setAllEpisodeApproved(false);
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
                System.out.println(1);
            } else if (!show.isAllSeasonApproved()) {
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
                        System.out.println(2);
                    } else if (!season.isAllEpisodesApproved()) {
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
                                System.out.println(3);
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
