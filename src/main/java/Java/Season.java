package Java;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;

public class Season extends Credit {

    private String showID;
    private ArrayList<Episode> episodes;
    private boolean allEpisodesApproved;

    public Season(Date dateAdded, int creditID, boolean approved, String description , String showID, ArrayList<Episode> episodes, Boolean allEpisodesApproved){
        super(dateAdded, creditID, approved, description);
        this.showID = showID;
        this.episodes = episodes;
        this.allEpisodesApproved = allEpisodesApproved;
    }

    public ArrayList<Episode> getEpisodes() {
        return episodes;
    }

    public void setEpisodes(ArrayList<Episode> episodes) {
        this.episodes = episodes;
    }

    public String getShowID() {
        return showID;
    }

    public void setShowID(String showID) {
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
}

