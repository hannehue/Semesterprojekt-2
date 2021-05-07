package Java.Application;

import java.util.ArrayList;

public interface ISeason {

    ArrayList<Episode> getEpisodes();
    void setEpisodes(ArrayList<Episode> episodes);

    int getShowID();
    void setShowID(int showID);
}
