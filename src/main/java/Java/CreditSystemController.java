package Java;

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
    private static ArrayList<Credit> personList = new ArrayList<>(); // omdøbes til personList
    private static ArrayList<Credit> movieList= new ArrayList<>();
    private static ArrayList<Credit> showList = new ArrayList<>();
    private static Stage primaryStage;
    private static UserType userType;
    private static String searchFieldPlaceholder = "";

    @Override
    public void start(Stage primaryStage) throws Exception{
        scene = new Scene(loadFXML("Dashboard"), 1024,768);
        primaryStage.setTitle("TV2-Krediteringer");
        primaryStage.setScene(scene);
        primaryStage.show();
        this.primaryStage = primaryStage;

        personList.add(new Person("Peter Petesdafasdf", null, 12342, true, "this is the desc", 2342,"31330913","this is personnifo for pete", null));
        personList.add(new Person("New pers", null, 123, true, "this is the desc", 2342,"31330913","this is the persinfo", null));
        personList.add(new Person("New pers 1", null, 123, true, "this is the desc", 2342,"31330913","this is personinfo new ", null));
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

    public static int nextId() {
        int temp = idTracker;
        idTracker++;
        return temp;
    }

    public static void addPerson(String name, String description, String phoneNumber, String email){
        Person person = new Person(
                name,
                new Date(), // change later
                nextId(),
                false,
                description,
                1,
                phoneNumber,
                null,
                email);
        personList.add(person);
        System.out.println(person.getName());
    }

    public static void addMovie(String name, String description, int length) {
        Movie movie = new Movie(
                name,
                null,
                nextId(),
                false,
                description,
                1, // Change later
                length,
                null,
                1, //Change later
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
                description,
                1,
                1,
                null,
                1,
                null);
        showList.add(show);
        System.out.println(show.getName());
    }



    public static ArrayList<Credit> getPersonList() {
        return personList;
    }

    public static ArrayList<Credit> getMovieList() {
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
}
