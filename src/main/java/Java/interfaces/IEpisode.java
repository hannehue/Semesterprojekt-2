package Java.interfaces;

public interface IEpisode extends IProduction {

    int getSeasonID();
    void setSeasonID(int seasonID);
    void setLengthInSecs(int LengthInSecs);

    void addStaffID(int id);
}
