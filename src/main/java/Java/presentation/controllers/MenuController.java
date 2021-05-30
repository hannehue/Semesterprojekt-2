package Java.presentation.controllers;

import Java.domain.ApplicationManager;
import javafx.animation.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
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
    protected Pane creditOverlook;

    @FXML
    protected ImageView loginButton;
    @FXML
    protected Label loginButtonLabel;

    private AnchorPane ContentPane;
    private String searchString;
    private static MenuController instance = new MenuController();

    private MenuController() {
    }

    public static MenuController getInstance() {
        return instance;
    }

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
        Searchicon.toFront();
    }


    ObservableList<Node> tempChildren = FXCollections.observableArrayList();
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

            if (tempChildren.size() == 0) {
                tempChildren.addAll(VBoxMenu.getChildren());
            } else {
                VBoxMenu.getChildren().clear();
                VBoxMenu.getChildren().setAll(tempChildren);
            }


            //Menu load----------------
            //Hvis der er logget ind, hvis menu i forhold til brugerens UserType
            if (ApplicationManager.getInstance().getUserType() != null) {
                if (!ApplicationManager.getInstance().getUserType().getPersonalProfile()){
                    System.out.println("personal legal");
                    VBoxMenu.getChildren().removeAll(profile);
                }
                if (!ApplicationManager.getInstance().getUserType().getAddCredit()){
                    System.out.println("credit legal");
                    VBoxMenu.getChildren().removeAll(addCredits);
                }
                if (!ApplicationManager.getInstance().getUserType().getAddUser()){
                    System.out.println("add user legal");
                    VBoxMenu.getChildren().removeAll(addUserI);
                }
                if (!ApplicationManager.getInstance().getUserType().getApproveCredit()){
                    System.out.println("approve credit legal");
                    VBoxMenu.getChildren().removeAll(approveCredit);
                }
                if (!ApplicationManager.getInstance().getUserType().getCreditOverlook()){
                    System.out.println("approve credit overlook");
                    VBoxMenu.getChildren().removeAll(creditOverlook);
                }
                VBoxMenu.getChildren().removeAll(login);
            } else { //Hvis ikke der er logget ind skal der kun vises login
                System.out.println("usertype is null");
                VBoxMenu.getChildren().removeAll(profile, approveCredit, addCredits,addUserI, logout, creditOverlook);
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
        if (!searchField.visibleProperty().get()){
            searchField.setVisible(true);
            Timeline timeline = new Timeline();
            KeyValue keyValue = new KeyValue(searchField.translateXProperty(), 0, Interpolator.LINEAR);
            KeyFrame keyFrame = new KeyFrame(Duration.millis(3000), keyValue);
            timeline.getKeyFrames().add(keyFrame);
            timeline.play();
        }
    }

    public void submitSearch(MouseEvent mouseEvent){
        System.out.println("clicked submit search");
        if (searchField.visibleProperty().get()){
            if (!searchField.getText().equalsIgnoreCase("")) {
                searchString = searchField.getText();
                try {
                    setContentPane("SearchResult.fxml", (Object) SearchController.getInstance());
                    SearchController.getInstance().setContent();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                searchField.setPromptText("Indtast noget");
            }
        }
    }

    //Gå til login
    @FXML
    private void handleLogin(MouseEvent mouseEvent){
        try {
            setContentPane("Login.fxml", LoginController.getInstance());
            hideMenu();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void handleAddCredit(MouseEvent mouseEvent) throws IOException {
        setContentPane("AddCredits.fxml", AddCreditController.getInstance());
        hideMenu();
    }

    public String getSearchString() {
        return searchString;
    }

    public void clearContentPane(){
        ContentPane.getChildren().clear();
    }

    public void setContentPane(String fxml, Object controller) throws IOException {
        ContentPane.getChildren().clear();
        Object fxmlController = controller;
        FXMLLoader fxmlLoader = new FXMLLoader(MenuController.class.getClassLoader().getResource(fxml));
        fxmlLoader.setController(fxmlController);
        Parent root = fxmlLoader.load();
        ContentPane.getChildren().add(root);
    }

    public void approveCreditHandler(MouseEvent mouseEvent) throws IOException {
       setContentPane("ApproveCredits.fxml", DashboardController.getInstance());
       hideMenu();
    }

    //Menu knap til Personlig profil
    public void handlePersonalProfile(MouseEvent mouseEvent) {
        try {
            setContentPane("PersonalProfile.fxml", PersonalProfileController.getInstance());
            hideMenu();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void handleLogout(MouseEvent mouseEvent) throws IOException {
        ApplicationManager.getInstance().setUserType(null);
        MenuController.getInstance().clearContentPane();
        Menu.setVisible(false);
    }

    public void handleAddUser(MouseEvent mouseEvent) {
        try {
            setContentPane("AddUsers.fxml", AddUserController.getInstance());
            hideMenu();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void handleCreditOverlook(MouseEvent mouseEvent){
        try{
            setContentPane("CreditOverlook.fxml", CreditOverlookController.getInstance());
            hideMenu();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
