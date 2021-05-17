package Java.presentation;

import Java.domain.*;
import Java.presentation.controllers.MenuController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class CreditSystemController extends Application {

    private Scene scene;
    private Stage primaryStage;
    private static CreditSystemController instance;

    public static CreditSystemController getInstance() {
        return instance;
    }

    public CreditSystemController(){
        if(instance != null) throw new UnsupportedOperationException("More than one instance cannot be created");
        instance = this;
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        /** GUI Setup*/
        scene = new Scene(loadFXML("Menu", MenuController.getInstance()), 1024,768);
        primaryStage.setTitle("TV2-Krediteringer");
        primaryStage.setScene(scene);
        primaryStage.minWidthProperty().set(300);
        primaryStage.minHeightProperty().set(130);
        primaryStage.show();
        this.primaryStage = primaryStage;

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
}
