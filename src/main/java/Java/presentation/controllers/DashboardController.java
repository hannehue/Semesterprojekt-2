package Java.presentation.controllers;

import Java.domain.ApplicationManager;
import Java.domain.data.Movie;
import Java.domain.data.Person;
import Java.domain.data.Show;
import Java.interfaces.*;
import Java.presentation.*;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import java.net.URL;
import java.util.ArrayList;
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

    private ObservableList<IPerson> personObservableList;
    private ObservableList<IMovie> movieObservableList;


    @Override
    public String toString() {
        return "DashboardController{" +
                "personToApprove=" + personToApprove +
                ", movieToApprove=" + movieToApprove +
                ", programToApprove=" + programToApprove +
                '}';
    }
    /* ------------------------------------------------------------------------------------------------------------------
        Metoder
    ------------------------------------------------------------------------------------------------------------------ */

    private static DashboardController instance = new DashboardController();

    private DashboardController(){
    }

    public static DashboardController getInstance() {
        return instance;
    }


    @FXML
    public void handleReloadProgram(MouseEvent mouseEvent) {
        reloadProgram();
    }

    protected void handleApprovePerson(int personId) {
        ApplicationManager.getInstance().setPersonApproved(personId);
    }

    protected void handleApproveMovie(int movieId) {
        ICredit approveCredit = null;
        for (IMovie movieCredit: movieObservableList) {
            if (movieCredit.getCreditID() == movieId) {
                movieObservableList.remove(movieCredit);
                movieCredit.setApproved(true);
                movieObservableList.add(movieCredit);
            }
        }
    }

    private void handleApproveCredit(int id, Class<? extends ICredit> credit) {
        if (Show.class.getTypeName().equals(credit.getTypeName())) {
            handleApproveShow(id);
        } else if (Movie.class.getTypeName().equals(credit.getTypeName())) {
            handleApproveMovie(id);
            System.out.println("got movie");
        } else if (Person.class.getTypeName().equals(credit.getTypeName())) {
            handleApprovePerson(id);
        }
    }

    protected void handleApproveShow(int Event) {
        for (IShow showCredit: ApplicationManager.getInstance().getShowList()) {
            if (showCredit.getCreditID() == Event) {
                showCredit.setApproved(true);
            }
        }
        reloadProgram();
    }

    protected void handleApproveSeason(int showId, int season) {
        for (IShow show: ApplicationManager.getInstance().getShowList()) {
            if (show.getCreditID() == showId) {
                for (ISeason s: show.getSeasons()) {
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
    }

    protected void handleApproveEpisode(int showId, int season, int episode) {
        for (IShow sh: ApplicationManager.getInstance().getShowList()) {
            if (sh.getCreditID() == showId) {
                for (ISeason s: sh.getSeasons()) {
                    if (s.getCreditID() == season) {
                        for (IEpisode e: s.getEpisodes()) {
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
    }

    private void addItem(AnchorPane listToApprove, ICredit credit, int offset){
        Pane personPane = new Pane();
        listToApprove.getChildren().add(personPane);
        personPane.setLayoutY(offset);
        personPane.setId(String.valueOf(credit.getCreditID()));

        Label personLabel = new Label("Name: " + credit.getName());
        personLabel.setLayoutX(20);
        personPane.getChildren().add(personLabel);


        Button approveButton = new Button();
        approveButton.setText("Godkend");
        approveButton.setLayoutX(300);
        personPane.getChildren().add(approveButton);
        int finalButtonCounter = credit.getCreditID();
        approveButton.setOnAction(actionEvent -> handleApproveCredit(finalButtonCounter, credit.getClass()));
    }

    private void removeItem(AnchorPane listToApprove, ICredit credit){
        for (Node children : listToApprove.getChildren()){
            if (children.getId() == String.valueOf(credit.getCreditID())){
                listToApprove.getChildren().remove(children);
            }
        }
    }

    public void setContent(AnchorPane listToApprove, ObservableList<? extends ICredit> creditList){
        listToApprove.getChildren().clear();

        System.out.println(creditList);
        int offset = 20;
        for (ICredit credit : creditList){
            addItem(listToApprove, credit, offset);
            offset += 30;
        }

        creditList.addListener(new ListChangeListener<ICredit>() {
            @Override
            public void onChanged(Change<? extends ICredit> change) {
                System.out.println("called onchange " + change);
                int offset = 20;
                while (change.next()) {
                    System.out.println("change next");
                    if (change.wasAdded()) {
                        System.out.println("was added " + change);
                        for (ICredit credit : change.getAddedSubList()){
                            if (!credit.isApproved()){
                                addItem(listToApprove, credit, offset);
                                offset += 30;
                            }
                        }
                    } else if (change.wasRemoved()) {
                        System.out.println("removed " + change);
                        for (ICredit credit : change.getRemoved()){
                            if (credit.isApproved()) {
                                removeItem(listToApprove, credit);
                            }
                        }
                    }
                }
            }
        });
    }

    private void reloadProgram() {
        programToApprove.getChildren().clear();

        int offset = 20;
        for (IShow show: ApplicationManager.getInstance().getShowList()) {
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
            } else if (!show.isAllSeasonApproved()) {
                for (ISeason season: show.getSeasons()) {
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
                    } else if (!season.isAllEpisodesApproved()) {
                        for (IEpisode episode: season.getEpisodes()) {
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        movieObservableList = ApplicationManager.getInstance().getMovies();
        //personObservableList = ApplicationManager.getInstance().getPersons();

        //setContent(personToApprove, personObservableList);
        setContent(movieToApprove, movieObservableList);
    }
}
