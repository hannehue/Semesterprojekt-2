package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    private static Scene scene;
    @Override
    public void start(Stage primaryStage) throws Exception{
        scene = new Scene(loadFXML("GUI"), 960,720);
        primaryStage.setTitle("TV2-Krediteringer");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    //sætter root for scenen, så den ved hvilken fil der skal vises
    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }
    //metode til at indlæse den nye .fxml fil som skal vises
    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getClassLoader().getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
