package Java;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.lang.reflect.Array;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;


public class CreditSystemController extends Application {
    private static Scene scene;
    private int idTracker; //should be moved to database
    private DatabaseLoader dataLoader;
    private static ArrayList<Credit> creditList;
    private static Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception{
        creditList = new ArrayList<>();

        /** TEST MILJØ **/

        addPerson("Jens Hans", "Elsker min hund", "45624585", "boi@gmail.com");

        dataLoader = new DatabaseLoader();
        //creditList.addAll(dataLoader.readPersons());
        dataLoader.writePersons(creditList);
        System.out.println(Arrays.toString(creditList.toArray()));

        /** TEST MILJØ **/

        scene = new Scene(loadFXML("GUI"), 1024,768);
        primaryStage.setTitle("TV2-Krediteringer");
        primaryStage.setScene(scene);
        primaryStage.show();
        this.primaryStage = primaryStage;
    }

    //sætter root for scenen, så den ved hvilken fil der skal vises
    static void setRoot(String fxml) throws IOException {
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
        creditList.add(person);
        System.out.println(person.getName());
    }

    public int nextId() {
        int temp = idTracker;
        idTracker++;
        return temp;
    }

    public static void main(String[] args) {
        launch(args);
    }
}