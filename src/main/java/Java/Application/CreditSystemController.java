package Java.Application;

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
    private static Scene scene;
    private static int idTracker = 0; //should be moved to database (tracker id for Movie og Person)
    private DatabaseLoader dataLoader;
    private static ArrayList<IPerson> personList = new ArrayList<>();
    private static ArrayList<IMovie> movieList= new ArrayList<>();
    private static ArrayList<IShow> showList = new ArrayList<>();
    private static ArrayList<Job> tempList = new ArrayList<>();
    private static Stage primaryStage;
    private static UserType userType;
    private static String searchFieldPlaceholder = "";

    @Override
    public void start(Stage primaryStage) throws Exception{

        /** TEST MILJØ **/

        //addPerson("Jens Hans", "Elsker min hund", "45624585", "boi@gmail.com");

        dataLoader = DatabaseLoader.getInstance();
        //creditList.addAll(dataLoader.readPersons());
        //[Role.getRoleFromString("Koreografi")], 234324,["pisboiii"])

        for (String[] strings: dataLoader.getPersonArraylist()){
            personList.add(dataLoader.queryToPerson(strings));
        }
        for (String[] strings: dataLoader.getMovieArrayList()){
            movieList.add(dataLoader.queryToMovie(strings));
        }

        System.out.println(personList.toString());
        /** TEST MILJØ **/

        /** GUI Setup**/
        scene = new Scene(loadFXML("Menu"), 1024,768);
        primaryStage.setTitle("TV2-Krediteringer");
        primaryStage.setScene(scene);
        primaryStage.minWidthProperty().set(300);
        primaryStage.minHeightProperty().set(130);
        primaryStage.show();
        this.primaryStage = primaryStage;

        /*
        personList.add(new Person("Peter Petesdafasdf", null, 12342, true,
                "this is the desc", 2342,"31330913", "this is persinfo", null));
        personList.add(new Person("New pers", null, 123, true, "this is the desc",
                2342,"31330913","this is the persinfo", null));
        personList.add(new Person("New pers 1", null, 123, true, "this is the desc",
                2342,"31330913","this is personinfo new ", null));
         */
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
    public static void setRoot(String fxml) throws IOException {
        primaryStage.setScene(new Scene(loadFXML(fxml)));
    }

    //metode til at indlæse den nye .fxml fil som skal vises
    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(CreditSystemController.class.getClassLoader().getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void addPerson(String name, String description, String phoneNumber, String email){
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

    public static int nextId() {
        int temp = idTracker;
        idTracker++;
        return temp;
    }

    public static void addTempJob(Job job) {
        tempList.add(job);
        System.out.println("temp job added");

    }

    public static void addJob(int productionId) {
        for (int i = tempList.size()-1; i > -1; i--) {
            Job job = tempList.get(i);
            for (IPerson person: personList) {
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

    public static void addMovie(String name, String description, Category[] categories, int id, int length) {
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

    public static void addShow(String title, String description) {
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

    public static void addSeason(String description, String show) {
        for (IShow s: showList) {
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

    public static void addEpisode(String title, int length, String show, String season, int id) {
        for (IShow s : showList) {
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

    public static String getNextEpisode(String show, String season) {
        String episodeString = "";
        for (IShow s: showList) {
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



    public static ArrayList<IPerson> getPersonList() {
        return personList;
    }

    public static ArrayList<IMovie> getMovieList() {
        return movieList;
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static UserType getUserType() {
        return userType;
    }

    public static void setUserType(UserType userTypeSetter) {
        userType = userTypeSetter;
    }

    public static String getSearchFieldPlaceholder() {
        return searchFieldPlaceholder;
    }

    public static void setSearchFieldPlaceholder(String searchFieldPlaceholder) {
        CreditSystemController.searchFieldPlaceholder = searchFieldPlaceholder;
    }

    public static ArrayList<IShow> getShowList() {
        return showList;
    }

    public static String tempListToString() {
        String temp = "Ingen personer tilføjet";
        if (!tempList.isEmpty()) {
            temp = "";
            for (Job job: tempList) {
                for (ICredit person: personList) {
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

    public static void deleteTempList() {
        for (int i = tempList.size()-1; i > -1; i--) {
            tempList.remove(i);
        }
    }

    public static IProduction getProduction(int productionID) {
        for (IMovie movie : movieList) {
            if (movie.getProductionID() == productionID) {
                return movie;
            }
        }
        for (IShow show : showList) {
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
