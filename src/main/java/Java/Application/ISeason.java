package Java.Application;

import java.util.ArrayList;

public interface ISeason  extends ICredit{

    ArrayList<IEpisode> getEpisodes();
    void setEpisodes(ArrayList<IEpisode> episodes);

    int getShowID();
    void setShowID(int showID);

    String toFileString();

    boolean isAllEpisodesApproved();

    void addEpisode(IEpisode episode);

    void setAllEpisodeApproved(boolean b);

    int getNumberOfEpisode();
}
