package Java.presentation.controllers;

import Java.domain.ApplicationManager;
import Java.domain.data.*;
import Java.interfaces.*;
import Java.presentation.*;
import javafx.collections.FXCollections;
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
    protected AnchorPane showToApprove;
    @FXML
    protected AnchorPane seasonToApprove;
    @FXML
    protected AnchorPane episodeToApprove;

    private ObservableList<IPerson> personObservableList;
    private ObservableList<IMovie> movieObservableList;
    private ObservableList<IShow> showObservableList;
    private ObservableList<ISeason> seasonObservableList = FXCollections.observableArrayList();
    private ObservableList<IEpisode> episodeObservableList = FXCollections.observableArrayList();


    @Override
    public String toString() {
        return "DashboardController{" +
                "personToApprove=" + personToApprove +
                ", movieToApprove=" + movieToApprove +
                ", programToApprove=" + showToApprove +
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

    /**
     * Aprrove some credit with an id, from a list.
     * @param id
     * @param observableList
     * @param <T>
     */
    protected <T extends ICredit> void approveCredit(int id, ObservableList<T> observableList) {
        T approveCredit = null;
        for (T credit: observableList) {
            if (credit.getCreditID() == id) {
                approveCredit = credit;
            }
        }
        if (approveCredit != null){
            observableList.remove(approveCredit);
            approveCredit.setApproved(true);
            observableList.add(approveCredit);
        }
    }

    private void handleApproveCredit(int id, Class<? extends ICredit> credit) {
        if (Show.class.getTypeName().equals(credit.getTypeName())) {
            approveCredit(id, showObservableList);
        } else if (Movie.class.getTypeName().equals(credit.getTypeName())) {
            approveCredit(id, movieObservableList);
        } else if (Person.class.getTypeName().equals(credit.getTypeName())) {
            approveCredit(id, personObservableList);
        } else if (Season.class.getTypeName().equals(credit.getTypeName())) {
            handleApproveSeason(id);
        } else if (Episode.class.getTypeName().equals(credit.getTypeName())) {
            handleApproveEpisode(id);
        }
    }

    /**
     * handle the approval of seasons
     * @param season
     */
    protected void handleApproveSeason(int season) {
        for (IShow show: showObservableList) {
            for (ISeason s: show.getSeasons()) {
                if (s.getCreditID() == season) {
                    show.getSeasons().remove(s);
                    s.setApproved(true);
                    show.getSeasons().add(s);
                }
            }
        }
    }

    /**
     * handle the approval of episodes
     * @param episode
     */
    protected void handleApproveEpisode(int episode) {
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
        Node removechild = null;
        for (Node child: listToApprove.getChildren()){
            if (Integer.parseInt(child.getId()) == credit.getCreditID()){
                removechild = child;
            }
        }
        if(removechild != null){
            listToApprove.getChildren().remove(removechild);
        }
    }

    public void setContent(AnchorPane listToApprove, ObservableList<? extends ICredit> creditList){
        int offset = 20;
        for (ICredit credit : creditList){
            if (!credit.isApproved()) {
                addItem(listToApprove, credit, offset);
                offset += 30;
            }
        }

        creditList.addListener(new ListChangeListener<ICredit>() {
            @Override
            public void onChanged(Change<? extends ICredit> change) {
                int offset = 20;
                while (change.next()) {
                    if (change.wasAdded()) {
                        for (ICredit credit : change.getAddedSubList()){
                            if (!credit.isApproved()){
                                addItem(listToApprove, credit, offset);
                                offset += 30;
                            }
                        }
                    } else if (change.wasRemoved()) {
                        for (ICredit credit : change.getRemoved()){
                            System.out.println("Removed credit: " + credit);
                            removeItem(listToApprove, credit);
                        }
                    }
                }
            }
        });
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        movieObservableList = ApplicationManager.getInstance().getMovies();
        setContent(movieToApprove, movieObservableList);
        personObservableList = ApplicationManager.getInstance().getPersons();
        setContent(personToApprove, personObservableList);
        showObservableList = ApplicationManager.getInstance().getShowList();
        setContent(showToApprove, showObservableList);
        setContent(seasonToApprove, seasonObservableList);
        setContent(episodeToApprove, episodeObservableList);
        for (IShow show : ApplicationManager.getInstance().getShowList()) {
            ObservableList<ISeason> seasons = show.getSeasons();
            for (ISeason season : seasons) {
                ObservableList<IEpisode>  episodes = season.getEpisodes();
                episodeObservableList.addAll(episodes);
            }
            seasonObservableList.addAll(seasons);
        }
    }
}
