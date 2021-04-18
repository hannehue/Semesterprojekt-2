package Java;

import java.util.ArrayList;
import java.util.Date;

public class Season extends Credit {

    private ArrayList<Episode> episodes;
    private String showID; //the show in which the season is located

    public Season(String seasonName,  String showID, Date dateadded, int seasonID, boolean approved, ArrayList<Episode> episodes, String description){
        super(seasonName, dateadded, seasonID, approved, description);
        this.episodes = episodes;
        this.showID = showID;
    }

    public int getNumberOfEpisode() {
        return episodes.size();
    }

    public void addEpisode(Episode episode){
        episodes.add(episode);
    }

}

