package Java.interfaces;

import Java.domain.data.Category;

public interface IDataProcessors {
    void addPerson(String name, String description, String phoneNumber, String email);
    void addJob(int productionId);
    void addMovie(String name, String description, Category[] categories, int id, int length);
    void addShow(String title, String description);
    void addSeason(String description, int showId);
    void addEpisode(String title, int length, String show, String season, int id);
    String getNextEpisode(String show, String season);

    void onStop();
}
