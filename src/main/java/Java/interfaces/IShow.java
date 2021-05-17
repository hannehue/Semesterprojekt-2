package Java.interfaces;

import javafx.collections.ObservableList;

import java.util.ArrayList;

public interface IShow extends ICredit {

    boolean isAllSeasonApproved();
    void setAllSeasonApproved(boolean allSeasonApproved);

    ObservableList<Integer> getSeasons();
    void setSeasons(ObservableList<Integer> seasons);


    int getNumberOfSeason();
    String toFileString();
}
