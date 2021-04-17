package Java.controllers;

import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class Menu2Controller implements Initializable {
    @FXML
    protected AnchorPane Menu;
    @FXML
    protected AnchorPane MenuLabels;
    @FXML
    protected TextField Searchbar;
    @FXML
    protected ImageView Searchicon;
    @FXML
    protected ImageView User;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }


    public void handleOpenMenu(MouseEvent mouseEvent) {
        ParallelTransition parallelTransition = new ParallelTransition();


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



    }

    public void handleOpenSearch(MouseEvent mouseEvent) {
        Searchbar.setVisible(true);
        Timeline timeline = new Timeline();
        KeyValue keyValue = new KeyValue(Searchbar.translateXProperty(), 0, Interpolator.LINEAR);
        KeyFrame keyFrame = new KeyFrame(Duration.millis(3000), keyValue);
        timeline.getKeyFrames().add(keyFrame);
        timeline.play();
    }

    public void MenuItemShow(MouseEvent mouseEvent) {

    }
}
