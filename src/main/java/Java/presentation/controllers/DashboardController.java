package Java.presentation.controllers;

import Java.domain.ApplicationManager;
import Java.domain.data.*;
import Java.interfaces.*;
import Java.presentation.*;
import javafx.collections.*;
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
    private ObservableMap<Integer, ISeason> seasonObservableMap = FXCollections.observableHashMap();
    private ObservableMap<Integer, IEpisode> episodeObservableMap = FXCollections.observableHashMap();


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


    private void handleApproveCredit(int id, Class<? extends ICredit> credit) {
        if (Show.class.getTypeName().equals(credit.getTypeName())) {
            ApplicationManager.getInstance().approveCredit(id, showObservableList);
        } else if (Movie.class.getTypeName().equals(credit.getTypeName())) {
            ApplicationManager.getInstance().approveCredit(id, movieObservableList);
        } else if (Person.class.getTypeName().equals(credit.getTypeName())) {
            ApplicationManager.getInstance().approveCredit(id, personObservableList);
        } else if (Season.class.getTypeName().equals(credit.getTypeName())) {
            ApplicationManager.getInstance().approveSeason(id);
        } else if (Episode.class.getTypeName().equals(credit.getTypeName())) {
            ApplicationManager.getInstance().approveEpisode(id);
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

    public void setContent(AnchorPane listToApprove, ObservableList<? extends ICredit> creditList){
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
    public <T extends ICredit> void setContent(AnchorPane listToApprove, ObservableMap<Integer, T> creditList){
        int offset = 20;
        for (Map.Entry<Integer, T> credit : creditList.entrySet()){
            if (!credit.getValue().isApproved()) {
                addItem(listToApprove, credit.getValue(), offset);
                offset += 30;
            }
        }

        creditList.addListener(new MapChangeListener<Integer, T>() {
            @Override
            public void onChanged(Change<? extends Integer, ? extends T> change) {
                int offset = 20;
                System.out.println("change in map");
                if (change.wasAdded()) {
                    System.out.println("change add");
                    ICredit credit = change.getValueAdded();
                    if (!credit.isApproved()){
                        addItem(listToApprove, credit, offset);
                        offset += 30;
                    }
                } else if (change.wasRemoved()) {
                    System.out.println("change removed");
                    ICredit credit = change.getValueRemoved();
                    if (!credit.isApproved()){
                        removeItem(listToApprove, credit);
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

        seasonObservableMap = ApplicationManager.getInstance().getSeasonMap();
        episodeObservableMap = ApplicationManager.getInstance().getEpisodeMap();
        setContent(seasonToApprove, seasonObservableMap);
        setContent(episodeToApprove, episodeObservableMap);
    }
}
