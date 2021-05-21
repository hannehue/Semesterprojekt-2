package Java.domain.services;

import Java.data.DatabaseLoaderFacade;
import Java.domain.ApplicationManager;
import Java.domain.data.Season;
import Java.interfaces.ISeason;
import Java.interfaces.IShow;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;

import java.util.Date;
import java.util.Map;

public class SeasonManager {
    private static SeasonManager instance = new SeasonManager();
    private SeasonManager(){}

    public static SeasonManager getInstance() {
        return instance;
    }

    private final ObservableMap<Integer, ISeason> seasonMap = FXCollections.observableHashMap();

    public ISeason addSeason(String description, IShow show) {

        ISeason season = new Season(
                "Season" + (show.getNumberOfSeason() + 1),
                description,
                false
        );
        season.setShowID(show.getIDMap().get("showID"));
        Map<String, Integer> IDs = DatabaseLoaderFacade.getInstance().putInDatabase(season);
        season.setIDMap(IDs);
        season.setCreditID(IDs.get("creditID"));
        show.getSeasons().add(season);
        seasonMap.put(season.getCreditID(), season);
        show.setAllSeasonApproved(false);
        return season;
    }


    public ISeason getSeasonById(int seasonId) {
        return seasonMap.get(seasonId);
    }

    public ObservableMap<Integer, ISeason> getSeasonMap(){
        return seasonMap;
    }
}
