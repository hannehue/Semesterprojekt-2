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
    private static int idTracker = 0; //should be moved to database
    private DatabaseLoader dataLoader;
    private static ArrayList<Credit> creditList = new ArrayList<>();
    private static Stage primaryStage;
    private static UserType userType;

    @Override
    public void start(Stage primaryStage) throws Exception{
        scene = new Scene(loadFXML("Dashboard"), 1024,768);
        primaryStage.setTitle("TV2-Krediteringer");
        primaryStage.setScene(scene);
        primaryStage.show();
        this.primaryStage = primaryStage;

        creditList.add(new Person("Peter Petesdafasdf", null, 12342, true, "this is the desc", 2342,"31330913",null, null));
        creditList.add(new Person("New pers", null, 123, true, "this is the desc", 2342,"31330913",null, null));

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
        creditList.add(person);
        System.out.println(person.getName());
    }

    public static ArrayList<Credit> getCreditList() {
        return creditList;
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
}
