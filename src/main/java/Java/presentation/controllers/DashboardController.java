package Java.presentation.controllers;

import Java.data.DatabaseLoaderFacade;
import Java.domain.ApplicationManager;
import Java.domain.data.*;
import Java.domain.objectMapping.Factory;
import Java.domain.services.*;
import Java.interfaces.*;
import javafx.collections.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import java.net.URL;
import java.sql.SQLException;
import java.util.Map;
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
    private ObservableList<ISeason> seasonObservableList;
    private ObservableList<IEpisode> episodeObservableList;


    /* ------------------------------------------------------------------------------------------------------------------
       Metoder
       ------------------------------------------------------------------------------------------------------------------ */

    private static final DashboardController instance = new DashboardController();

    private DashboardController(){
    }

    public static DashboardController getInstance() {
        return instance;
    }


    private void handleApproveCredit(int id, Class<? extends ICredit> credit) {
        personObservableList.clear();
        movieObservableList.clear();
        showObservableList.clear();
        seasonObservableList.clear();
        episodeObservableList.clear();
        if (Show.class.getTypeName().equals(credit.getTypeName())) {
            ApplicationManager.getInstance().approveCredit(id, showObservableList);
        } else if (Movie.class.getTypeName().equals(credit.getTypeName())) {
            ApplicationManager.getInstance().approveCredit(id, movieObservableList);
        } else if (Person.class.getTypeName().equals(credit.getTypeName())) {
            ApplicationManager.getInstance().approveCredit(id, personObservableList);
        } else if (Season.class.getTypeName().equals(credit.getTypeName())) {
            ApplicationManager.getInstance().approveCredit(id, seasonObservableList);
        } else if (Episode.class.getTypeName().equals(credit.getTypeName())) {
            ApplicationManager.getInstance().approveCredit(id, episodeObservableList);
        }
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

    private void setContent(AnchorPane listToApprove, ObservableList<? extends ICredit> creditList){
        int offset = 20;
        for (ICredit credit : creditList){
            if (!credit.isApproved()) {
                addItem(listToApprove, credit, offset);
                offset += 30;
            }
        }

        creditList.addListener((ListChangeListener<ICredit>) change -> {
            int offset1 = 20;
            while (change.next()) {
                if (change.wasAdded()) {
                    for (ICredit credit : change.getAddedSubList()){
                        if (!credit.isApproved()){
                            addItem(listToApprove, credit, offset1);
                            offset1 += 30;
                        }
                    }
                } else if (change.wasRemoved()) {
                    for (ICredit credit : change.getRemoved()){
                        System.out.println("Removed credit: " + credit);
                        removeItem(listToApprove, credit);
                    }
                }
            }
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Factory factory = Factory.getInstance();
        factory.getAllUnapprovedCredits();
        movieObservableList = MovieManager.getInstance().getMovies();
        setContent(movieToApprove, movieObservableList);
        personObservableList = PersonManager.getInstance().getPersonList();
        setContent(personToApprove, personObservableList);
        showObservableList = ShowManager.getInstance().getShowList();
        setContent(showToApprove, showObservableList);

        seasonObservableList = SeasonManager.getInstance().getSeasonList();
        episodeObservableList = EpisodeManager.getInstance().getEpisodeList();
        setContent(seasonToApprove, seasonObservableList);
        setContent(episodeToApprove, episodeObservableList);
    }
}
