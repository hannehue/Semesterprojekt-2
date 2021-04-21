package Java;

import java.util.ArrayList;
import java.util.Date;

public abstract class Production extends Credit {

    private int productionID;
    private Category[] categories;
    private int lengthInSecs;
    private Date releaseDate;

    public Production(String name, Date dateAdded, int creditID, boolean approved, String description, int productionID, Category[] categories, int lengthInSecs, Date releaseDate){
       //Husk at implementér category. - Is implemented by Victor: remember to read and confirm.
        super(name, dateAdded, creditID, approved, description);
        this.productionID = productionID;
        this.categories = categories;
        this.lengthInSecs = lengthInSecs;
        this.releaseDate = releaseDate;
    }

    public int getProgramID() {
        return productionID;
    }

    public Category[] getCategories() {
        return categories;
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

    public void setCategories(Java.Category[] categories) {
        this.categories = categories;
    }

    public void setLengthInSecs(int lengthInSecs) {
        this.lengthInSecs = lengthInSecs;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }
}

