package Java.Application;

import java.util.Date;

public class Group extends Credit implements IGroup{
    private int groupID;

    public Group(String name, Date dateAdded, int creditId, boolean approved, String description, int groupID){
        super(name, dateAdded, creditId, approved, description);
        this.groupID = groupID;
    }

    @Override
    public int getGroupID() {
        return groupID;
    }

    @Override
    public void setGroupID(int groupID) {
        this.groupID = groupID;
    }

    @Override
    public String toString() {
        return "Name: " + getName() + ", CreditID: " + getCreditID() +
                ", Description: " + getDescription() + ", GroupID: " + getGroupID();
    }

    @Override
    public String toFileString() {
        return "" + getName() + "," + getDateAdded() + "," + getCreditID() + "," + isApproved() + "," + getDescription() + "," + getGroupID();
    }
}
