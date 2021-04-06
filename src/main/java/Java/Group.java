package Java;

import java.util.Date;

public class Group extends Credit {
    private int groupId;

    public Group(String name, Date dateAdded, int creditId, boolean approved, String description, int groupId){
        super(name, dateAdded, creditId, approved, description);
        this.groupId = groupId;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }
}
