package Java.domain.services;

import Java.data.DatabaseLoaderFacade;
import Java.domain.data.Show;
import Java.interfaces.IShow;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;

import java.util.ArrayList;
import java.util.Map;

public class ShowManager {
    private static ShowManager instance = new ShowManager();
    private ShowManager(){}

    public static ShowManager getInstance() {
        return instance;
    }

    private final ObservableList<IShow> showMap = FXCollections.observableArrayList();

    public IShow addShow(String title, String description) {
        IShow show = new Show(
                title,
                description,
                false
        );
        Map<String, Integer> IDs = DatabaseLoaderFacade.getInstance().putInDatabase(show);
        show.setIDMap(IDs);
        show.setCreditID(IDs.get("creditID"));
        showMap.put(IDs.get("showID"), show);
        System.out.println("Added show: " + show.getName());
        return show;
    }

    public ArrayList<IShow> searchShows(String searchString){
        return DatabaseLoaderFacade.getInstance().searchShowsFromDatabase(searchString);
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
