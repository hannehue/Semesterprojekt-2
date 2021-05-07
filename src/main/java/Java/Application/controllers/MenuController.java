package Java.controllers;

import Java.Application.CreditSystemController;
import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MenuController implements Initializable {
    @FXML
    protected AnchorPane Menu;
    @FXML
    protected TextField searchField;
    @FXML
    protected ImageView Searchicon;
    @FXML
    protected AnchorPane rootPane;

    @FXML
    protected VBox VBoxMenu;

    @FXML
    protected Pane login;
    @FXML
    protected Pane logout;
    @FXML
    protected Pane addUserI;
    @FXML
    protected Pane profile;
    @FXML
    protected Pane approveCredit;
    @FXML
    protected Pane addCredits;

    @FXML
    protected ImageView loginButton;
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
        Menu.toFront();
    }


    public void handleOpenMenu(MouseEvent mouseEvent) {
        ParallelTransition parallelTransition = new ParallelTransition();
        if (!Menu.isVisible()){
            System.out.println("clicked");
            Menu.translateYProperty().set(-30);
            Menu.setVisible(true);

            Timeline timeline1 = new Timeline();
            KeyValue keyValue1 = new KeyValue(Menu.translateYProperty(), 0, Interpolator.EASE_IN);
            KeyFrame keyFrame1 = new KeyFrame(Duration.millis(500), keyValue1);
            timeline1.getKeyFrames().add(keyFrame1);


            parallelTransition.getChildren().add(timeline1);

            parallelTransition.play();
            parallelTransition.getChildren().clear();

            //Menu load----------------
            //Hvis der er logget ind, hvis menu i forhold til brugerens UserType
            if (CreditSystemController.getUserType() != null) {
                if (!CreditSystemController.getUserType().getPersonalProfile()){
                    VBoxMenu.getChildren().removeAll(profile);
                }
                if (!CreditSystemController.getUserType().getAddCredit()){
                    VBoxMenu.getChildren().removeAll(addCredits);
                }
                if (!CreditSystemController.getUserType().getAddUser()){
                    VBoxMenu.getChildren().removeAll(addUserI);
                }
                if (!CreditSystemController.getUserType().getApproveCredit()){
                    VBoxMenu.getChildren().removeAll(approveCredit);
                }
                VBoxMenu.getChildren().removeAll(login);
            } else { //Hvis ikke der er logget ind skal der kun vises login
                VBoxMenu.getChildren().removeAll(profile, approveCredit, addCredits,addUserI, logout);
                VBoxMenu.toFront();
            }

        } else {

            //Animation til at lukke menu
            Timeline timeline1 = new Timeline();
            KeyValue keyValue1 = new KeyValue(Menu.translateYProperty(), -1000, Interpolator.EASE_OUT);
            KeyFrame keyFrame1 = new KeyFrame(Duration.millis(200), keyValue1);
            timeline1.getKeyFrames().add(keyFrame1);

            parallelTransition.getChildren().add(timeline1);
            parallelTransition.setOnFinished(event -> hideMenu());
            parallelTransition.play();
            parallelTransition.getChildren().clear();
        }
    }

    //Metode der bare sætter menuen til at være usyndelig
    private void hideMenu(){
        Menu.setVisible(false);
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
            hideMenu();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void handleAddCredit(MouseEvent mouseEvent) throws IOException {
        setContentPane("AddCredits.fxml");
        hideMenu();
    }

    public static String getSearchString() {
        return searchString;
    }

    public static void setContentPane(String fxml) throws IOException {
        ContentPane.getChildren().clear();
        Parent root = FXMLLoader.load(MenuController.class.getClassLoader().getResource(fxml));
        ContentPane.getChildren().add(root);
    }

    public void approveCreditHandler(MouseEvent mouseEvent) throws IOException {
       setContentPane("ApproveCredits.fxml");
       hideMenu();
    }

    //Menu knap til Personlig profil
    public void handlePersonalProfile(MouseEvent mouseEvent) {
        try {
            setContentPane("PersonalProfile.fxml");
            hideMenu();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void handleLogout(MouseEvent mouseEvent) throws IOException {
        CreditSystemController.setUserType(null);
        CreditSystemController.setRoot("Menu");
    }

    public void handleAddUser(MouseEvent mouseEvent) {
        try {
            setContentPane("AddUsers.fxml");
            hideMenu();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
