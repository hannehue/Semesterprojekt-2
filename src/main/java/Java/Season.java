package Java;

import java.util.ArrayList;
import java.util.Date;

public class Season extends Credit {

    private ArrayList<Episode> episodes;
    private boolean allEpisodeApproved;
    private int showID; //the show in which the season is located

    public Season(String seasonName,  int showID, Date dateadded, int seasonID, boolean approved, ArrayList<Episode> episodes, String description, boolean allEpisodeApproved){
        super(seasonName, dateadded, seasonID, approved, description);
        this.episodes = episodes;
        this.showID = showID;
        this.allEpisodeApproved = allEpisodeApproved;
    }

    public boolean isAllEpisodeApproved() {
        return allEpisodeApproved;
    }

    public void setAllEpisodeApproved(boolean allEpisodeApproved) {
        this.allEpisodeApproved = allEpisodeApproved;
    }

    public int getNumberOfEpisode() {
        return episodes.size();
    }

    public void addEpisode(Episode episode){
        episodes.add(episode);
    }

    public ArrayList<Episode> getEpisodes() {
        return episodes;
    }
}

