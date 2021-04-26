package Java;

import java.util.ArrayList;
import java.util.Date;

public class Season extends Credit {
    private int showID;
    private ArrayList<Episode> episodes;
    private boolean allEpisodesApproved;

    public Season(String name, Date dateAdded, int creditID, boolean approved, String description , int showID,  Boolean allEpisodesApproved){
        super(name,dateAdded, creditID, approved, description);
        this.showID = showID;
        this.episodes = new ArrayList<>();
        this.allEpisodesApproved = allEpisodesApproved;
    }

    public ArrayList<Episode> getEpisodes() {
        return episodes;
    }

    public void setEpisodes(ArrayList<Episode> episodes) {
        this.episodes = episodes;
    }

    public int getShowID() {
        return showID;
    }

    public void setShowID(int showID) {
        this.showID = showID;
    }


    @Override
    public String toString() {
        return "Season{" +
                "showID='" + showID + '\'' +
                ", episodes=" + episodes +
                '}';
    }

    @Override
    public String toFileString() {
        String seasonFileString = "";

        seasonFileString += getDateAdded() + "##" + getCreditID() + "##" + isApproved() + "##" + getDescription() + "##" +
                getShowID() + "##";

        seasonFileString += "--";

        for (Episode episode: getEpisodes()){
            seasonFileString += episode.toFileString() + "--";
        }
        seasonFileString += "##" + isAllEpisodesApproved();

        return seasonFileString;
    }

    public boolean isAllEpisodesApproved() {
        return allEpisodesApproved;
    }


    public void setAllEpisodeApproved(boolean allEpisodeApproved) {
        this.allEpisodesApproved = allEpisodeApproved;
    }

    public int getNumberOfEpisode() {
        return episodes.size();
    }

    public void addEpisode(Episode episode){
        episodes.add(episode);
    }
}

