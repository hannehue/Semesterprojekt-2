package Java.domain.services;

import Java.interfaces.ICredit;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ResourceBundle;

public class CustomCell extends ListCell<ICredit>{

    private Label name;
    private GridPane pane;
    private Button editBtn;
    private Button approveBtn;
    private Button godkend;
    private ColumnConstraints col1 = new ColumnConstraints();
    private ICredit credit;


    public CustomCell() {
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

        if (!empty && credit != null){
            this.name.setText(credit.buildView());
            setGraphic(pane);
        }
        this.credit = credit;
    }

    @Override
    public void startEdit(){
        if (!isEditable() || !getListView().isEditable()){
            System.out.println("not editable");
            return;
        }
        super.startEdit();
        if (isEditing()) {
            TextField textField = new TextField();
            textField.setPromptText(this.credit.getName());
            pane.getChildren().removeAll();
            pane.add(textField,0,0);

        }
        getListView().setEditable(false);
    }
}