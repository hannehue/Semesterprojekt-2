package Java;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CreditSystemController extends Application {

    private int idTracker; //should be moved to database
    private DatabaseLoader dataLoader;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("GUI.fxml"));
        primaryStage.setTitle("TV2-Krediteringer");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
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
