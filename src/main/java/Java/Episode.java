package Java;

public class Episode {

    private int episodeID;
    private String episodeName;
    private String episodeDescription;

    public Episode(int episodeID, String episodeName, String episodeDescription){
        this.episodeID = episodeID;
        this.episodeName = episodeName;
        this.episodeDescription = episodeDescription;
    }

    public int getEpisodeID() {
        return episodeID;
    }

    public String getEpisodeName() {
        return episodeName;
    }

    public String getEpisodeDescription() {
        return episodeDescription;
    }

    public void setEpisodeID(int episodeID) {
        this.episodeID = episodeID;
    }

    public void setEpisodeName(String episodeName) {
        this.episodeName = episodeName;
    }

    public void setEpisodeDescription(String episodeDescription) {
        this.episodeDescription = episodeDescription;
    }
}
