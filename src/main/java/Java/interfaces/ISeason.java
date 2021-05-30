package Java.interfaces;

import javafx.collections.ObservableList;

import java.util.ArrayList;

public interface ISeason  extends ICredit{

    ObservableList<IEpisode> getEpisodes();
    void setEpisodes(ObservableList<IEpisode> episodes);

    int getShowID();
    void setShowID(int showID);

    boolean isAllEpisodesApproved();

    void addEpisode(IEpisode episode);

    void setAllEpisodeApproved(boolean b);

    int getNumberOfEpisode();
}
