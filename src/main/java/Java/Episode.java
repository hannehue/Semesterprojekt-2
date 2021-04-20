package Java;

import java.util.Date;
import java.util.HashMap;

public class Episode extends Production{

    private int seasonID;
    private HashMap<String, Person> cast;
    private HashMap<Role, Person[]> productionTeam;

    public Episode(String title, Date dateAdded, int seasonID, int creditID,
                   boolean approved, String description, int productionID,
                   int lengthInSecs, Date releaseDate){
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
