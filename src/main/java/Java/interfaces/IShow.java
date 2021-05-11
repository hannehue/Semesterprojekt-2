package Java.interfaces;

import javafx.collections.ObservableList;

import java.util.ArrayList;

public interface IShow extends ICredit {

    boolean isAllSeasonApproved();
    void setAllSeasonApproved(boolean allSeasonApproved);

    ObservableList<ISeason> getSeasons();
    void setSeasons(ObservableList<ISeason> seasons);


    int getNumberOfSeason();
    String toFileString();
}
