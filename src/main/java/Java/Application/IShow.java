package Java.Application;

import java.util.ArrayList;

public interface IShow extends ICredit {

    boolean isAllSeasonApproved();
    void setAllSeasonApproved(boolean allSeasonApproved);

    ArrayList<ISeason> getSeasons();
    void setSeasons(ArrayList<ISeason> seasons);


    int getNumberOfSeason();
    String toFileString();
}
