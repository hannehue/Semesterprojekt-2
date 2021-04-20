package Java;

import java.util.Date;

public class Group extends Credit {
    private int groupID;

    public Group(String name, Date dateAdded, int creditId, boolean approved, String description, int groupID){
        super(name, dateAdded, creditId, approved, description);
        this.groupID = groupID;
    }

    public int getGroupID() {
        return groupID;
    }

    public void setGroupID(int groupID) {
        this.groupID = groupID;
    }

    @Override
    public String toString() {
        return "Name: " + getName() + ", CreditID: " + getCreditID() +
                ", Description: " + getDescription() + ", GroupID: " + getGroupID();
    }
}
