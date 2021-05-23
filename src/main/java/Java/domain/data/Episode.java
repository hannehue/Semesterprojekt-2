package Java.domain.data;

import Java.interfaces.IEpisode;

import java.util.Arrays;
import java.util.Date;

public class Episode extends Production implements IEpisode {

    private int seasonID;

    public Episode(String name, Date dateAdded, int creditID, boolean approved, String description, int programID,
                   Category[] category, int lengthInSecs, Date releaseDate, int seasonID){
        super(name, dateAdded, creditID, approved, description, programID, category, lengthInSecs, releaseDate);
        this.seasonID = seasonID;
    }

    public Episode(String name, String description, Category[] category, int lengthInSecs, Date releaseDate){
        super(
                name,
                new Date(),
                0,
                false,
                description,
                0,
                category,
                lengthInSecs,
                releaseDate
        );
        this.seasonID = 0;
    }

    @Override
    public String toString() {
        return "Name: " + getName() + ", CreditID: " + getCreditID() +
                ", Description: " + getDescription() + ", EpidoseID: " + getProductionID() + ", SeasonID" + getSeasonID() +
                ", Categories: " + Arrays.toString(getCategories()).substring(1,Arrays.toString(getCategories()).length() - 1) +
                ", Episode length: " + (getLengthInSecs() / 60 / 60) + " hours " + getLengthInSecs() / 60 % 60  + " minutes " +
                ", Release date: " + getReleaseDate() + ", Staff IDs: " + getStaffIDs();
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
