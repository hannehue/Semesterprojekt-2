package Java;

public class Season {

    private int seasonID;
    private String description;
    private Episode[] episodes;
    private String seasonName;

    public Season(int seasonID, String description, Episode[] episodes, String seasonName){
        this.seasonID = seasonID;
        this.description = description;
        this.episodes = episodes;
        this.seasonName = seasonName;

    }

    public int getSeasonID() {
        return seasonID;
    }

    public String getDescription() {
        return description;
    }

    public Episode[] getEpisodes() {
        return episodes;
    }

    public String getSeasonName() {
        return seasonName;
    }

    public void setSeasonID(int seasonID) {
        this.seasonID = seasonID;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setEpisodes(Episode[] episodes) {
        this.episodes = episodes;
    }

    public void setSeasonName(String seasonName) {
        this.seasonName = seasonName;
    }
}

