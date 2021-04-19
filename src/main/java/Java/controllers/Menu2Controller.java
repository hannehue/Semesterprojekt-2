package Java.controllers;

import Java.CreditSystemController;
import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Menu;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.security.cert.PolicyNode;
import java.util.ResourceBundle;

public class Menu2Controller implements Initializable {
    @FXML
    protected AnchorPane Menu;
    @FXML
    protected AnchorPane MenuLabels;
    @FXML
    protected TextField searchField;
    @FXML
    protected ImageView Searchicon;
    @FXML
    protected ImageView User;
    @FXML
    protected AnchorPane rootPane;

    private static AnchorPane ContentPane;

    private static String searchString;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ContentPane = new AnchorPane();
        ContentPane.prefHeight(200);
        ContentPane.prefWidth(200);
        rootPane.setTopAnchor(ContentPane, 69.0);
        rootPane.setBottomAnchor(ContentPane, 0.0);
        rootPane.setLeftAnchor(ContentPane, 0.0);
        rootPane.setRightAnchor(ContentPane, 0.0);
        rootPane.getChildren().add(ContentPane);
    }


    public void handleOpenMenu(MouseEvent mouseEvent) {
        ParallelTransition parallelTransition = new ParallelTransition();
        if (!MenuLabels.isVisible()){
            System.out.println("clicked");
            Menu.translateYProperty().set(-30);
            Menu.setVisible(true);
            MenuLabels.translateYProperty().set(-30);
            MenuLabels.setVisible(true);

            Timeline timeline1 = new Timeline();
            KeyValue keyValue1 = new KeyValue(Menu.translateYProperty(), 0, Interpolator.EASE_IN);
            KeyFrame keyFrame1 = new KeyFrame(Duration.millis(500), keyValue1);
            timeline1.getKeyFrames().add(keyFrame1);

            Timeline timeline2 = new Timeline();
            KeyValue keyValue2 = new KeyValue(MenuLabels.translateYProperty(), 0, Interpolator.EASE_IN);
            KeyFrame keyFrame2 = new KeyFrame(Duration.millis(500), keyValue2);
            timeline2.getKeyFrames().add(keyFrame2);

            parallelTransition.getChildren().add(timeline1);
            parallelTransition.getChildren().add(timeline2);

            parallelTransition.play();
            parallelTransition.getChildren().clear();
        } else {

            System.out.println("clicked2");

            Timeline timeline1 = new Timeline();
            KeyValue keyValue1 = new KeyValue(Menu.translateYProperty(), -1000, Interpolator.EASE_OUT);
            KeyFrame keyFrame1 = new KeyFrame(Duration.millis(200), keyValue1);
            timeline1.getKeyFrames().add(keyFrame1);

            Timeline timeline2 = new Timeline();
            KeyValue keyValue2 = new KeyValue(MenuLabels.translateYProperty(), -1000, Interpolator.EASE_OUT);
            KeyFrame keyFrame2 = new KeyFrame(Duration.millis(200), keyValue2);
            timeline2.getKeyFrames().add(keyFrame2);

            parallelTransition.getChildren().add(timeline1);
            parallelTransition.getChildren().add(timeline2);
            parallelTransition.setOnFinished(event -> hideMenu());
            parallelTransition.play();
            parallelTransition.getChildren().clear();
        }
    }

    private void hideMenu(){
        Menu.setVisible(false);
        MenuLabels.setVisible(false);
    }

    public void handleOpenSearch(MouseEvent mouseEvent) {
        if (searchField.visibleProperty().get()){
            if (!searchField.getText().equalsIgnoreCase("")) {
                //searchField.setPromptText(searchField.getText());
                //CreditSystemController.setSearchFieldPlaceholder(searchField.getText());
                searchString = searchField.getText();
                        System.out.println("getting text " + searchField.getText());
                try {
                    setContentPane("SearchResult.fxml");
                    SearchController.setContent();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                searchField.setPromptText("Indtast noget");
            }
        } else {
            searchField.setVisible(true);
            Timeline timeline = new Timeline();
            KeyValue keyValue = new KeyValue(searchField.translateXProperty(), 0, Interpolator.LINEAR);
            KeyFrame keyFrame = new KeyFrame(Duration.millis(3000), keyValue);
            timeline.getKeyFrames().add(keyFrame);
            timeline.play();
            timeline.setOnFinished(event -> searchField.setPromptText(CreditSystemController.getSearchFieldPlaceholder()));
        }
    }





    public void MenuItemShow(MouseEvent mouseEvent) {

    }

    public static String getSearchString() {
        return searchString;
    }

    public static void setContentPane(String fxml) throws IOException {
        ContentPane.getChildren().clear();
        Parent root = FXMLLoader.load(Menu2Controller.class.getClassLoader().getResource(fxml));
        ContentPane.getChildren().add(root);
    }

}
