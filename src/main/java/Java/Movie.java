package Java;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class Movie extends Production{

    private HashMap<String, Person> cast;
    private HashMap<Role, Person[]> productionTeam;

    public Movie(String name, Date dateAdded, int creditID, boolean approved, String description, int programID,
                 int lengthInSecs, Date releaseDate, int showID, ArrayList<Season> seasons){
        //Add cast and productionTeam to constructor when person and role are implemented

        super(name, dateAdded, creditID, approved, description, programID, lengthInSecs, releaseDate);
    }

    public void setPersonInCast(String characterName, Person person) {
        cast.put(characterName, person);
    }

    public void getPersonInCast(String charactherName){
        cast.get(charactherName);
    }

    public Person[] getProductionTeamRole(Role role) {
        return productionTeam.get(role);
    }

    public void setRolesInProduction(Role role, Person person[]) {
        productionTeam.put(role, person);
    }
}
