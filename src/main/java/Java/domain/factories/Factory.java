package Java.domain.factories;

import Java.data.DatabaseLoaderFacade;
import Java.domain.data.Job;
import Java.domain.data.Person;
import Java.domain.data.Role;
import Java.interfaces.IJob;
import Java.interfaces.IMovie;
import Java.interfaces.IPerson;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Factory {

    private static Factory instance;
    private SimpleDateFormat formatter;

    private Factory() {
        formatter = new SimpleDateFormat("E MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
    }

    public static Factory getInstance() {
        if (instance == null) {
            instance = new Factory();
        }
        return instance;
    }

    public ObservableList<IPerson> getPersons(String searchString) {
        ObservableList<IPerson> personObservableList = FXCollections.observableArrayList();
        ResultSet personsResultSet = DatabaseLoaderFacade.getInstance().searchPersonsFromDatabase(searchString);

        try {
            while (personsResultSet.next()) {
                IPerson tempPerson = Mapper.getInstance().mapPerson(personsResultSet);

                Map<String, Integer> IDs = new HashMap<>();
                IDs.put("creditID", tempPerson.getCreditID());
                IDs.put("personID", tempPerson.getPersonID());
                tempPerson.setIDMap(IDs);

                //Temporary job list
                ArrayList<IJob> jobs = new ArrayList<>();
                //Result set handling
                jobs.addAll(getInstance().getJobs(tempPerson));
                // Takes the temporary job list, and sets it to the current tempPerson
                tempPerson.setJobs(jobs);
                //Adds the current temp person to the observable list to be returned
                personObservableList.add(tempPerson);
            }
            return personObservableList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private ArrayList<IJob> getJobs(IPerson person) {
        ResultSet jobResultSet = DatabaseLoaderFacade.getInstance().getJobsForPerson(person);
        ArrayList<IJob> jobs = new ArrayList<>();
        try {
            while (jobResultSet.next()) {
                jobs.add(Mapper.getInstance().mapJob(jobResultSet));
            }
            return jobs;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ObservableList<IMovie> getMovies(String searchString){
        ObservableList<IMovie> movieList = FXCollections.observableArrayList();
        ResultSet movieResultSet = DatabaseLoaderFacade.getInstance().searchMoviesFromDatabase(searchString);
            try {
                while(movieResultSet.next()) {
                    IMovie movie = Mapper.getInstance().mapMovie(movieResultSet);
                    Map<String, Integer> IDs = new HashMap<>();
                    IDs.put("creditID", movie.getCreditID());
                    IDs.put("productionID", movie.getProductionID());
                    IDs.put("movieID", movieResultSet.getInt("movie_id"));
                    movie.setIDMap(IDs);
                    movieList.add(movie);
                }
                return movieList;
            } catch (SQLException e) {
                e.printStackTrace();
        }
            return null;
    }
}
