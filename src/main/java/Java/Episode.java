package Java;

public class Episode {

    private int episodeID;
    private String episodeName;
    private String episodeDescription;
    private int length;
    private String episodeNameId;

    public Episode(int episodeID, String episodeName, String episodeDescription, int length, String episodeNameId){
        this.episodeID = episodeID;
        this.episodeName = episodeName;
        this.episodeDescription = episodeDescription;
        this.length = length;
        this.episodeNameId = episodeNameId;
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

    public String getEpisodeNameId() {
        return episodeNameId;
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
