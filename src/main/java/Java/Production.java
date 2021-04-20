package Java;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public abstract class Production extends Credit {

    private int productionID;
    private Category[] categories;
    private int lengthInSecs;
    private Date releaseDate;
    private HashMap<String, Person> cast;
    private HashMap<Role, ArrayList<Person>> productionTeam;

    public Production(String name, Date dateAdded, int creditID, boolean approved, String description, int productionID, int lengthInSecs, Date releaseDate){
       //Husk at implementér category.
        super(name, dateAdded, creditID, approved, description);
        this.productionID = productionID;
        this.lengthInSecs = lengthInSecs;
        this.releaseDate = releaseDate;
        cast = new HashMap<>();
        productionTeam = new HashMap<>();
    }

    public int getProgramID() {
        return productionID;
    }

    public int getLengthInSecs() {
        return lengthInSecs;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setProgramID(int programID) {
        this.productionID = productionID;
    }

    public void setLengthInSecs(int lengthInSecs) {
        this.lengthInSecs = lengthInSecs;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setPersonInCast(String characterName, Person person) {
        cast.put(characterName, person);
    }

    public void getPersonInCast(String charactherName){
        cast.get(charactherName);
    }

    public HashMap<Role, ArrayList<Person>> getProductionTeam() {
        return productionTeam;
    }

    public void setProductionTeam(HashMap<Role, ArrayList<Person>> productionTeam) {
        this.productionTeam = productionTeam;
    }
}

