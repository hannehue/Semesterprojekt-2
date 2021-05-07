package Java.domain;

import Java.data.DatabaseLoader;
import Java.interfaces.IDataProcessors;
import Java.interfaces.IMovie;
import Java.interfaces.IPerson;
import Java.interfaces.IShow;

import java.util.ArrayList;

public class ApplicationManager implements IDataProcessors {

    private DatabaseLoader dataLoader;
    private ArrayList<IPerson> personList = new ArrayList<>();
    private ArrayList<IMovie> movieList= new ArrayList<>();
    private ArrayList<IShow> showList = new ArrayList<>();
    private ArrayList<Job> tempList = new ArrayList<>();
    private UserType userType;
    private String searchFieldPlaceholder = "";

    @Override
    public void addJob() {

    }

    @Override
    public void addMovie() {

    }

    @Override
    public void addShow() {

    }

    @Override
    public void addSeason() {

    }

    @Override
    public void addEpisode() {

    }

    @Override
    public String getNextEpisode() {
        return null;
    }
}
