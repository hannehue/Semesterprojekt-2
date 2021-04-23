package Java;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public abstract class Production extends Credit {

    private int productionID;
    private Category[] categories;
    private int lengthInSecs;
    private Date releaseDate;
    private ArrayList staff;

    public Production(String name, Date dateAdded, int creditID, boolean approved, String description, int productionID, int lengthInSecs, Date releaseDate){
       //Husk at implementér category.
        super(name, dateAdded, creditID, approved, description);
        this.productionID = productionID;
        this.lengthInSecs = lengthInSecs;
        this.releaseDate = releaseDate;
        this.staff = new ArrayList();
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

    public ArrayList getStaff() {
        return staff;
    }

    public void setStaff(ArrayList staff) {
        this.staff = staff;
    }
}


