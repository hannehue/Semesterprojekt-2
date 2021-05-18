package Java.domain.services;

import Java.domain.ApplicationManager;
import Java.domain.data.Episode;
import Java.interfaces.IEpisode;
import Java.interfaces.ISeason;
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

    public int addEpisode(int seasonId, int length, String title) {
        ISeason season = SeasonManager.getInstance().getSeasonById(seasonId);
        IEpisode episode = new Episode(
                getNextEpisode(season.getCreditID()) + " - " + title,
                "description",
                null,
                length,
                new Date()
        );
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
