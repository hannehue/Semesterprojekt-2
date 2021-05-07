package Java.Application;

import java.util.ArrayList;
import java.util.Date;

public abstract class Production extends Credit implements IProduction {

    private int productionID;
    private Category[] categories;
    private int lengthInSecs;
    private Date releaseDate;
    private ArrayList<Integer> staffIDs;

    public Production(String name, Date dateAdded, int creditID, boolean approved, String description, int productionID,
                      Category[] categories, int lengthInSecs, Date releaseDate) {
        //Husk at implementér category. - Is implemented by Victor: remember to read and confirm.
        super(name, dateAdded, creditID, approved, description);
        this.productionID = productionID;
        this.categories = categories;
        this.lengthInSecs = lengthInSecs;
        this.releaseDate = releaseDate;
        this.staffIDs = new ArrayList<>();
    }

    @Override
    public int getProductionID() {
        return productionID;
    }

    @Override
    public Category[] getCategories() {
        return categories;
    }

    @Override
    public int getLengthInSecs() {
        return lengthInSecs;
    }

    @Override
    public Date getReleaseDate() {
        return releaseDate;
    }

    @Override
    public ArrayList<Integer> getStaffIDs() {
        return staffIDs;
    }


    public void addStaffID(int id){
        staffIDs.add(id);
    }

    public void setProductionID(int programID) {
        this.productionID = productionID;
    }

    public void setCategories(Category[] categories) {
        this.categories = categories;
    }

    public void setLengthInSecs(int lengthInSecs) {
        this.lengthInSecs = lengthInSecs;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }
}


