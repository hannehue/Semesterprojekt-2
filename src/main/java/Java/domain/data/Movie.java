package Java.domain.data;

import Java.interfaces.IMovie;

import java.util.*;

public class Movie extends Production implements IMovie {

    public Movie(String name, Date dateAdded, int creditID, boolean approved, String description, int productionID,
                 Category[] category, int lengthInSecs, Date releaseDate) {
        //Add cast and productionTeam to constructor when person and role are implemented
        super(name, dateAdded, creditID, approved, description, productionID, category, lengthInSecs, releaseDate);
    }

    public Movie(String name, String desciption, Category[] categories, int lengthInSecs, Date releaseDate){
        super(
                name,
                new Date(),
                0,
                false,
                desciption,
                0,
                categories,
                lengthInSecs,
                releaseDate
        );
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
}
