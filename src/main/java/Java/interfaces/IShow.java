package Java.interfaces;

import javafx.collections.ObservableList;

public interface IShow extends ICredit {

    boolean isAllSeasonApproved();
    void setAllSeasonApproved(boolean allSeasonApproved);

    ObservableList<ISeason> getSeasons();
    void setSeasons(ObservableList<ISeason> seasons);


    int getNumberOfSeason();
}
