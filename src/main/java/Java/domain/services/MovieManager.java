package Java.domain.services;

import Java.data.DatabaseLoader;
import Java.data.DatabaseLoaderFacade;
import Java.domain.data.Category;
import Java.domain.data.Movie;
import Java.interfaces.IMovie;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.Date;

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

    public int addMovie(String name, String description, Category[] categories, int length, Date releaseDate) {
        IMovie movie = new Movie(
                name,
                description,
                categories,
                length,
                releaseDate);
        movieList.add(movie);
        //call to database, get the Id
        System.out.println(movie.getName());
        return 0;
    }

    public ArrayList<IMovie> searchMovie(String findMovie){
        return DatabaseLoaderFacade.getInstance().searchMoviesFromDatabase(findMovie);
    }

}
