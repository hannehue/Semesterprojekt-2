package Java.Application;

public interface IEpisode extends IProduction {

    int getSeasonID();
    void setSeasonID(int seasonID);

    String toFileString();

    void addStaffID(int id);
}
