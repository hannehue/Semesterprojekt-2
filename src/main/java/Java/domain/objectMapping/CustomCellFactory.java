package Java.domain.objectMapping;

import Java.domain.services.CustomCell;
import Java.interfaces.ICredit;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

public class CustomCellFactory implements Callback<ListView<ICredit>, ListCell<ICredit>> {
    @Override
    public ListCell<ICredit> call(ListView<ICredit> iCreditListView) {
        return new CustomCell();
    }
}
