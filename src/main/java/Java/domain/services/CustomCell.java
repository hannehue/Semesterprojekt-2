package Java.domain.services;

import Java.persistence.DatabaseLoaderFacade;
import Java.domain.ApplicationManager;
import Java.interfaces.ICredit;
import Java.interfaces.IJob;
import Java.interfaces.IMovie;
import Java.interfaces.IPerson;
import javafx.beans.value.ChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomCell<I> extends ListCell<ICredit> {
    private final Label name;
    private final GridPane pane;
    private GridPane movieRolePane;
    private final TextField itemName;
    private final TextArea itemDescription;
    private final Label itemJobs;
    private ComboBox<String> jobRoles;
    private ComboBox<String> movieList;
    private TextField characterName;
    private final Button Godkend;

    public CustomCell(){
        super();
        setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                System.out.println(getItem());
            }
        });
        setEditable(true);
        Button actionBtn = new Button("edit");
        actionBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                getListView().setEditable(true);
                startEdit();
            }
        });
        Button approveBtn = new Button("Approve");
        approveBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                System.out.println(getItem().getCreditID() + " + " + getItem().getName());
                ApplicationManager.getInstance().approveCredit(getItem().getCreditID(), getListView().getItems());
                getListView().getItems().remove(getItem());
            }
        });

        name = new Label();
        pane = new GridPane();


        itemName = new TextField();
        itemDescription = new TextArea();
        itemJobs = new Label();
        Godkend = new Button();
        Godkend.setText("Godkend");
        Godkend.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                //Kode til at gemme et nyt job
                System.out.println("NOT IMPLEMENTED YET");

                //reload edit
            }
        });


        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(85);
        pane.getColumnConstraints().addAll(col1);
        pane.add(name, 0,0);
        pane.add(actionBtn,2,0);
        if (getItem() != null) {
            if (!getItem().isApproved()) {
                pane.add(approveBtn, 3, 0);
            }
            if (getItem().isApproved()) {
                pane.getChildren().remove(approveBtn);
            }
        }
        setText(null);
    }
    @Override
    public void updateItem(ICredit credit, boolean empty){
        super.updateItem(credit, empty);
        if (!empty){
            name.setText(credit.buildView());
            pane.setId(String.valueOf(credit.getCreditID()));
            setGraphic(pane);
        } else {
            setGraphic(null);
        }
    }

    @Override
    public void startEdit(){
        if (!isEditable() || !getListView().isEditable()){
            System.out.println("not editable");
            return;
        }
        super.startEdit();
        if (isEditing()) {
            pane.getChildren().clear();
            //Hvis det er en person
            if (PersonManager.getInstance().searchPerson(getItem().getName()) != null) {
                for (IPerson person : PersonManager.getInstance().searchPerson(getItem().getName())) {
                    itemName.setPromptText(person.getName());
                    itemDescription.setPromptText(person.getDescription());
                    for (IJob iJob : person.getJobs()) {
                        if (iJob.getCharacterName() != null) {
                            itemJobs.setText(itemJobs.getText() + iJob.getRole() + "Spiller" + iJob.getCharacterName() + " på " + iJob.getProductionID() + "\n");
                        } else {
                            itemJobs.setText(itemJobs.getText() + iJob.getRole() + " på " + iJob.getProductionID() + "\n");
                        }
                    }
                    movieRolePane = new GridPane();
                    jobRoles = new ComboBox<>();
                    movieList = new ComboBox<String>();
                    ResultSet jobRoleResultSet = null;
                    ObservableList<IMovie> MovieList = null;


                    try {
                        jobRoleResultSet = DatabaseLoaderFacade.getInstance().getJobRoles();
                        MovieList = MovieManager.getInstance().searchMovie("");
                        while (jobRoleResultSet.next()) {
                            //set texten
                            jobRoles.getItems().add(jobRoleResultSet.getString(2));
                            //set id
                            jobRoles.idProperty().set(jobRoleResultSet.getString(1));
                        }
                        for (IMovie iMovie : MovieList) {
                            movieList.getItems().add(iMovie.getName());
                            movieList.idProperty().set(String.valueOf(iMovie.getProductionID()));
                        }

                    } catch (SQLException sqlException) {
                        sqlException.printStackTrace();
                    }
                }
            }

            characterName = new TextField();
            characterName.setVisible(false);

            jobRoles.valueProperty().addListener((ChangeListener<? super String>) (observableValue, o, t1) -> {
                if (jobRoles.getSelectionModel().getSelectedItem().toString().equals("Skuespiller")) {
                    characterName.setVisible(true);
                }
            });

            itemName.setText(null);
            itemDescription.setText(null);
            pane.add(itemName, 0, 0);
            pane.add(itemDescription, 0, 1);
            movieRolePane.add(jobRoles, 0, 0);
            movieRolePane.add(movieList, 1, 0);
            movieRolePane.add(characterName, 0, 1);
            movieRolePane.add(Godkend, 1, 1);
            pane.add(movieRolePane, 0, 2);
            pane.add(itemJobs, 0, 3);
            setGraphic(pane);
        }
        getListView().setEditable(false);
    }

    @Override
    public void commitEdit(ICredit credit) {
        super.commitEdit(credit);

    }
}