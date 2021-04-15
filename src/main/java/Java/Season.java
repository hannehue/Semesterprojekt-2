package Java;

import java.util.ArrayList;

public class
Season {

    private int seasonID;
    private String description;
    private ArrayList<Episode> episodes;
    private String seasonName;
    private int numberOfEpisode;

    public Season(int seasonID, String description, ArrayList<Episode> episodes, String seasonName, int numberOfEpisode){
        this.seasonID = seasonID;
        this.description = description;
        this.episodes = episodes;
        this.seasonName = seasonName;
        this.numberOfEpisode = numberOfEpisode;


    }

    public int getSeasonID() {
        return seasonID;
    }

    public String getDescription() {
        return description;
    }

    public ArrayList<Episode> getEpisodes() {
        return episodes;
    }

    public String getSeasonName() {
        return seasonName;
    }

    public void addNumberOfEpisode() {
        this.numberOfEpisode++;
    }

    public int getNumberOfEpisode() {
        return numberOfEpisode;
    }

    public void setSeasonID(int seasonID) {
        this.seasonID = seasonID;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setEpisodes(ArrayList<Episode> episodes) {
        this.episodes = episodes;
    }

    public void setSeasonName(String seasonName) {
        this.seasonName = seasonName;
    }
}

