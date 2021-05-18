package Java.domain.data;

import Java.interfaces.IEpisode;
import Java.interfaces.ISeason;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.Date;

public class Season extends Credit implements ISeason {
    private int showID;
    private ObservableList<Integer> episodes;
    private boolean allEpisodesApproved;

    public Season(String name, Date dateAdded, int creditID, boolean approved, String description , int showID,  Boolean allEpisodesApproved){
        super(name,dateAdded, creditID, approved, description);
        this.showID = showID;
        this.episodes = FXCollections.observableArrayList();
        this.allEpisodesApproved = allEpisodesApproved;
    }

    public Season(String name, String description , Boolean allEpisodesApproved){
        super(
                name,
                new Date(),
                0,
                false,
                description
        );
        this.showID = 0;
        this.episodes = FXCollections.observableArrayList();
        this.allEpisodesApproved = allEpisodesApproved;
    }

    @Override
    public ObservableList<Integer> getEpisodes() {
        return episodes;
    }

    @Override
    public void setEpisodes(ObservableList<Integer> episodes) {
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
        return this.getName();
    }

    @Override
    public String toFileString() {
        String seasonFileString = "";

        seasonFileString += getDateAdded() + "##" + getCreditID() + "##" + isApproved() + "##" + getDescription() + "##" +
                getShowID() + "##";

        seasonFileString += "--";

        for (Integer episode: getEpisodes()){
            seasonFileString += episode + "--";
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

    public void addEpisode(Integer episode){
        episodes.add(episode);
    }
}

