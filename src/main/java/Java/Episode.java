package Java;

import java.util.Date;

public class Episode extends Production{

    private int seasonID;

    public Episode(String title, Date dateAdded, int seasonID, int creditID, boolean approved, String description, int productionID, int lengthInSecs, Date releaseDate){
        super(title, dateAdded, creditID, approved, description, productionID, lengthInSecs, releaseDate);
        this.seasonID = seasonID;
    }

    public int getSeasonID() {
        return seasonID;
    }

    public void setSeasonID(int seasonID) {
        this.seasonID = seasonID;
    }
}
