package Java;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class Movie extends Production{

    //private HashMap<String, Person> cast;
    //private HashMap<Role, Person[]> productionTeam;

    public Movie(String name, Date dateAdded, int creditID, boolean approved, String description, int programID,
                 Category category, int lengthInSecs, Date releaseDate) {
        //Add cast and productionTeam to constructor when person and role are implemented

        super(name, dateAdded, creditID, approved, description, programID, category, lengthInSecs, releaseDate);
        //this.cast = cast;
        //this.productionTeam = productionTeam;
    }

    @Override
    public String toString() {
        return "Name: " + getName() + ", CreditID: " + getCreditID() +
               ", Description: " + getDescription() + ", MovieID: " + getProgramID() +
               ", Category: " + getCategories() + ", Movie length: " + getLengthInSecs() +
               ", Release date: " + getReleaseDate();
    }

    @Override
    public String toFileString() {
        return null;
    }
}
