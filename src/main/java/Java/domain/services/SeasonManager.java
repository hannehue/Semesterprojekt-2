package Java.domain.services;

import Java.domain.ApplicationManager;
import Java.domain.data.Season;
import Java.interfaces.ISeason;
import Java.interfaces.IShow;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;

import java.util.Date;

public class SeasonManager {
    private static SeasonManager instance = new SeasonManager();
    private SeasonManager(){}

    public static SeasonManager getInstance() {
        return instance;
    }

    private final ObservableMap<Integer, ISeason> seasonMap = FXCollections.observableHashMap();

    public void addSeason(String description, int showId) {
        IShow show = ShowManager.getInstance().getShowById(showId);
        ISeason season = new Season(
                "S" + (show.getNumberOfSeason() + 1),
                description,
                false
        );
        show.getSeasons().add(season.getCreditID());
        seasonMap.put(season.getCreditID(), season);
        show.setAllSeasonApproved(false);
    }


    public ISeason getSeasonById(int seasonId) {
        return seasonMap.get(seasonId);
    }

    public ObservableMap<Integer, ISeason> getSeasonMap(){
        return seasonMap;
    }
}
