package Java.domain.services;

import Java.persistence.DatabaseLoaderFacade;
import Java.domain.data.Season;
import Java.interfaces.ISeason;
import Java.interfaces.IShow;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.Map;

public class SeasonManager {
    private static SeasonManager instance = new SeasonManager();
    private SeasonManager(){}

    public static SeasonManager getInstance() {
        return instance;
    }

    private final ObservableList<ISeason> seasonList = FXCollections.observableArrayList();

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
        seasonList.add(season);
        show.setAllSeasonApproved(false);
        return season;
    }


    public ISeason getSeasonById(int seasonCreditId) {
        for (ISeason season : seasonList) {
            if (season.getCreditID() == seasonCreditId) {
                return season;
            }
        }
        return null;
    }

    public void addToList(ArrayList<ISeason> seasons){
        for (ISeason season: seasons){
            seasonList.add(season);
        }
        populateEpisodeList();
    }

    public void populateEpisodeList(){
        for (ISeason season: seasonList){
            EpisodeManager.getInstance().addToList(season);
        }
    }

    public ObservableList<ISeason> getSeasonList(){
        return seasonList;
    }
}
