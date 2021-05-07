package Java.Application;

import java.util.ArrayList;

public interface ISeason  extends ICredit{

    ArrayList<Episode> getEpisodes();
    void setEpisodes(ArrayList<Episode> episodes);

    int getShowID();
    void setShowID(int showID);

    String toFileString();

    boolean isAllEpisodesApproved();
}
