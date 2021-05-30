package Java.domain.services;

import Java.persistance.DatabaseLoaderFacade;
import Java.domain.data.Category;
import Java.domain.data.Movie;
import Java.domain.objectMapping.Factory;
import Java.interfaces.IMovie;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Date;
import java.util.Map;

public class MovieManager {
    private static MovieManager instance = new MovieManager();
    private MovieManager(){}

    public static MovieManager getInstance() {
        return instance;
    }

    private final ObservableList<IMovie> movieList = FXCollections.observableArrayList();

    public ObservableList<IMovie> getMovies(){
        return movieList;
    }

    public IMovie addMovie(String name, String description, Category[] categories, int length, Date releaseDate) {
        IMovie movie = new Movie(
                name,
                description,
                categories,
                length,
                releaseDate);
        Map<String, Integer> IDs = DatabaseLoaderFacade.getInstance().putInDatabase(movie);
        movieList.add(movie);
        movie.setIDMap(IDs);
        //call to database, get the Id
        System.out.println(movie.getName() + " added");
        System.out.printf("IDs" + IDs.get("productionID"));
        return movie;
    }

    public ObservableList<IMovie> searchMovie(String findMovie){
        return Factory.getInstance().getMovies(findMovie);
    }

}
