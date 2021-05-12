package Java.interfaces;

import javafx.collections.ObservableList;

import java.util.ArrayList;

public interface ISeason  extends ICredit{

    ObservableList<Integer> getEpisodes();
    void setEpisodes(ObservableList<Integer> episodes);

    int getShowID();
    void setShowID(int showID);

    String toFileString();

    boolean isAllEpisodesApproved();

    void addEpisode(Integer episode);

    void setAllEpisodeApproved(boolean b);

    int getNumberOfEpisode();
}
