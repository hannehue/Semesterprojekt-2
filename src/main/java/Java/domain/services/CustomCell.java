package Java.domain.services;

import Java.interfaces.ICredit;
import Java.presentation.controllers.CreditOverlookController;
import javafx.scene.control.*;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;

public class CustomCell extends ListCell<ICredit>{

    private Label name;
    private GridPane pane;
    private Button editBtn;
    private Button approveBtn;
    private Button godkend;
    private ColumnConstraints col1 = new ColumnConstraints();
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
        col1.setPercentWidth(85);
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
        getListView().setEditable(false);
    }
}