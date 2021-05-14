package Java.domain.dataoperators;

import Java.domain.data.Category;
import Java.domain.data.Movie;
import Java.interfaces.IMovie;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class MovieOperator {
    private static MovieOperator instance = new MovieOperator();
    private MovieOperator(){}

    public static MovieOperator getInstance() {
        return instance;
    }

    private final ObservableList<IMovie> movieList = FXCollections.observableArrayList();

    public ObservableList<IMovie> getMovies(){
        return movieList;
    }

    public void addMovie(String name, String description, Category[] categories, int id, int length) {
        IMovie movie = new Movie(
                name,
                null,
                id,
                false,
                description,
                1, // Change later
                categories,
                length,
                null);
        movieList.add(movie);
        System.out.println(movie.getName());
    }

}
