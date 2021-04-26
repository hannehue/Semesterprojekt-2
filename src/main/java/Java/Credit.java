package Java;

import java.util.Date;

public abstract class Credit {

    private String name;
    private Date dateAdded;
    private int creditID;
    private boolean approved;
    private String description;


    public Credit(Date dateAdded, int creditID, boolean approved, String description){
        this.dateAdded = dateAdded;
        this.creditID = creditID;
        this.approved = approved;
        this.description = description;
    }

    /*
    @Override
    public String toString() {
        throw new UnsupportedOperationException("Not implemented yet");
    }*/

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(Date dateAdded) {
        this.dateAdded = dateAdded;
    }

    public int getCreditID() {
        return creditID;
    }

    public void setCreditID(int creditID) {
        this.creditID = creditID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public abstract String toFileString();
}
