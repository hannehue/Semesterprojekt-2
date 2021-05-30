package Java.presentation.controllers;

import Java.data.DatabaseLoaderFacade;
import Java.domain.ApplicationManager;
import Java.domain.data.Category;
import Java.domain.data.Job;
import Java.domain.data.Role;
import Java.domain.objectMapping.CustomCellFactory;
import Java.domain.services.*;
import Java.interfaces.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.util.StringConverter;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;

public class CreditOverlookController implements Initializable {

    private static CreditOverlookController instance = new CreditOverlookController();
    private CreditOverlookController() { }
    public static CreditOverlookController getInstance() {
        return instance;
    }


    @FXML
    protected ListView itemView;
    @FXML
    protected AnchorPane itemEdit;

    @FXML
    protected RadioButton FilterMovieButton;
    @FXML
    protected RadioButton FilterShowButton;
    @FXML
    protected RadioButton FilterPersonButton;
    @FXML
    protected RadioButton FilterAllButton;

    @FXML
    protected ComboBox ApprovalBox;


    ToggleGroup toggleGroup = new ToggleGroup();
    String searchString = "";

    private ObservableList<IPerson> personObservableList;
    private ObservableList<IMovie> movieObservableList;
    private ObservableList<IShow> showObservableList;
    //private ObservableList<ISeason> seasonObservableList;
    //private ObservableList<IEpisode> episodeObservableList;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ApprovalBox.getSelectionModel().selectFirst();
        FilterAllButton.setToggleGroup(toggleGroup);
        FilterPersonButton.setToggleGroup(toggleGroup);
        FilterMovieButton.setToggleGroup(toggleGroup);
        FilterShowButton.setToggleGroup(toggleGroup);
        FilterAllButton.selectedProperty().set(true);

        itemEdit.setVisible(false);

        //hent alle unapproved krediteringer
        try {
            DatabaseLoaderFacade.getInstance().getAllUnApprovedCredits();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        //sæt dem i lister
        movieObservableList = MovieManager.getInstance().getMovies();
        personObservableList = PersonManager.getInstance().getPersonList();
        showObservableList = ShowManager.getInstance().getShowList();

        //hent ting
        ObservableList<ICredit> observableList = ApplicationManager.getInstance().search("");

        itemView.setCellFactory(new CustomCellFactory());
        itemView.setEditable(true);
        itemView.setItems(observableList);
    }

    public void editItem(ICredit credit) {
        ScrollPane scrollPane = new ScrollPane();
        FilterAllButton.setVisible(false);
        FilterMovieButton.setVisible(false);
        FilterPersonButton.setVisible(false);
        FilterShowButton.setVisible(false);

        itemView.disableProperty().set(true);
        itemView.visibleProperty().set(false);
        itemEdit.setVisible(true);
        itemEdit.setViewOrder(1);
        GridPane editGrid = new GridPane();
            ColumnConstraints col0 = new ColumnConstraints();
            ColumnConstraints col1 = new ColumnConstraints();
            ColumnConstraints col2 = new ColumnConstraints();

        if (credit instanceof IPerson){
            col0.setPercentWidth(1);
            col1.setPercentWidth(2);
            editGrid.getColumnConstraints().add(0, col0);
            editGrid.getColumnConstraints().add(1, col1);

            PersonManager.getInstance().searchPerson(credit.getName());
            //opret person
            IPerson person = PersonManager.getInstance().getPersonById(credit.getCreditID());

            //knap til godkend redigering
            Button godkendBtn = new Button();
            Button annullerBtn = new Button();
                godkendBtn.setText("Godkend redigering");
                annullerBtn.setText("Annuller");
            //opret labels for tingene
            Label nameLabel = new Label("Navn: ");
            Label emailLabel = new Label("Email: ");
            Label descriptionLabel = new Label("Beskrivelse: ");
            Label personalInfoLabel = new Label("Personlig info: ");
            Label addNewJob = new Label("Tilføj et job: ");
            Label jobPart = new Label("Udførte jobs: ");

            //opret felter for person
            TextField name = new TextField();
                name.setText(person.getName());
            TextField email = new TextField();
                email.setText(person.getEmail());
            TextArea personalInfo = new TextArea();
                personalInfo.setText(person.getPersonalInfo());

            //opret combobox for roller/job
            GridPane jobRolePane = new GridPane();
                Button addJobBtn = new Button();
                ComboBox<Role> job = new ComboBox();
                ComboBox<IMovie> movies = new ComboBox();
                    movies.setConverter(new StringConverter<IMovie>() {
                        @Override
                        public String toString(IMovie iMovie) {
                            return iMovie.getName();
                        }

                        @Override
                        public IMovie fromString(String s) {
                            return null;
                        }
                    });
                    addJobBtn.setText("Tilføj");

                    job.getItems().addAll(Role.values());
                    ObservableList<IMovie> movieList = MovieManager.getInstance().searchMovie("");

                    for (IMovie movie : movieList){
                        movies.getItems().add(movie);
                    }

                    job.getSelectionModel().selectFirst();
                    movies.getSelectionModel().selectFirst();
                    jobRolePane.add(job,0,0);
                    jobRolePane.add(movies,1,0);
                    jobRolePane.add(addJobBtn,2,0);

                    TextField charactername = new TextField();
                    jobRolePane.add(charactername,0,1);
                    job.setOnAction(actionEvent -> {
                        if (job.getSelectionModel().getSelectedItem().toString().equals("Skuespiller")){
                            charactername.setPromptText("Indtast karakters navn");
                            jobRolePane.add(charactername,0,1);
                        } else if (jobRolePane.getChildren().contains(charactername) && !job.getSelectionModel().getSelectedItem().toString().equals("Skuespiller")){
                            jobRolePane.getChildren().remove(charactername);
                        }
                    });

            editGrid.add(nameLabel, 1,0);
            editGrid.add(emailLabel, 1,1);
            editGrid.add(descriptionLabel, 1,2);
            editGrid.add(personalInfoLabel, 1,3);
            editGrid.add(addNewJob,1,4);
            editGrid.add(jobPart,1,5);

            editGrid.add(name,2,0);
            editGrid.add(email,2,1);
            editGrid.add(personalInfo,2,3);
            editGrid.add(jobRolePane,2,4);
            editGrid.add(createdoneJobs(person),2,5);

            editGrid.add(godkendBtn,3,0);
            editGrid.add(annullerBtn,3,1);


            addJobBtn.setOnAction(actionEvent -> {
                if (job.getSelectionModel().getSelectedItem().toString().equals("Skuespiller")){
                    JobManager.getInstance().addTempJob(person.getPersonID(), job.getSelectionModel().getSelectedItem(), charactername.getText(), movies.getSelectionModel().getSelectedItem().getProductionID());
                    DatabaseLoaderFacade.getInstance().putInDatabase(JobManager.getInstance().getTempList().get(0));
                    JobManager.getInstance().getTempList().remove(0);
                } else {
                    JobManager.getInstance().addTempJob(person.getPersonID(), job.getSelectionModel().getSelectedItem(), movies.getSelectionModel().getSelectedItem().getProductionID());
                    DatabaseLoaderFacade.getInstance().putInDatabase(JobManager.getInstance().getTempList().get(0));
                    JobManager.getInstance().getTempList().remove(0);
                }
                //editItem(credit);
                PersonManager.getInstance().searchPerson(credit.getName());
                //opret person
                IPerson person2 = PersonManager.getInstance().getPersonById(credit.getCreditID());
                editGrid.getChildren().remove(11);
                editGrid.add(createdoneJobs(person2),2,5);
            });

            godkendBtn.setOnAction(actionEvent -> {
                person.setName(name.getText());
                person.setEmail(email.getText());
                person.setPersonalInfo(personalInfo.getText());
                try {
                    DatabaseLoaderFacade.getInstance().updateCredit(person);
                } catch (SQLException sqlException) {
                    sqlException.printStackTrace();
                }
                try {
                    MenuController.getInstance().setContentPane("CreditOverlook.fxml", CreditOverlookController.getInstance());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            annullerBtn.setOnAction(actionEvent -> {
                try {
                    MenuController.getInstance().setContentPane("CreditOverlook.fxml", CreditOverlookController.getInstance());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
        if (credit instanceof IMovie){
            col0.setPercentWidth(8);
            col1.setPercentWidth(12);
            col2.setPercentWidth(60);
            editGrid.getColumnConstraints().add(0, col0);
            editGrid.getColumnConstraints().add(1, col1);
            editGrid.getColumnConstraints().add(2,col2);

            IMovie movie = MovieManager.getInstance().searchMovie(credit.getName()).get(0);
            //knap til godkend redigering
            Button godkendBtn = new Button();
            Button annullerBtn = new Button();
                godkendBtn.setText("Godkend redigering");
                annullerBtn.setText("Annuller");

                Label movieNameLabel = new Label("Navn: ");
                TextField movieName = new TextField();
                    movieName.setText(movie.getName());
                Label movieDescriptionLabel = new Label("Beskrivelse");
                TextArea movieDescription = new TextArea();
                    movieDescription.setText(movie.getDescription());
                Label categoryLabel = new Label("Kategori");
                ComboBox<Category> categoryComboBox = new ComboBox<>();
                    categoryComboBox.getItems().addAll(Category.values());
                    categoryComboBox.getSelectionModel().select(movie.getCategories()[0]);
                Label movieLengthInSecondsLabel = new Label("Længde i sekunder");
                TextField movieLengthInSeconds = new TextField();
                    movieLengthInSeconds.textProperty().addListener(new ChangeListener<String>() {
                        @Override
                        public void changed(ObservableValue<? extends String> observable, String oldValue,
                                            String newValue) {
                            if (!newValue.matches("\\d*")) {
                                movieLengthInSeconds.setText(newValue.replaceAll("[^\\d]", ""));
                            }
                        }
                    });
                movieLengthInSeconds.setText(String.valueOf(movieLengthInSeconds));



            editGrid.add(movieNameLabel,1,0);
            editGrid.add(movieDescriptionLabel,1,1);
            editGrid.add(categoryLabel,1,2);
            editGrid.add(movieLengthInSecondsLabel,1,3);

            editGrid.add(movieName,2,0);
            editGrid.add(movieDescription,2,1);
            editGrid.add(categoryComboBox,2,2);
            editGrid.add(movieLengthInSeconds,2,3);

            editGrid.add(godkendBtn,3,0);
            editGrid.add(annullerBtn,3,1);

            godkendBtn.setOnAction(actionEvent -> {
                movie.setName(movieName.getText());
                movie.setDescription(movieDescription.getText());
                Category[] list = new Category[]{
                        categoryComboBox.getSelectionModel().getSelectedItem()
                };
                movie.setCategories(list);
                movie.setLengthInSecs( Integer.parseInt(movieLengthInSeconds.getText()));
                try {
                    DatabaseLoaderFacade.getInstance().updateCredit(movie);
                } catch (SQLException sqlException) {
                    sqlException.printStackTrace();
                }
                try {
                    MenuController.getInstance().setContentPane("CreditOverlook.fxml", CreditOverlookController.getInstance());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            annullerBtn.setOnAction(actionEvent -> {
                try {
                    MenuController.getInstance().setContentPane("CreditOverlook.fxml", CreditOverlookController.getInstance());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
        if (credit instanceof IShow){
            col0.setPercentWidth(8);
            col1.setPercentWidth(12);
            col2.setPercentWidth(60);
            editGrid.getColumnConstraints().add(0, col0);
            editGrid.getColumnConstraints().add(1, col1);
            editGrid.getColumnConstraints().add(2,col2);

            IShow iShow = ShowManager.getInstance().getShowById(credit.getCreditID());

            //knap til godkend redigering
            Button godkendBtn = new Button();
            Button annullerBtn = new Button();
                godkendBtn.setText("Godkend redigering");
                annullerBtn.setText("Annuller");

            Label showNameLabel = new Label("Navn: ");
            Label showDescriptionLabel = new Label("Beskrivelse: ");
            Label showSeasonsLabel = new Label("Sæsoner: ");

            TextField showName = new TextField();
                showName.setText(iShow.getName());
            TextArea showDescription = new TextArea();
                showDescription.setText(iShow.getDescription());
            ListView showSeasons = new ListView();
                showSeasons.setCellFactory(new CustomCellFactory());
                showSeasons.setItems(iShow.getSeasons());

            editGrid.add(showNameLabel,1,0);
            editGrid.add(showDescriptionLabel,1,1);
            editGrid.add(showSeasonsLabel,1,2);

            editGrid.add(showName,2,0);
            editGrid.add(showDescription,2,1);
            editGrid.add(showSeasons,2,2);

            editGrid.add(godkendBtn,3,0);
            editGrid.add(annullerBtn,3,1);

            godkendBtn.setOnAction(actionEvent -> {
                iShow.setName(showName.getText());
                iShow.setDescription(showDescription.getText());
                try {
                    DatabaseLoaderFacade.getInstance().updateCredit(iShow);
                } catch (SQLException sqlException) {
                    sqlException.printStackTrace();
                }
                try {
                    MenuController.getInstance().setContentPane("CreditOverlook.fxml", CreditOverlookController.getInstance());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            annullerBtn.setOnAction(actionEvent -> {
                try {
                    MenuController.getInstance().setContentPane("CreditOverlook.fxml", CreditOverlookController.getInstance());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
        if (credit instanceof ISeason) {
            col0.setPercentWidth(8);
            col1.setPercentWidth(12);
            col2.setPercentWidth(60);
            editGrid.getColumnConstraints().add(0, col0);
            editGrid.getColumnConstraints().add(1, col1);
            editGrid.getColumnConstraints().add(2,col2);

            ISeason iSeason = SeasonManager.getInstance().getSeasonById(credit.getCreditID());

            //knap til godkend redigering
            Button godkendBtn = new Button();
            Button annullerBtn = new Button();
                godkendBtn.setText("Godkend redigering");
                annullerBtn.setText("Annuller");


            Label theshow = new Label(ShowManager.getInstance().getShowById(iSeason.getShowID()).getName() + " " + iSeason.getName());
            Label seasonDescriptionLabel = new Label("Beskrivelse: ");
            Label seasonEpisodesLabel = new Label("Episoder");

            TextArea seasonDescription = new TextArea();
                seasonDescription.setText(iSeason.getDescription());
            ListView seasonEpisodes = new ListView();

                seasonEpisodes.setCellFactory(new CustomCellFactory());
                seasonEpisodes.setItems(iSeason.getEpisodes());


            editGrid.add(theshow,1,0);
            editGrid.add(seasonDescriptionLabel,1,1);
            editGrid.add(seasonEpisodesLabel,1,2);

            editGrid.add(seasonDescription,2,1);
            editGrid.add(seasonEpisodes,2,2);

            editGrid.add(godkendBtn,3,0);
            editGrid.add(annullerBtn,3,1);

            godkendBtn.setOnAction(actionEvent -> {
                iSeason.setDescription(seasonDescription.getText());
                try {
                    DatabaseLoaderFacade.getInstance().updateCredit(iSeason);
                } catch (SQLException sqlException) {
                    sqlException.printStackTrace();
                }
                try {
                    MenuController.getInstance().setContentPane("CreditOverlook.fxml", CreditOverlookController.getInstance());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            annullerBtn.setOnAction(actionEvent -> {
                try {
                    MenuController.getInstance().setContentPane("CreditOverlook.fxml", CreditOverlookController.getInstance());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
        if (credit instanceof IEpisode){
            col0.setPercentWidth(8);
            col1.setPercentWidth(12);
            col2.setPercentWidth(60);
            editGrid.getColumnConstraints().add(0, col0);
            editGrid.getColumnConstraints().add(1, col1);
            editGrid.getColumnConstraints().add(2,col2);

            IEpisode iEpisode = EpisodeManager.getInstance().getEpisodeById(credit.getCreditID());

            //knap til godkend redigering
            Button godkendBtn = new Button();
            Button annullerBtn = new Button();
                godkendBtn.setText("Godkend redigering");
                annullerBtn.setText("Annuller");

            Label episodeNameLabel = new Label("Navn: ");
            Label episodeDescriptionLabel = new Label("Beskrivelse: ");
            Label episodeLengthInSecondsLabel = new Label("Length in seconds: ");
            Label episodecategoryLabel = new Label("Kategori");

            TextField episodeName = new TextField();
                episodeName.setText(iEpisode.getName());
            TextArea episodeDescription = new TextArea();
                episodeDescription.setText(iEpisode.getDescription());
            TextField episodeLengthInSeconds = new TextField();
            ComboBox<Category> categoryComboBox = new ComboBox<>();
                categoryComboBox.getItems().addAll(Category.values());
                categoryComboBox.getSelectionModel().select(iEpisode.getCategories()[0]);
            episodeLengthInSeconds.textProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue,
                                    String newValue) {
                    if (!newValue.matches("\\d*")) {
                        episodeLengthInSeconds.setText(newValue.replaceAll("[^\\d]", ""));
                    }
                }
            });
            episodeLengthInSeconds.setText(String.valueOf(episodeLengthInSeconds));

            editGrid.add(episodeNameLabel,1,0);
            editGrid.add(episodeDescriptionLabel, 1,1);
            editGrid.add(episodecategoryLabel,1,2);
            editGrid.add(episodeLengthInSeconds,1,3);

            editGrid.add(episodeName,2,0);
            editGrid.add(episodeDescription,2,1);
            editGrid.add(categoryComboBox, 2,2);
            editGrid.add(episodeLengthInSeconds,2,3);

            editGrid.add(godkendBtn,3,0);
            editGrid.add(annullerBtn,3,1);


            godkendBtn.setOnAction(actionEvent -> {
                iEpisode.setName(episodeName.getText());
                iEpisode.setDescription(episodeDescription.getText());
                Category[] list = new Category[]{
                        categoryComboBox.getSelectionModel().getSelectedItem()
                };
                iEpisode.setCategories(list);
                iEpisode.setLengthInSecs(Integer.parseInt(episodeLengthInSeconds.getText()));

                try {
                    DatabaseLoaderFacade.getInstance().updateCredit(iEpisode);
                } catch (SQLException sqlException) {
                    sqlException.printStackTrace();
                }
                try {
                    MenuController.getInstance().setContentPane("CreditOverlook.fxml", CreditOverlookController.getInstance());
                } catch (IOException e) {
                    e.printStackTrace();
                }

            });
            annullerBtn.setOnAction(actionEvent -> {
                try {
                    MenuController.getInstance().setContentPane("CreditOverlook.fxml", CreditOverlookController.getInstance());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.setContent(editGrid);
        itemEdit.getChildren().add(scrollPane);
    }

    public GridPane createdoneJobs(IPerson person){
        //udførte jobs
        GridPane doneJobs = new GridPane();
        int i = 0;

        for (IJob iJob: person.getJobs()){
            //System.out.println(iJob.getCharacterName() + " på " + iJob.getProductionID());
            if (iJob.getCharacterName() != null){
                Label jobLabel = new Label();
                jobLabel.setText(iJob.getRole() + " Spiller " + iJob.getCharacterName() + " på " + iJob.getProductionID()  + "\n");
                doneJobs.add(jobLabel,0,i);
                i++;
            } else {
                Label jobLabel = new Label();
                jobLabel.setText(iJob.getRole() + " på " + iJob.getProductionID() + "\n");
                doneJobs.add(jobLabel,0,i);
                i++;
            }
        }
        if (doneJobs.getChildren().isEmpty()){
            Label noJobs = new Label("Denne person har ikke nogle krediteringer endnu");
            doneJobs.add(noJobs,0,1);
        }
        return doneJobs;
    }

    public void changeApprovalType(ActionEvent actionEvent) {
        if (ApprovalBox.getSelectionModel().getSelectedItem().toString().equals("Unapproved")){
            //fjern liste content
            //listView.getItems().clear();
            switch (toggleGroup.getSelectedToggle().toString()) {
                case "Persons" -> setContent(personObservableList);
                case "Movies" -> setContent(movieObservableList);
                case "Shows" -> setContent(showObservableList);
                case "All" -> setContent(ApplicationManager.getInstance().search(searchString, ""));
                default -> {
                    setContent(personObservableList);
                    setContent(movieObservableList);
                    setContent(showObservableList);
                }
            }
        }
        if (ApprovalBox.getSelectionModel().getSelectedItem().toString().equals("Approved")){
            itemView.getItems().clear();
            switch (toggleGroup.getSelectedToggle().toString()){
                case "Persons": itemView.setItems(ApplicationManager.getInstance().search(searchString, "persons")); break;
                case "Movies": itemView.setItems(ApplicationManager.getInstance().search(searchString, "movie")); break;
                case "Shows": itemView.setItems(ApplicationManager.getInstance().search(searchString, "shows")); break;
                default:
                    itemView.setItems(ApplicationManager.getInstance().search(searchString, ""));
                    break;
            }
        }
    }

    public void setContent (ObservableList<? extends ICredit> creditList){
        ObservableList listToSet = FXCollections.observableArrayList();
        for (ICredit credit : creditList){
            if (!credit.isApproved()){
                listToSet.add(credit);
            }
        }
        //listView.setItems(listToSet);
    }


    public void handleFilterMovies(ActionEvent actionEvent){
        if (ApprovalBox.getSelectionModel().getSelectedItem().toString().equals("Approved")) {
            itemView.getItems().clear();
            itemView.setItems(ApplicationManager.getInstance().search(searchString, "movie"));
        } else if (ApprovalBox.getSelectionModel().getSelectedItem().toString().equals("Unapproved")){
            setContent(movieObservableList);
        }
    }
    public void handleFilterShows(ActionEvent actionEvent){
        if (ApprovalBox.getSelectionModel().getSelectedItem().toString().equals("Approved")) {
            itemView.getItems().clear();
            itemView.setItems(ApplicationManager.getInstance().search("", "shows"));
        } else if (ApprovalBox.getSelectionModel().getSelectedItem().toString().equals("Unapproved")){
            setContent(showObservableList);
        }
    }
    public void handleFilterPersons(ActionEvent actionEvent){
        if (ApprovalBox.getSelectionModel().getSelectedItem().toString().equals("Approved")) {
            itemView.getItems().clear();
            itemView.setItems(ApplicationManager.getInstance().search(searchString, "persons"));
        } else if (ApprovalBox.getSelectionModel().getSelectedItem().toString().equals("Unapproved")){
            setContent(personObservableList);
        }
    }
    public void handleFilterAll(ActionEvent actionEvent){
        if (ApprovalBox.getSelectionModel().getSelectedItem().toString().equals("Approved")) {
            itemView.getItems().clear();
            itemView.setItems(ApplicationManager.getInstance().search(""));
        }
    }
}
