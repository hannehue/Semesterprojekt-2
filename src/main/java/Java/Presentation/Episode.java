package Java.Presentation;

import java.util.Arrays;
import java.util.Date;

public class Episode extends Production implements IEpisode {

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
                ", Release date: " + getReleaseDate() + ", Staff IDs: " + getStaffIDs();
    }

    @Override
    public String toFileString() {
        String EpisodeFileString = "";
        EpisodeFileString += getName() + "," + getDateAdded() + "," + getCreditID() + "," + isApproved() +
                "," + getDescription() + "," + getProductionID() + ",";

        String categories = "";
        for (Category category: getCategories()){
            categories += category.toString() + ";";
        }
        EpisodeFileString += categories.substring(0, categories.length() - 1) + ",";

        EpisodeFileString += getLengthInSecs() + "," + getReleaseDate() + ",";

        String staffIDString = "";
        for(Integer staffId: getStaffIDs()){
            staffIDString += staffId + ";";
        }
        EpisodeFileString += staffIDString.substring(0, staffIDString.length() - 1) + ",";

        EpisodeFileString += getSeasonID();

        return EpisodeFileString;
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