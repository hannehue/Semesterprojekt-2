package Java.interfaces;

import java.util.Date;
import java.util.Map;

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

    Map<String, Integer> getIDMap();
    void setIDMap(Map<String, Integer> IDs);

    String buildView();
}
