package Java;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;

public class Season extends Credit {

    private int showID;
    private ArrayList<Episode> episodes;

    public Season(String name, Date dateAdded, int creditID, boolean approved, String description , int showID, ArrayList<Episode> episodes){
        super(name, dateAdded, creditID, approved, description);
        this.showID = showID;
        this.episodes = episodes;
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
    public String toFileString() {
        return null;
    }
}

