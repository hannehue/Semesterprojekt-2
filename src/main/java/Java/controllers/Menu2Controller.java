package Java.controllers;

import Java.CreditSystemController;
import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
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
    protected AnchorPane rootPane;
    @FXML
    protected VBox VBoxMenu;
    @FXML
    protected VBox VBoxMenuLabels;


    @FXML
    protected ImageView profileImageview;
    @FXML
    protected ImageView approveCreditImageview;
    @FXML
    protected ImageView addCreditsImageview;
    @FXML
    protected ImageView addUserImageview;
    @FXML
    protected ImageView logoutButtonImageview;
    @FXML
    protected Label profileLabel;
    @FXML
    protected Label approveCreditLabel;
    @FXML
    protected Label addCreditsLabel;
    @FXML
    protected Label addUserLabel;
    @FXML
    protected Label logoutButtonLabel;



    @FXML
    protected ImageView loginButtonImageview;
    @FXML
    protected Label loginButtonLabel;



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
        //Animation til at åbne menu
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

            //Menu load----------------
            //Hvis der er logget ind, hvis menu i forhold til brugerens UserType
            if (CreditSystemController.getUserType() != null) {
                if (!CreditSystemController.getUserType().getPersonalProfile()){
                    VBoxMenu.getChildren().removeAll(profileImageview);
                    VBoxMenuLabels.getChildren().removeAll(profileLabel);
                }
                if (!CreditSystemController.getUserType().getAddCredit()){
                    VBoxMenu.getChildren().removeAll(addCreditsImageview);
                    VBoxMenuLabels.getChildren().removeAll(addCreditsLabel);
                }
                if (!CreditSystemController.getUserType().getAddUser()){
                    VBoxMenu.getChildren().removeAll(addUserImageview);
                    VBoxMenuLabels.getChildren().removeAll(addUserLabel);
                }
                if (!CreditSystemController.getUserType().getApproveCredit()){
                    VBoxMenu.getChildren().removeAll(approveCreditImageview);
                    VBoxMenuLabels.getChildren().removeAll(approveCreditLabel);
                }
                VBoxMenu.getChildren().removeAll(loginButtonImageview);
                VBoxMenuLabels.getChildren().removeAll(loginButtonLabel);
            } else { //Hvis ikke der er logget ind skal der kun vises login
                VBoxMenu.getChildren().removeAll(profileImageview, approveCreditImageview, addCreditsImageview,addUserImageview, logoutButtonImageview);
                VBoxMenuLabels.getChildren().removeAll(profileLabel, approveCreditLabel, approveCreditLabel, addCreditsLabel,addUserLabel,logoutButtonLabel);
                VBoxMenu.toFront();
                VBoxMenuLabels.toFront();
            }

        } else {
            //Animation til at lukke menu
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
    //Metode der bare sætter menuen til at være usyndelig
    private void hideMenu(){
        Menu.setVisible(false);
        MenuLabels.setVisible(false);
    }
    //Søge felt
    public void handleOpenSearch(MouseEvent mouseEvent) {
        //Hvis søgefeltet er synligt
        if (searchField.visibleProperty().get()){
            //Ignorer at der skal søge hvis brugeren ikke har indtastet noget
            if (!searchField.getText().equalsIgnoreCase("")) {
                //gemmer søgestrengen
                searchString = searchField.getText();
                //prøver at åbne søgeresultat ind i content
                try {
                    setContentPane("SearchResult.fxml");
                    SearchController.setContent();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            //Hvis der ikke er indtastet noget
            } else {
                searchField.setPromptText("Indtast noget");
            }
        //Hvis søgefeltet ikke er åbent, så start animation
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

    //Gå til login
    @FXML
    private void handleLogin(MouseEvent mouseEvent){
        try {
            setContentPane("Login.fxml");
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    //Getter for søgestrengen
    public static String getSearchString() {
        return searchString;
    }

    //Metode til at loaded fxml fil ind på ContentPane, så de hele kan køres fra Menu2.fxml
    public static void setContentPane(String fxml) throws IOException {
        ContentPane.getChildren().clear();
        Parent root = FXMLLoader.load(Menu2Controller.class.getClassLoader().getResource(fxml));
        ContentPane.getChildren().add(root);
    }


    //Menu knap til Personlig profil
    public void handlePersonalProfile(MouseEvent mouseEvent) {
        try {
            setContentPane("PersonalProfile.fxml");
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    //Credit Person
    @FXML
    protected  void handleEditProfile(ActionEvent Event) throws IOException{
        //Tilføj kode der henter brugerens informationer og sætter dem i textfields i stedet for labels
        CreditSystemController.setRoot("CreditPersonProfile-edit");
    }
}
