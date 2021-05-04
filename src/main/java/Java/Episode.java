package Java;

import java.util.Arrays;
import java.util.Date;

public class Episode extends Production {

    private int seasonID;

    public Episode(String name, Date dateAdded, int creditID, boolean approved, String description, int programID,
                   Category[] category, int lengthInSecs, Date releaseDate, int seasonID){
        super(name, dateAdded, creditID, approved, description, programID, category, lengthInSecs, releaseDate);
        this.seasonID = seasonID;
    }

    @Override
    public String toString() {
        return "Name: " + getName() + ", CreditID: " + getCreditID() +
                ", Description: " + getDescription() + ", EpidoseID: " + getProductionID() + ", SeasonID" + getSeasonID() +
                ", Categories: " + Arrays.toString(getCategories()).substring(1,Arrays.toString(getCategories()).length() - 1) +
                ", Episode length: " + (getLengthInSecs() / 60 / 60) + " hours " + getLengthInSecs() / 60 % 60  + " minutes " +
                ", Release date: " + getReleaseDate();
    }

    @Override
    public String toFileString() {
        return null;
    }

    @Override
    public int getSeasonID() {
        return seasonID;
    }

    @Override
    public void setSeasonID(int seasonID) {
        this.seasonID = seasonID;
    }
}
