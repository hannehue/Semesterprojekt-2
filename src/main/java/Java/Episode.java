package Java;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class Episode extends Production {

    private String seasonID;
    private ArrayList<String> staffIDs;

    public Episode(String name, Date dateAdded, int creditID, boolean approved, String description, int programID,
                   Category[] category, int lengthInSecs, Date releaseDate, ArrayList<String> staffIDs, String seasonID){
        super(name, dateAdded, creditID, approved, description, programID, category, lengthInSecs, releaseDate, staffIDs);
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
        for(String staffId: getStaffIDs()){
            staffIDString += staffId + ";";
        }
        EpisodeFileString += staffIDString.substring(0, staffIDString.length() - 1) + ",";

        EpisodeFileString += getSeasonID() + ",";

        return EpisodeFileString;
    }


    public String getSeasonID() {
        return seasonID;
    }

    public void setSeasonID(String seasonID) {
        this.seasonID = seasonID;
    }
}
