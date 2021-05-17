package Java.domain;

import Java.interfaces.IMovie;

import java.util.*;

public class Movie extends Production implements IMovie {

    public Movie(String name, Date dateAdded, int creditID, boolean approved, String description, int productionID,
                 Category[] category, int lengthInSecs, Date releaseDate) {
        //Add cast and productionTeam to constructor when person and role are implemented
        super(name, dateAdded, creditID, approved, description, productionID, category, lengthInSecs, releaseDate);
    }
    //Overloaded constructor to send a string to production to get a

    @Override
    public String toString() {
        return "Name: " + getName() + ", CreditID: " + getCreditID() +
               ", Description: " + getDescription() + ", MovieID: " + getProductionID() +
               ", Categories: " + Arrays.toString(getCategories()).substring(1,Arrays.toString(getCategories()).length() - 1) +
               ", Movie length: " + (getLengthInSecs() / 60 / 60) + " hours " + getLengthInSecs() / 60 % 60  + " minutes " +
               ", Release date: " + getReleaseDate() + " Staff IDs: " + getStaffIDs();
    }

    @Override
    public String toFileString() {
        String movieFileString = "";
        movieFileString += getName() + "," + getDateAdded() + "," + getCreditID() + "," + isApproved() +
                "," + getDescription() + "," + getProductionID() + ",";

        String categories = "";
        for (Category category: getCategories()){
            categories += category.toString() + ";";
        }
        movieFileString += categories.substring(0, categories.length() - 1) + ",";

        movieFileString += getLengthInSecs() + "," + getReleaseDate() + ",";

        String staffIDString = "";
        for(Integer staffId: getStaffIDs()){
            staffIDString += staffId + ";";
        }
        movieFileString += staffIDString.substring(0, staffIDString.length() - 1) + ",";

        return movieFileString;
    }

}
