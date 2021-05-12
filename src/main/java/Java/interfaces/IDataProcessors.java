package Java.interfaces;

import Java.domain.data.Category;

public interface IDataProcessors {
    void addPerson(String name, String description, String phoneNumber, String email);
    void addJob(int productionId);
    void addMovie(String name, String description, Category[] categories, int id, int length);
    void addShow(String title, String description);
    void addSeason(String description, int showId);
    void addEpisode(String title, int length, int seasonId, int id);
    String getNextEpisode(Integer seasonId);

    void onStop();
}
