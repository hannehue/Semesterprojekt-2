package Java.domain.services;

import Java.data.DatabaseLoaderFacade;
import Java.domain.ApplicationManager;
import Java.domain.data.Category;
import Java.domain.data.Episode;
import Java.interfaces.IEpisode;
import Java.interfaces.ISeason;
import Java.interfaces.IShow;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

import java.util.Date;
import java.util.Map;

public class EpisodeManager {
    private static EpisodeManager instance = new EpisodeManager();

    private EpisodeManager(){

    }

    public static EpisodeManager getInstance(){
        return instance;
    }

    private final ObservableList<IEpisode> episodeList = FXCollections.observableArrayList();

    public IEpisode addEpisode(ISeason season, int length, String title, Category category) {
        IEpisode episode = new Episode(
                getNextEpisode(season) + " - " + title,
                "description",
                new Category[]{category},
                length,
                new Date()
        );
        episode.setSeasonID(season.getIDMap().get("seasonID"));
        Map<String, Integer> IDs = DatabaseLoaderFacade.getInstance().putInDatabase(episode);
        episode.setProductionID(IDs.get("productionID"));
        season.getEpisodes().add(episode);
        episodeList.add(episode);
        System.out.println("tilføjet " + episode.getName());
        return episode;
    }

    public String getNextEpisode(ISeason season) {
        String episodeString = season + "E" + (season.getNumberOfEpisode() + 1);
        return episodeString;
    }

    public ObservableList<IEpisode> getEpisodeList(){
        return episodeList;
    }

    public void addToList(ISeason season){
        for (IEpisode episode: season.getEpisodes()){
            episodeList.add(episode);
        }
    }

    public IEpisode getEpisodeById(int episodeCreditId) {
        for (IEpisode episode : episodeList) {
            if (episode.getCreditID() == episodeCreditId) {
                return episode;
            }
        }
        return null;
    }

}
