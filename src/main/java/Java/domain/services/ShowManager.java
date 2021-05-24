package Java.domain.services;

import Java.data.DatabaseLoader;
import Java.data.DatabaseLoaderFacade;
import Java.domain.ApplicationManager;
import Java.domain.data.Show;
import Java.interfaces.IPerson;
import Java.interfaces.ISeason;
import Java.interfaces.IShow;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

import java.util.ArrayList;
import java.util.Map;

public class ShowManager {
    private static ShowManager instance = new ShowManager();
    private ShowManager(){}

    public static ShowManager getInstance() {
        return instance;
    }

    private final ObservableList<IShow> showList = FXCollections.observableArrayList();

    public IShow addShow(String title, String description) {
        IShow show = new Show(
                title,
                description,
                false
        );
        Map<String, Integer> IDs = DatabaseLoaderFacade.getInstance().putInDatabase(show);
        show.setIDMap(IDs);
        show.setCreditID(IDs.get("creditID"));
        showList.add(show);
        System.out.println("Added show: " + show.getName());
        return show;
    }

    public ArrayList<IShow> searchShows(String searchString){
        ArrayList<IShow> shows = DatabaseLoaderFacade.getInstance().searchShowsFromDatabase(searchString);
        showList.setAll(shows);
        populateSeasonList();
        return shows;
    }

    public ObservableList<IShow> getShowList() {
        return showList;
    }

    public IShow getShowById(int showCreditId) {
        for (IShow show : showList){
            if (show.getCreditID() == showCreditId){
                return show;
            }
        }
        System.out.println("Show not in list");
        return null;
    }

    public ObservableList<IShow> getAllShows() {
        return showList;
    }

    public IShow searchShowName(String name) {
        for (IShow show : showList){
            if (show.getName() == name ) {
                return show;
            }
        }
        return null;
    }

    public void populateSeasonList(){

        for(IShow show: showList){
            ArrayList<ISeason> seasons = new ArrayList<>();
            seasons.addAll(show.getSeasons());
            SeasonManager.getInstance().addToList(seasons);
        }
    }

}
