package Java.domain.services;

import Java.domain.ApplicationManager;
import Java.domain.data.Show;
import Java.interfaces.IShow;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;

import java.util.ArrayList;

public class ShowManager {
    private static ShowManager instance = new ShowManager();
    private ShowManager(){}

    public static ShowManager getInstance() {
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

    public IShow getShowById(int showId) {
        return showMap.get(showId);
    }

    public ArrayList<IShow> getAllShows() {
        return new ArrayList<>(showMap.values());
    }

    public IShow searchShowName(String name) {
        for (IShow show : showMap.values()){
            if (show.getName() == name ) {
                return show;
            }
        }
        return null;
    }
}
