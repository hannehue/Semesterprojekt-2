package Java;

import java.util.ArrayList;
import java.util.Date;

public class Show extends Production{

    private int showID;
    private ArrayList <Season> seasons;

    public Show(String name, Date dateAdded, int creditID, boolean approved, String description, int programID,
                int lengthInSecs, Date releaseDate, int showID, ArrayList <Season> seasons){
        super(name, dateAdded, creditID, approved, description, programID, lengthInSecs, releaseDate);
        this.showID = showID;
        this.seasons = seasons;
    }

    public int getShowID() {
        return showID;
    }

    public ArrayList<Season> getSeasons() {
        return seasons;
    }

    public void setShowID(int showID) {
        this.showID = showID;
    }

    public void setSeasons(ArrayList<Season> seasons) {
        this.seasons = seasons;
    }
}
