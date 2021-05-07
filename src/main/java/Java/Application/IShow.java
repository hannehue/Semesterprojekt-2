package Java.Application;

import java.util.ArrayList;

public interface IShow extends ICredit {

    boolean isAllSeasonApproved();
    void setAllSeasonApproved(boolean allSeasonApproved);

    ArrayList<Season> getSeasons();
    void setSeasons(ArrayList<Season> seasons);


}
