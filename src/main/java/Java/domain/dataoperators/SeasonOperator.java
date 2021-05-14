package Java.domain.dataoperators;

import Java.domain.ApplicationManager;
import Java.domain.data.Season;
import Java.interfaces.ISeason;
import Java.interfaces.IShow;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;

import java.util.Date;

public class SeasonOperator {
    private static SeasonOperator instance = new SeasonOperator();
    private SeasonOperator(){}

    public static SeasonOperator getInstance() {
        return instance;
    }

    private final ObservableMap<Integer, ISeason> seasonMap = FXCollections.observableHashMap();

    public void addSeason(String description, int showId) {
        IShow show = ShowOperator.getInstance().getShowById(showId);
        ISeason season = new Season(
                "S" + (show.getNumberOfSeason() + 1),
                new Date(),
                ApplicationManager.getInstance().nextId(),
                false,
                description,
                show.getCreditID(),
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
