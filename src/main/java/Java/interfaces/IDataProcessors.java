package Java.interfaces;

import Java.domain.Category;

public interface IDataProcessors {
    void addPerson(String name, String description, String phoneNumber, String email);
    void addJob(int productionId);
    void addMovie(String name, String description, Category[] categories, int id, int length);
    void addShow(String title, String description);
    void addSeason(String description, String showList);
    void addEpisode(String title, int length, String show, String season, int id);
    String getNextEpisode();

    void onStop();
}
