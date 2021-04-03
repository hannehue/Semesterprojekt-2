package Java;

import java.util.Date;

public abstract class Production extends Credit {

    private int productionID;
    private Category[] categories;
    private int lengthInSecs;
    private Date releaseDate;

    public Production(String name, Date dateAdded, int creditID, boolean approved, String description, int productionID, int lengthInSecs, Date releaseDate){
       //Husk at implementér category.
        super(name, dateAdded, creditID, approved, description);
        this.productionID = productionID;
        this.lengthInSecs = lengthInSecs;
        this.releaseDate = releaseDate;
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
}

