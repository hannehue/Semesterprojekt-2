package Java.domain.services;

import Java.persistence.DatabaseLoaderFacade;
import Java.domain.ApplicationManager;
import Java.interfaces.ICredit;
import Java.interfaces.IPerson;
import Java.presentation.controllers.CreditOverlookController;
import Java.presentation.controllers.MenuController;
import javafx.scene.control.*;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.sql.SQLException;

public class CustomCell extends ListCell<ICredit>{

    private Label name;
    private GridPane pane;
    private Button editBtn;
    private Button godkend;
    private ICredit credit;


    public CustomCell() {
        super();
        updateSelected(false);
        name = new Label();
        pane = new GridPane();
        editBtn = new Button();
            editBtn.setText("Edit");
            editBtn.setOnAction(actionEvent -> startEdit());

        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(60);
        pane.getColumnConstraints().add(col1);
        pane.add(name,0,0);
        pane.add(editBtn,2,0);

        setGraphic(pane);
    }

    @Override
    public void updateItem(ICredit credit, boolean empty){
        super.updateItem(credit, empty);
        if (isEditing()){
            getListView().scrollTo(credit);
        }
        if (empty || credit == null){
            setText(null);
            setGraphic(null);
        } else {
            this.credit = getItem();
            name.setText(credit.buildView());
            if (this.credit instanceof IPerson){
                Button deleteBtn = new Button();
                deleteBtn.setText("Slet person");
                deleteBtn.setOnAction(actionEvent -> {
                    DatabaseLoaderFacade.getInstance().deleteCredit(getItem());
                    try {
                        MenuController.getInstance().setContentPane("CreditOverlook.fxml", CreditOverlookController.getInstance());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                if (getItem() instanceof IPerson){
                    pane.add(deleteBtn,4,0);
                }
            }
            if (!getItem().isApproved()){
                Button approveBtn = new Button();
                approveBtn.setText("Godkend");
                approveBtn.setOnAction(actionEvent -> {
                    try {
                        DatabaseLoaderFacade.getInstance().setCreditApproveState(credit,true);
                    } catch (SQLException sqlException) {
                        sqlException.printStackTrace();
                    }
                    try {
                        MenuController.getInstance().setContentPane("CreditOverlook.fxml", CreditOverlookController.getInstance());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                pane.add(approveBtn,3,0);
            }
            setGraphic(pane);
        }
    }

    @Override
    public void startEdit(){
        if (!isEditable() || !getListView().isEditable()){
            System.out.println("not editable");
            return;
        }
        super.startEdit();
        if (isEditing()) {
            CreditOverlookController.getInstance().editItem(getItem());
        }
    }
}