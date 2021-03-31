package Java;

import java.util.Date;

public abstract class Production extends Credit {

    private int programID;
    //private Category[] category;
    private int lengthInSecs;
    private Date releaseDate;

    public Production(String name, Date dateAdded, int creditID, boolean approved, String description, int programID, int lengthInSecs, Date releaseDate){
       //Husk at implementér category.
        super(name, dateAdded, creditID, approved, description);
        this.programID = programID;
        this.lengthInSecs = lengthInSecs;
        this.releaseDate = releaseDate;
    }

    public int getProgramID() {
        return programID;
    }

    public int getLengthInSecs() {
        return lengthInSecs;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setProgramID(int programID) {
        this.programID = programID;
    }

    public void setLengthInSecs(int lengthInSecs) {
        this.lengthInSecs = lengthInSecs;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }
}

