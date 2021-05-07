package Java.Application;

import java.util.ArrayList;
import java.util.Date;

public class Season extends Credit implements ISeason{
    private int showID;
    private ArrayList<IEpisode> episodes;
    private boolean allEpisodesApproved;

    public Season(String name, Date dateAdded, int creditID, boolean approved, String description , int showID,  Boolean allEpisodesApproved){
        super(name,dateAdded, creditID, approved, description);
        this.showID = showID;
        this.episodes = new ArrayList<>();
        this.allEpisodesApproved = allEpisodesApproved;
    }

    @Override
    public ArrayList<IEpisode> getEpisodes() {
        return episodes;
    }

    @Override
    public void setEpisodes(ArrayList<IEpisode> episodes) {
        this.episodes = episodes;
    }

    @Override
    public int getShowID() {
        return showID;
    }

    @Override
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

        for (IEpisode episode: getEpisodes()){
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

    public void addEpisode(IEpisode episode){
        episodes.add(episode);
    }
}

