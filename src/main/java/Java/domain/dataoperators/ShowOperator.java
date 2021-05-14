package Java.domain.dataoperators;

import Java.domain.ApplicationManager;
import Java.domain.data.Show;
import Java.interfaces.IShow;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;

import java.util.ArrayList;

public class ShowOperator {
    private static ShowOperator instance = new ShowOperator();
    private ShowOperator(){}

    public static ShowOperator getInstance() {
        return instance;
    }

    private final ObservableMap<Integer, IShow> showMap = FXCollections.observableHashMap();

    public void addShow(String title, String description) {
        IShow show = new Show(
                title,
                null,
                ApplicationManager.getInstance().nextId(),
                false,
                "desc",
                true);
        showMap.put(show.getCreditID(), show);
        System.out.println("Added show: " + show.getName());
    }

    public ObservableMap<Integer, IShow> getShowList() {
        return showMap;
    }

    public IShow getShowById(int showId){
        return showMap.get(showId);
    }

    public ArrayList<IShow> getAllShows(){
        return (ArrayList<IShow>) showMap.values();
    }

    public IShow searchShowName(String name){
        for (IShow show : showMap.values()){
            if (show.getName() == name ) {
                return show;
            }
        }
        return null;
    }
}
