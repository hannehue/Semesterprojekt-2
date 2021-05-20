package Java.domain.services;

import Java.data.DatabaseLoaderFacade;
import Java.domain.ApplicationManager;
import Java.domain.data.Category;
import Java.domain.data.Episode;
import Java.interfaces.IEpisode;
import Java.interfaces.ISeason;
import Java.interfaces.IShow;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;

import java.util.Date;

public class EpisodeManager {
    private static EpisodeManager instance = new EpisodeManager();

    private EpisodeManager(){

    }

    public static EpisodeManager getInstance(){
        return instance;
    }

    private final ObservableMap<Integer, IEpisode> episodeMap = FXCollections.observableHashMap();

    public int addEpisode(ISeason season, int length, String title, Category category) {
        IEpisode episode = new Episode(
                getNextEpisode(season.getCreditID()) + " - " + title,
                "description",
                new Category[]{category},
                length,
                new Date()
        );
        episode.setSeasonID(season.getIDMap().get("seasonID"));
        DatabaseLoaderFacade.getInstance().putInDatabase(episode);
        season.getEpisodes().add(episode.getCreditID());
        episodeMap.put(episode.getCreditID(), episode);
        System.out.println("tilføjet " + episode.getName());
        return 0;
    }

    public String getNextEpisode(Integer seasonId) {
        ISeason season = SeasonManager.getInstance().getSeasonById(seasonId);
        String episodeString = season + "E" + (season.getNumberOfEpisode() + 1);
        return episodeString;
    }

    public ObservableMap<Integer, IEpisode> getEpisodeMap(){
        return episodeMap;
    }

}
