package Java.Application;

import Java.Application.controllers.MenuController;
import Java.Data.DatabaseLoader;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;


public class CreditSystemController extends Application {

    private Scene scene;
    private int idTracker = 0; //should be moved to database (tracker id for Movie og Person)
    private DatabaseLoader dataLoader;
    private ArrayList<Person> personList = new ArrayList<>();
    private ArrayList<Movie> movieList= new ArrayList<>();
    private ArrayList<Show> showList = new ArrayList<>();
    private ArrayList<Job> tempList = new ArrayList<>();
    private Stage primaryStage;
    private UserType userType;
    private String searchFieldPlaceholder = "";
    private static CreditSystemController instance;

    public static CreditSystemController getInstance() {
        return instance;
    }

    public CreditSystemController(){
        if(instance != null) throw new UnsupportedOperationException("More than one instance cannot be created");
        instance = this;
    }


    @Override
    public void start(Stage primaryStage) throws Exception {

        /** TEST MILJØ **/


        dataLoader = DatabaseLoader.getInstance();
        //creditList.addAll(dataLoader.readPersons());
        //[Role.getRoleFromString("Koreografi")], 234324,["pisboiii"])

        for (String[] strings: dataLoader.getPersonArraylist()){
            personList.add(dataLoader.stringsToPerson(strings));
        }
        for (String[] strings: dataLoader.getMovieArrayList()){
            movieList.add(dataLoader.stringsToMovie(strings));
        }

        System.out.println(personList.toString());
        /** TEST MILJØ **/

        /** GUI Setup*/
        scene = new Scene(loadFXML("Menu", MenuController.getInstance()), 1024,768);
        primaryStage.setTitle("TV2-Krediteringer");
        primaryStage.setScene(scene);
        primaryStage.minWidthProperty().set(300);
        primaryStage.minHeightProperty().set(130);
        primaryStage.show();
        this.primaryStage = primaryStage;

        ArrayList<Job> jobs = new ArrayList<>();
        jobs.add(new Job(Role.MEDVIRKENDE, "mickey", 12312));
        jobs.add(new Job(Role.BILLED_OG_LYDREDIGERING, 12312) );
        Person thisa = (Person) personList.get(0);
        thisa.setJobs(jobs);
    }
    
    @Override
    public void stop() {
        dataLoader.addCreditsToDatabase(personList);
        dataLoader.addCreditsToDatabase(movieList);
        dataLoader.addCreditsToDatabase(showList);

        try {
            dataLoader.writeAllCredits();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //sætter root for scenen, så den ved hvilken fil der skal vises
    public void setRoot(String fxml, Object controller) throws IOException {
        primaryStage.setScene(new Scene(loadFXML(fxml, controller)));
    }

    //metode til at indlæse den nye .fxml fil som skal vises
    private Parent loadFXML(String fxml, Object controller) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/" + fxml + ".fxml"));
        fxmlLoader.setController(controller);
        return fxmlLoader.load();
    }

    public void addPerson(String name, String description, String phoneNumber, String email){
        Person person = new Person(
                name,
                new Date(),
                1, // change later
                false,
                description,
                1,
                phoneNumber,
                null,
                email);
        personList.add(person);
    }

    public int nextId() {
        int temp = idTracker;
        idTracker++;
        return temp;
    }

    public void addTempJob(Job job) {
        tempList.add(job);
        System.out.println("temp job added");

    }

    public void addJob(int productionId) {
        for (int i = tempList.size()-1; i > -1; i--) {
            Job job = tempList.get(i);
            for (Person person: personList) {
                if (person.getCreditID() == job.getPersonId()) {
                    Job newJob;
                    if (job.getRole() == Role.SKUESPILLER) {
                        newJob = new Job(job.getRole(), job.getCharacterName(), productionId);
                    } else {
                        newJob = new Job(job.getRole(), productionId);
                    }
                    person.getJobs().add(newJob);
                }
            }
            tempList.remove(i);
        }
    }

    public void addMovie(String name, String description, Category[] categories, int id, int length) {
        Movie movie = new Movie(
                name,
                null,
                id,
                false,
                description,
                1, // Change later
                categories,
                length,
                null);
        movieList.add(movie);
        System.out.println(movie.getName());
    }

    public void addShow(String title, String description) {
        Show show = new Show(
                title,
                null,
                nextId(),
                false,
                "desc",
                true);
        showList.add(show);
        System.out.println("Added show: " + show.getName());
    }

    public void addSeason(String description, String show) {
        for (Show s: showList) {
            if (s.getName() == show) {
                Season season = new Season(
                        "S" + (s.getNumberOfSeason() + 1),
                        new Date(),
                        nextId(),
                        false,
                        description,
                        s.getCreditID(),
                        false
                );
                s.getSeasons().add(season);
                s.setAllSeasonApproved(false);
                System.out.println("tilføjet " + season.getName() + " til show: " + s.getName());
            }
        }
    }

    public void addEpisode(String title, int length, String show, String season, int id) {
        for (Show s : showList) {
            if (s.getName() == show) {
                for (Season seasonToGet : s.getSeasons()) {
                    if (seasonToGet.getName() == season) {
                        Episode episode = new Episode(
                                getNextEpisode(show, season) + " - " + title,
                                new Date(),
                                id,
                                false,
                                "description",
                                nextId(),
                                null,
                                length,
                                null,
                                seasonToGet.getCreditID()
                        );
                        seasonToGet.addEpisode(episode);
                        seasonToGet.setAllEpisodeApproved(false);
                        System.out.println("tilføjet " + episode.getName());
                    }
                }
            }
        }
    }

    public String getNextEpisode(String show, String season) {
        String episodeString = "";
        for (Show s: showList) {
            if (s.getName() == show) {
                for (Season se: s.getSeasons()) {
                    if (se.getName() == season) {
                        episodeString = season + "E" + (se.getNumberOfEpisode() + 1);
                    }
                }
            }
        }
        return episodeString;
    }



    public ArrayList<Person> getPersonList() {
        return personList;
    }

    public ArrayList<Movie> getMovieList() {
        return movieList;
    }

    public static void main(String[] args) {
        launch(args);
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userTypeSetter) {
        userType = userTypeSetter;
    }

    public String getSearchFieldPlaceholder() {
        return searchFieldPlaceholder;
    }

    public void setSearchFieldPlaceholder(String searchFieldPlaceholder) {
        CreditSystemController.getInstance().searchFieldPlaceholder = searchFieldPlaceholder;
    }

    public ArrayList<Show> getShowList() {
        return showList;
    }

    public String tempListToString() {
        String temp = "Ingen personer tilføjet";
        if (!tempList.isEmpty()) {
            temp = "";
            for (Job job: tempList) {
                for (Credit person: personList) {
                    if (person.getCreditID() == job.getPersonId()){
                        if (job.getRole() == Role.SKUESPILLER) {
                            temp += job.getRole() + ": " + person.getName() + " - " + job.getCharacterName() + "\n";
                        } else {
                            temp += job.getRole() + ": " + person.getName() + "\n";
                        }
                    }
                }
            }
        } return temp;
    }

    public void deleteTempList() {
        for (int i = tempList.size()-1; i > -1; i--) {
            tempList.remove(i);
        }
    }

    public Production getProduction(int productionID) {
        for (Movie movie : movieList) {
            if (movie.getProductionID() == productionID) {
                return movie;
            }
        }
        for (Show show : showList) {
            for (Season season : show.getSeasons()) {
                for (Episode episode : season.getEpisodes()) {
                    if (episode.getProductionID() == productionID) {
                        return episode;
                    }
                }
            }
        }
        return null;
    }
}
