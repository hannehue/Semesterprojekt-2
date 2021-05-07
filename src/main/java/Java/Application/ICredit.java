package Java.Application;

import java.util.ArrayList;
import java.util.Date;

public interface ICredit {

    //getters/setters for Credit class
    String getName();
    void setName(String name);

    Date getDateAdded();
    void setDateAdded(Date dateAdded);

    int getCreditID();
    void setCreditID(int creditID);

    String getDescription();
    void setDescription(String description);

    boolean isApproved();
    void setApproved(boolean approved);

    //getters/setters for Season class


}
